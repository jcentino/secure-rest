package be.atbash.ee.security.rest.step3.order;

import be.atbash.ee.security.rest.step3.authc.ApplicationUserToken;
import be.atbash.ee.security.signature.jaxrs.annotation.RestSignatureCheck;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/order")
@RestSignatureCheck
public class OrderController {

    @Context
    private HttpServletRequest servletRequest;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void createOrder(Order order) {
        ApplicationUserToken applicationUserToken = (ApplicationUserToken) servletRequest.getAttribute(ApplicationUserToken.class.getName());
        if (order.getClientId() != applicationUserToken.getId()) {
            System.out.println("clientId Mismatch");
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        System.out.println(order);
    }
}
