package ea.kolokolov;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ea.kolokolov.model.Account;
import ea.kolokolov.model.Transaction;
import ea.kolokolov.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.StringJoiner;

import static java.math.RoundingMode.HALF_UP;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


public class ApiTest {

    private static final String CONTENT_TYPE = "Content-type";
    private static final String BASE_URL = "http://localhost:" + Main.PORT;
    private static final String USERS = "users";
    private static final String LOGIN = "iivanov";
    private static final String ACCOUNTS = "accounts";
    private static final Integer ACCOUNT_NUMBER = 654124124;
    private static final Integer WRONG_ACCOUNT_NUMBER = 333312312;
    private static final String TRANSACTIONS = "transactions";
    private static final BigDecimal AMOUNT = new BigDecimal(1000.50).setScale(2, HALF_UP);
    private static final int TO = 778999007;

    private static BigDecimal accountBalance;
    private CloseableHttpClient client = HttpClientBuilder.create().build();
    private ObjectMapper mapper = new ObjectMapper();
    private Server server;

    @BeforeClass
    public void setUp() throws Exception {
        server = Main.getServer();
        server.start();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    private void getUsers() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .toString();
        //then
        HttpResponse response = client.execute(new HttpGet(uri));
        User[] users = extract(response, User[].class);
        assertEquals(users.length, 4);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK_200);
    }

    @Test
    private void getUserInfo() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .add(LOGIN)
                .toString();
        //then
        HttpResponse response = client.execute(new HttpGet(uri));
        User user = extract(response, User.class);
        assertEquals(user.getLogin(), LOGIN);
    }

    @Test
    private void getUserAccounts() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .add(LOGIN).add(ACCOUNTS)
                .toString();

        //then
        HttpResponse response = client.execute(new HttpGet(uri));
        Account[] accounts = extract(response, Account[].class);
        assertNotNull(accounts[0]);
    }

    @Test
    private void getUserAccountInfo_beforeTransaction() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .add(LOGIN).add(ACCOUNTS)
                .add(String.valueOf(ACCOUNT_NUMBER))
                .toString();

        //then
        HttpResponse response = client.execute(new HttpGet(uri));
        Account account = extract(response, Account.class);
        assertEquals(account.getAccountId(), ACCOUNT_NUMBER);
        accountBalance = account.getBalance();
        assertNotNull(accountBalance);
    }

    @Test
    private void getAccountTransactions_withoutExistingTransactions() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .add(LOGIN).add(ACCOUNTS)
                .add(String.valueOf(WRONG_ACCOUNT_NUMBER))
                .add(TRANSACTIONS)
                .toString();

        //then
        HttpResponse response = client.execute(new HttpGet(uri));
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.NOT_FOUND_404);
    }

    @Test(dependsOnMethods = "getUserAccountInfo_beforeTransaction")
    private void postAccountTransaction() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .add(LOGIN).add(ACCOUNTS)
                .add(String.valueOf(ACCOUNT_NUMBER))
                .add(TRANSACTIONS)
                .toString();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
        Transaction transaction = new Transaction();
        transaction.setFrom(ACCOUNT_NUMBER);
        transaction.setTo(TO);
        transaction.setCount(AMOUNT);
        httpPost.setEntity(new StringEntity(mapper.writeValueAsString(transaction)));

        //then
        HttpResponse response = client.execute(httpPost);
        Transaction extractTransaction = extract(response, Transaction.class);
        assertEquals(extractTransaction.getFrom(), transaction.getFrom());
        assertEquals(extractTransaction.getTo(), transaction.getTo());
        assertNotNull(extractTransaction.getTransactionNumber());

    }

    @Test(dependsOnMethods = "postAccountTransaction")
    private void getUserAccountInfo_afterTransaction() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .add(LOGIN).add(ACCOUNTS)
                .add(String.valueOf(ACCOUNT_NUMBER))
                .toString();

        //then
        HttpResponse response = client.execute(new HttpGet(uri));
        Account account = extract(response, Account.class);
        assertEquals(account.getAccountId(), ACCOUNT_NUMBER);
        assertEquals(account.getBalance(), accountBalance.subtract(AMOUNT));
    }


    @Test(dependsOnMethods = "postAccountTransaction")
    private void getAccountTransactions_withExistingTransactions() throws IOException {
        //given
        String uri = new StringJoiner("/")
                .add(BASE_URL).add(USERS)
                .add(LOGIN).add(ACCOUNTS)
                .add(String.valueOf(ACCOUNT_NUMBER))
                .add(TRANSACTIONS)
                .toString();

        //then
        HttpResponse response = client.execute(new HttpGet(uri));
        Transaction[] transactions = extract(response, Transaction[].class);
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.OK_200);
        assertEquals(transactions.length, 1);
        Transaction transaction = transactions[0];
        assertNotNull(transaction.getTransactionNumber());
        assertNotNull(transaction.getStatus());
        assertNotNull(transaction.getFrom());
        assertNotNull(transaction.getTransactionNumber());
        assertNotNull(transaction.getCount());
    }

    @AfterClass
    public void tearDown() throws Exception {
        server.stop();
    }

    private <T> T extract(HttpResponse response, Class<T> valueType) throws IOException {
        return mapper.readValue(response.getEntity().getContent(), valueType);
    }
}