/*
 * Copyright ${license.git.copyrightYears} Rudy De Busscher (https://www.atbash.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.atbash.ee.security.rest.step3.keys;

import be.atbash.ee.security.octopus.jwk.JWKManager;
import be.atbash.ee.security.signature.api.SignatureKeyInfo;
import be.atbash.ee.security.signature.api.SignatureKeyInfoProvider;
import be.atbash.ee.security.signature.api.common.Algorithm;
import be.atbash.util.exception.AtbashUnexpectedException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;

/**
 *
 */
@ApplicationScoped
public class KeyInfoProvider implements SignatureKeyInfoProvider {

    @Inject
    private JWKManager jwkManager;

    private SignatureKeyInfo rsaKeyInfo;

    @PostConstruct
    public void init() {
        RSAKey key = (RSAKey) jwkManager.getJWKForApiKey("fb943c5c-8653-4144-b00e-0a714bdc958e");
        try {
            rsaKeyInfo = new SignatureKeyInfo(Algorithm.get("rsa-sha256"), "fb943c5c-8653-4144-b00e-0a714bdc958e", key.toPrivateKey());
        } catch (JOSEException e) {
            throw new AtbashUnexpectedException(e);
        }
    }

    @Override
    public SignatureKeyInfo provideKeyFor(String method, URI uri) {

        return rsaKeyInfo;
    }

}
