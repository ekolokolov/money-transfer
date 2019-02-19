package ea.kolokolov.endpoint;

import ea.kolokolov.model.Transaction;
import ea.kolokolov.service.TransactionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
        return transactionService.createTransaction(request);
    }

    @GET
    public List<Transaction> getTransactions(@PathParam("accountId") Integer accountId) {
        return transactionService.getAllTransactions(accountId);
    }

}
