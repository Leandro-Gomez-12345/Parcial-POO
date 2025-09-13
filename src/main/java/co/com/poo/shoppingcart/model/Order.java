package co.com.poo.shoppingcart.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private Date date;
    private List<CartItem> items;
    private Double subtotal;
    private Double discount;
    private Double finalTotal;

    public Order(List<CartItem> items, Double subtotal, Double discount) {
        this.orderId = generateOrderId();
        this.date = new Date();
        this.items = items;
        this.subtotal = subtotal;
        this.discount = discount;
        this.finalTotal = subtotal - discount;
    }

    public String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(Double finalTotal) {
        this.finalTotal = finalTotal;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", items=" + items +
                ", subtotal=" + subtotal +
                ", discount=" + discount +
                ", finalTotal=" + finalTotal +
                '}';
    }
}
