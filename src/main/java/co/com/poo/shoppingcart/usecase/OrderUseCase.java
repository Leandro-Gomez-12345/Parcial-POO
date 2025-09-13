package co.com.poo.shoppingcart.usecase;

import co.com.poo.shoppingcart.model.Cart;
import co.com.poo.shoppingcart.model.Order;
import co.com.poo.shoppingcart.service.CartRepository;
import co.com.poo.shoppingcart.service.OrderRepository;

public class OrderUseCase {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository; // Necesitarías crear esta interfaz
    
    public OrderUseCase(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }
    
    public Order createOrder() {
        Cart cart = cartRepository.getCart();
        Order order = new Order(cart.getItems(), cart.getTotalAmount(), 0.0); // Sin descuento por ahora
        orderRepository.saveOrder(order);
        return order;
    }
    
    public Order getOrder(String orderId) {
        return orderRepository.getOrderById(orderId);
    }
    
    public double calculateTotal(Order order) {
        return order.getFinalTotal();
    }
}
