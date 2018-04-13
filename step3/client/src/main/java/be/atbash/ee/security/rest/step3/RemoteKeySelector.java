package be.atbash.ee.security.rest.step3;

import be.atbash.ee.security.octopus.jwt.keys.KeySelector;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.AssymetricJWK;
import com.nimbusds.jose.jwk.JWK;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.text.ParseException;

/**
 *
 */
@ApplicationScoped
public class RemoteKeySelector implements KeySelector {

    private Client client;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    @Override
    public <T extends Key> T selectSecretKey(String kid) {
        WebTarget target = client.target("http://localhost:8080/service/data/key").path(kid);
        Response response = target.request(MediaType.TEXT_PLAIN).get();

        String serializedKey = response.readEntity(String.class);
        try {
            JWK jwk =  JWK.parse(serializedKey);
            return (T) ((AssymetricJWK)jwk).toPublicKey();
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
