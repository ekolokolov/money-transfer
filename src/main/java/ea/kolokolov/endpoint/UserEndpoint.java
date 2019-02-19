package ea.kolokolov.endpoint;

import ea.kolokolov.exception.UserNotFoundException;
import ea.kolokolov.service.UserInfoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.ok;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    private UserInfoService userInfoService;

    @Inject
    public UserEndpoint(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GET
    @Path("/{login}")
    public Response get(@PathParam("login") String login) throws UserNotFoundException {
        return ok(userInfoService.getUser(login)).build();
    }

    @GET
    public Response getUsers() {
        return ok(userInfoService.getUsers()).build();
    }

}
