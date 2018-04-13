package be.atbash.ee.security.rest.step3.view;

import be.atbash.ee.security.rest.step3.user.ApplicationUserToken;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 */
@SessionScoped
@Named
public class UserBean implements Serializable{

    private String encodedJWT;
    private ApplicationUserToken applicationUserToken;

    public String getEncodedJWT() {
        return encodedJWT;
    }

    public void setEncodedJWT(String encodedJWT) {
        this.encodedJWT = encodedJWT;
    }

    public ApplicationUserToken getApplicationUserToken() {
        return applicationUserToken;
    }

    public void setApplicationUserToken(ApplicationUserToken applicationUserToken) {
        this.applicationUserToken = applicationUserToken;
    }
}
