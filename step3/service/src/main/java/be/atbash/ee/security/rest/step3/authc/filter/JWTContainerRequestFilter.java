package be.atbash.ee.security.rest.step3.authc.filter;

import be.atbash.ee.security.rest.step3.authc.ApplicationUserToken;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 *
 */
@Provider
public class JWTContainerRequestFilter implements ContainerRequestFilter {

    @Inject
    private AuthenticationService authenticationService;

    @Context
    private transient HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {

        String path = containerRequest.getUriInfo().getPath();
        if ("/order".equals(path)) {
            ApplicationUserToken applicationUserToken = authenticationService.authenticate(containerRequest);

            servletRequest.setAttribute(ApplicationUserToken.class.getName(), applicationUserToken);
        }
    }
}
