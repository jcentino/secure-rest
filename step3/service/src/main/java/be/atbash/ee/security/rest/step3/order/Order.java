package be.atbash.ee.security.rest.step3.order;

import java.util.List;

/**
 *
 */

public class Order {

    private long clientId;
    private List<Long> productIds;
    private DeliveryAddress deliveryAddress;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "Order{"
                + "clientId=" + clientId
                + ", productIds=" + productIds
                + ", deliveryAddress=" + deliveryAddress +
                '}';
    }
}
