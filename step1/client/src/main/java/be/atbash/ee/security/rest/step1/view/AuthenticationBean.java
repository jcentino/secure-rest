package be.atbash.ee.security.rest.step1.view;

import be.atbash.ee.security.rest.step1.logging.ClientLoggingFilter;
import be.atbash.ee.security.rest.step1.user.ApplicationUserToken;
import be.atbash.ee.security.rest.step1.user.UserNamePassword;

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

    private UserNamePassword userNamePassword = new UserNamePassword();

    public UserNamePassword getUserNamePassword() {
        return userNamePassword;
    }

    public void doLogon() {
        Client client = ClientBuilder.newClient();
        client.register(ClientLoggingFilter.class);
        WebTarget target = client.target("http://localhost:8080/service/data/authenticate");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(userNamePassword));
        ApplicationUserToken token = response.readEntity(ApplicationUserToken.class);
        response.close();

        userBean.setApplicationUserToken(token);

    }

}
