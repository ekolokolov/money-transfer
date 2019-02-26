package ea.kolokolov.endpoint;

import ea.kolokolov.model.Account;
import ea.kolokolov.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@Path("users/{login}/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountEndpoint {

    private AccountService accountService;

    @Inject
    public AccountEndpoint(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    public Response getAccounts(@PathParam("login") String login) {
        List<Account> accounts = accountService.getAccounts(login);
        return !accounts.isEmpty() ? ok(accounts).build() : status(NOT_FOUND).build();
    }

    @GET
    @Path("{accountId}")
    public Response getAccount(@PathParam("accountId") Integer accountId) {
        Account account = accountService.getAccount(accountId);
        return account != null ? ok(account).build() : status(NOT_FOUND).build();
    }

    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @POST
    public Response createAccount(Account account) {
        return status(FORBIDDEN).build();
    }

    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @PUT
    public Response updateAccount(Account account) {
        return status(FORBIDDEN).build();
    }

    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @DELETE
    public Response deleteAccount(Account account) {
        return status(FORBIDDEN).build();
    }

}
