package be.atbash.ee.security.rest.step2.authc;

import be.atbash.ee.security.octopus.jwk.JWKManager;
import be.atbash.ee.security.octopus.jwt.JWTEncoding;
import be.atbash.ee.security.octopus.jwt.encoder.JWTEncoder;
import be.atbash.ee.security.octopus.jwt.parameter.JWTParameters;
import be.atbash.ee.security.octopus.jwt.parameter.JWTParametersBuilder;
import be.atbash.ee.security.rest.step2.user.User;
import be.atbash.ee.security.rest.step2.user.UserBoundary;

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

    @Inject
    private JWTEncoder encoder;

    @Inject
    private JWKManager jwkManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String doAuthenticate(UserNamePassword userNamePassword) {
        User user = userBoundary.findUser(userNamePassword);

        if (user != null) {
            ApplicationUserToken token = createToken(user);
            JWTParameters parameters = JWTParametersBuilder.newBuilderFor(JWTEncoding.JWS)
                    .withSecretKeyForSigning(jwkManager.getJWKSigningKey())
                    .build();
            return encoder.encode(token, parameters);
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
