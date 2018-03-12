package be.atbash.ee.security.rest.step3.authc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 */

public class ApplicationUserToken {

    private Long id;
    private String userName;
    private String name;
    private long exp;

    // Should also have permissions, See MP JWT Auth tokens as an example

    public ApplicationUserToken() {
        // exp is the seconds in JWT contexts, not the millisecond
        exp = LocalDateTime.now().plusHours(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli() / 1000;
    }

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

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }
}
