package ea.kolokolov.endpoint;

import ea.kolokolov.data.Account;
import ea.kolokolov.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountEndpoint {

    private HelloService helloService;

    @Inject
    public AccountEndpoint(HelloService helloService) {
        this.helloService = helloService;
    }

    @GET
    public Account get() {
        return new Account("Evgeny", 12L);
    }

}
