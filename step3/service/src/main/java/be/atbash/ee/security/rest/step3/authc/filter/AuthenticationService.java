package be.atbash.ee.security.rest.step3.authc.filter;

import be.atbash.ee.security.octopus.jwt.InvalidJWTException;
import be.atbash.ee.security.octopus.jwt.decoder.JWTData;
import be.atbash.ee.security.octopus.jwt.decoder.JWTDecoder;
import be.atbash.ee.security.octopus.jwt.keys.KeySelector;
import be.atbash.ee.security.rest.step3.authc.ApplicationUserToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

/**
 *
 */

@ApplicationScoped
public class AuthenticationService {

    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    @Inject
    private KeySelector keySelector;

    @Inject
    private ApplicationUserTokenVerifier verifier;

    @Inject
    private JWTDecoder decoder;

    public ApplicationUserToken authenticate(ContainerRequestContext containerRequest) {
        String header = containerRequest.getHeaderString(AUTHENTICATION_HEADER);

        String[] parts = header.split(" ");
        if (parts.length != 2) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        if (!BEARER.equals(parts[0])) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        try {
            JWTData<ApplicationUserToken> userToken = decoder.decode(parts[1], ApplicationUserToken.class, keySelector, verifier);
            return userToken.getData();
        } catch (InvalidJWTException invalidJWTException) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

    }
}
