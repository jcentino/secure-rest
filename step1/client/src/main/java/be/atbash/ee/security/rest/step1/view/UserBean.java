package be.atbash.ee.security.rest.step1.view;

import be.atbash.ee.security.rest.step1.user.ApplicationUserToken;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 */
@SessionScoped
@Named
public class UserBean implements Serializable {

    private ApplicationUserToken applicationUserToken;

    public ApplicationUserToken getApplicationUserToken() {
        return applicationUserToken;
    }

    public void setApplicationUserToken(ApplicationUserToken applicationUserToken) {
        this.applicationUserToken = applicationUserToken;
    }
}
