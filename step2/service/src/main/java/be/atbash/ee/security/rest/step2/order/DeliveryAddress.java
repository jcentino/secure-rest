package be.atbash.ee.security.rest.step2.order;

/**
 *
 */

public class DeliveryAddress {

    private String address;
    private String city;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "DeliveryAddress{"
                + "address='" + address + '\''
                + ", city='" + city + '\'' +
                '}';
    }
}
