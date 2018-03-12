package be.atbash.ee.security.rest.step1.authc;

import be.atbash.ee.security.rest.step1.user.User;
import be.atbash.ee.security.rest.step1.user.UserBoundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("/authenticate")
public class AuthenticationController {

    @Inject
    private UserBoundary userBoundary;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationUserToken doAuthenticate(UserNamePassword userNamePassword) {
        User user = userBoundary.findUser(userNamePassword);

        if (user != null) {

            return createToken(user);
        }
        return null;
    }

    private ApplicationUserToken createToken(User user) {
        ApplicationUserToken token = new ApplicationUserToken();
        token.setId(user.getId());
        token.setName(user.getName());
        token.setUserName(user.getUserName());
        return token;
    }
}
