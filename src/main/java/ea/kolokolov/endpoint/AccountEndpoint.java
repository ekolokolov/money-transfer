package ea.kolokolov.endpoint;

import ea.kolokolov.model.Account;
import ea.kolokolov.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
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
        return ok(accountService.getAccounts(login)).build();
    }

    @GET
    @Path("{accountId}")
    public Response getAccount(@PathParam("accountId") Integer accountId) {
        return ok(accountService.getAccount(accountId)).build();
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
