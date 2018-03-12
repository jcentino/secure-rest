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
import be.atbash.ee.security.signature.api.SignatureKeyData;
import be.atbash.ee.security.signature.api.SignatureKeyDataProvider;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 */
@ApplicationScoped
public class KeyDataProvider implements SignatureKeyDataProvider {

    @Inject
    private JWKManager jwkManager;

    @Override
    public SignatureKeyData getKeyData(String keyId) {
        SignatureKeyData result = null;
        if (jwkManager.existsApiKey(keyId)) {
            JWK jwkKey = jwkManager.getJWKForApiKey(keyId);
            if (jwkKey instanceof RSAKey) {
                RSAKey rsaKey = (RSAKey) jwkKey;
                try {
                    result = new SignatureKeyData(rsaKey.toPrivateKey());
                } catch (JOSEException e) {
                    e.printStackTrace();
                }
            }

        }

        return result;
    }

}
