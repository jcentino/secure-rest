package be.atbash.ee.security.rest.step2.authc.filter;

import be.atbash.ee.security.octopus.jwt.decoder.JWTVerifier;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 */
@ApplicationScoped
public class ApplicationUserTokenVerifier implements JWTVerifier {

    @Override
    public boolean verify(JWSHeader jwsHeader, JWTClaimsSet jwtClaimsSet) {
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        System.out.println(jwtClaimsSet.getExpirationTime().toInstant().atZone(ZoneOffset.UTC));
        return jwtClaimsSet.getExpirationTime().toInstant().isAfter(LocalDateTime.now().toInstant(ZoneOffset.UTC));

    }
}
