package ea.kolokolov.endpoint;

import ea.kolokolov.service.MoneyTransferService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("account/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferEndpoint {

    private MoneyTransferService transferService;

    @Inject
    public TransferEndpoint(MoneyTransferService transferService) {
        this.transferService = transferService;
    }

    @GET
    @Path("/{from}/{to}/{count}")
    public String transfer(@PathParam("from") Integer fromId,
                           @PathParam("to") Integer toId,
                           @PathParam("count") BigDecimal count) {
        return transferService.transfer(fromId, toId, count);
    }

}
