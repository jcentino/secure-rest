package be.atbash.ee.security.rest.step1.authc;

/**
 *
 */

public class ApplicationUserToken {

    private Long id;
    private String userName;
    private String name;

    // Should also have permissions, See MP JWT Auth tokens as an example

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
