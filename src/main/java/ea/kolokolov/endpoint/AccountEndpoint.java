package ea.kolokolov.endpoint;

import ea.kolokolov.model.User;
import ea.kolokolov.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("/{id}")
    public User get(@PathParam("id") Integer id) {
        return helloService.sayHello(id);
    }

}
