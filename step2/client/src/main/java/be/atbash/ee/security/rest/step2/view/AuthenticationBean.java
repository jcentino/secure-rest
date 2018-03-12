package be.atbash.ee.security.rest.step2.view;

import be.atbash.ee.security.octopus.jwt.decoder.JWTData;
import be.atbash.ee.security.octopus.jwt.decoder.JWTDecoder;
import be.atbash.ee.security.octopus.jwt.keys.KeySelector;
import be.atbash.ee.security.rest.step2.RemoteKeySelector;
import be.atbash.ee.security.rest.step2.logging.ClientLoggingFilter;
import be.atbash.ee.security.rest.step2.user.ApplicationUserToken;
import be.atbash.ee.security.rest.step2.user.UserNamePassword;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
