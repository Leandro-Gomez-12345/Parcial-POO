package co.com.poo.shoppingcart.service;

import co.com.poo.shoppingcart.model.Order;

public interface OrderRepository {
    void saveOrder(Order order);
    Order getOrderById(String orderId);
}