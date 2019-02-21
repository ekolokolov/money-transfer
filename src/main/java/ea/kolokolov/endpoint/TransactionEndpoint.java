package ea.kolokolov.endpoint;

import ea.kolokolov.model.Transaction;
import ea.kolokolov.service.TransactionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;


@Path("users/{login}/accounts/{accountId}/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionEndpoint {

    private TransactionService transactionService;

    @Inject
    public TransactionEndpoint(TransactionService transactionService) {
        this.transactionService = transactionService;
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

    @POST
    public Response createTransaction(@PathParam("accountId") Integer accountId, Transaction request) {
        return ok(transactionService.executeTransaction(request)).build();
    }


    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @PUT
    public Response updateTransaction(Transaction transaction) {
        return status(FORBIDDEN).build();
    }

    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @DELETE
    public Response deleteTransaction() {
        return status(FORBIDDEN).build();
    }
}
