package ea.kolokolov.endpoint;

import ea.kolokolov.exception.UserNotFoundException;
import ea.kolokolov.model.User;
import ea.kolokolov.service.UserInfoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    private UserInfoService helloService;

    @Inject
    public UserEndpoint(UserInfoService helloService) {
        this.helloService = helloService;
    }

    @GET
    @Path("/{login}")
    public User get(@PathParam("login") String login) throws UserNotFoundException {
        User user = helloService.getUserFullInfo(login);
        if (user == null) throw new UserNotFoundException(login);
        return user;
    }

}
