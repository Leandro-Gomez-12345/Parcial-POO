package co.com.poo.shoppingcart.service;

import co.com.poo.shoppingcart.model.Order;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {
    private static final InMemoryOrderRepository INSTANCE = new InMemoryOrderRepository();
    private final Map<String, Order> orders = new HashMap<>();
    
    private InMemoryOrderRepository() {}
    
    public static InMemoryOrderRepository getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void saveOrder(Order order) {
        orders.put(order.getOrderId(), order);
    }
    
    @Override
    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }
}