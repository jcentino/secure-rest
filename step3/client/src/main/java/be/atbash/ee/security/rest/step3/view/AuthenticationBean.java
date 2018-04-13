package be.atbash.ee.security.rest.step3.view;

import be.atbash.ee.security.octopus.jwt.decoder.JWTData;
import be.atbash.ee.security.octopus.jwt.decoder.JWTDecoder;
import be.atbash.ee.security.rest.step3.RemoteKeySelector;
import be.atbash.ee.security.rest.step3.logging.ClientLoggingFilter;
import be.atbash.ee.security.rest.step3.user.ApplicationUserToken;
import be.atbash.ee.security.rest.step3.user.UserNamePassword;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 *
 */
@Model
public class AuthenticationBean implements Serializable {

    @Inject
    private UserBean userBean;

    @Inject
    private JWTDecoder decoder;

    @Inject
    private RemoteKeySelector keySelector;

    private UserNamePassword userNamePassword = new UserNamePassword();

    public UserNamePassword getUserNamePassword() {
        return userNamePassword;
    }

    public void doLogon() {
        Client client = ClientBuilder.newClient();
        client.register(ClientLoggingFilter.class);
        WebTarget target = client.target("http://localhost:8080/service/data/authenticate");
        Response response = target.request(MediaType.TEXT_PLAIN).post(Entity.json(userNamePassword));
        String encodedJWT = response.readEntity(String.class);
        response.close();

        JWTData<ApplicationUserToken> token = decoder.decode(encodedJWT, ApplicationUserToken.class, this.keySelector, null);

        userBean.setEncodedJWT(encodedJWT);
        userBean.setApplicationUserToken(token.getData());
    }

}
