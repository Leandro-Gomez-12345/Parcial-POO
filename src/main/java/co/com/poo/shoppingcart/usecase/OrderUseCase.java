package co.com.poo.shoppingcart.usecase;

import co.com.poo.shoppingcart.model.Cart;
import co.com.poo.shoppingcart.model.Order;
import co.com.poo.shoppingcart.service.CartRepository;
import java.util.HashMap;
import java.util.Map;

public class OrderUseCase {
    private final CartRepository cartRepository;
    private final Map<String, Order> orders; // Almacenamiento interno de órdenes

    public OrderUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.orders = new HashMap<>(); // Inicializamos el mapa de órdenes
    }

    public Order createOrder() {
        Cart cart = cartRepository.getCart();
        double subtotal = cart.getTotalAmount();

        // Aplicar descuento del 5% si el total es mayor a $100,000
        double discount = 0.0;
        if (subtotal > 100000) {
            discount = subtotal * 0.05; // 5% de descuento
        }

        Order order = new Order(cart.getItems(), subtotal, discount);
        orders.put(order.getOrderId(), order); // Guardamos la orden en el mapa interno
        return order;
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId); // Obtenemos la orden del mapa interno
    }

    public double calculateTotal(Order order) {
        return order.getFinalTotal();
    }
}