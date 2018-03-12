package be.atbash.ee.security.rest.step1.user;

import be.atbash.ee.security.rest.step1.authc.UserNamePassword;

import javax.ejb.Stateless;

/**
 *
 */
@Stateless
public class UserBoundary {

    public User findUser(UserNamePassword userNamePassword) {
        User result = null;
        if ("rubus".equals(userNamePassword.getUsername())) {
            if (userNamePassword.getUsername().equals(userNamePassword.getPassword())) {
                result = new User();
                result.setId(123L);
                result.setName("Rudy De Busscher");
                result.setUserName("rubus");
            }
        } else {
            if (userNamePassword.getUsername().equals(userNamePassword.getPassword())) {
                result = new User();
                result.setId(777L);
                result.setName(userNamePassword.getUsername());
                result.setUserName(userNamePassword.getPassword());
            }
        }
        return result;
    }

}
