package be.atbash.ee.security.rest.step2.order;

import be.atbash.ee.security.rest.step2.authc.ApplicationUserToken;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/order")
public class OrderController {

    @Context
    private HttpServletRequest servletRequest;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void createOrder(Order order) {
        ApplicationUserToken applicationUserToken = (ApplicationUserToken) servletRequest.getAttribute(ApplicationUserToken.class.getName());
        if (order.getClientId() != applicationUserToken.getId()) {
            System.out.println(String.format("ERROR: Order clientID %s does not match clientId from Token %s", order.getClientId(), applicationUserToken.getId()));
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        System.out.println(order);
    }
}
