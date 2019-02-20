package ea.kolokolov.endpoint;

import ea.kolokolov.model.Transaction;
import ea.kolokolov.service.TransactionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.Response.ok;


@Path("users/{login}/accounts/{accountId}/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionEndpoint {

    private TransactionService transactionService;

    @Inject
    public TransactionEndpoint(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @POST
    public Transaction createTransaction(@PathParam("accountId") Integer accountId, Transaction request) {
        return transactionService.transferMoney(request);
    }

    @GET
    public List<Transaction> getAllTransactions(@PathParam("accountId") Integer accountId) {
        return transactionService.getAllTransactions(accountId);
    }

    @GET
    @Path("{transactionId}")
    public Response getTransaction(@PathParam("transactionId") UUID uuid) {
        Transaction transaction = transactionService.getTransaction(uuid);
        return ok(transaction).build();
    }

}
