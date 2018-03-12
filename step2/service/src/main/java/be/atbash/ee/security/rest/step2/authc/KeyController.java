package be.atbash.ee.security.rest.step2.authc;

import be.atbash.ee.security.octopus.jwk.JWKManager;
import com.nimbusds.jose.jwk.JWK;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/key")
public class KeyController {

    @Inject
    private JWKManager jwkManager;

    @Path("/{kid}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getKeyWithId(@PathParam("kid") String kid) {
        if (jwkManager.existsApiKey(kid)) {
            JWK key = jwkManager.getJWKForApiKey(kid).toPublicJWK();
            return Response.ok(key.toJSONString(), MediaType.TEXT_PLAIN).build();
        } else {
            return Response.noContent().build();
        }
    }
}
