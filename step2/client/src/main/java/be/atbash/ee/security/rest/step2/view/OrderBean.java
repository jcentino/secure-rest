package be.atbash.ee.security.rest.step2.view;

import be.atbash.ee.security.octopus.jwt.encoder.JWTEncoder;
import be.atbash.ee.security.octopus.jwt.parameter.JWTParametersNone;
import be.atbash.ee.security.rest.step2.logging.ClientLoggingFilter;
import be.atbash.ee.security.rest.step2.order.DeliveryAddress;
import be.atbash.ee.security.rest.step2.order.Order;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SessionScoped
@Named
public class OrderBean implements Serializable {

    @Inject
    private UserBean userBean;

    private DeliveryAddress deliveryAddress;

    private List<Long> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        deliveryAddress = new DeliveryAddress();
    }

    public void selectProduct() {
        switch (products.size()) {
            case 0:
                products.add(15L);
                break;
            case 1:
                products.add(74L);
                break;
            case 2:
                products.add(82L);
                break;
        }
    }

    public void sendOrder() {
        Order order = new Order();
        order.setClientId(userBean.getApplicationUserToken().getId());
        order.setProductIds(products);
        order.setDeliveryAddress(deliveryAddress);

        Client client = ClientBuilder.newClient();
        client.register(ClientLoggingFilter.class);
        WebTarget target = client.target("http://localhost:8080/service/data/order");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userBean.getEncodedJWT())
                .post(Entity.json(order));
        System.out.println(response.getEntity());

    }

    public List<Long> getProducts() {
        return products;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }
}
