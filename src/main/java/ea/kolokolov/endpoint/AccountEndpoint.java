package ea.kolokolov.endpoint;

import ea.kolokolov.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("account")
public class AccountEndpoint {

    private HelloService helloService;

    @Inject
    public AccountEndpoint(HelloService helloService) {
        this.helloService = helloService;
    }

    @GET
    public String get() {
        return helloService.sayHello() + " yo, bro!";
    }

}
