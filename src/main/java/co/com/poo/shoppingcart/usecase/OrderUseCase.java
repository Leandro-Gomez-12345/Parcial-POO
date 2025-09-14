package co.com.poo.shoppingcart.usecase;

import co.com.poo.shoppingcart.model.Cart;
import co.com.poo.shoppingcart.model.Order;
import co.com.poo.shoppingcart.service.CartRepository;
import java.util.HashMap;
import java.util.Map;

public class OrderUseCase {
    private final CartRepository cartRepository;
    private final Map<String, Order> orders; // Almacenamiento interno de órdenes
    
    // Constantes para la regla de descuento
    private static final double DISCOUNT_THRESHOLD = 100000.0;
    private static final double DISCOUNT_PERCENTAGE = 0.05; // 5%
    
    public OrderUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.orders = new HashMap<>(); // Inicializamos el mapa de órdenes
    }
    
    public Order createOrder() {
        Cart cart = cartRepository.getCart();
        double subtotal = cart.getTotalAmount();
        double discount = calculateDiscount(subtotal);
        
        Order order = new Order(cart.getItems(), subtotal, discount);
        orders.put(order.getOrderId(), order); // Guardamos la orden en el mapa interno
        return order;
    }
    
    /**
     * Calcula el descuento según la regla de negocio:
     * Si el total es mayor a $100.000, se aplica un 5% de descuento.
     * 
     * @param total El monto total del pedido
     * @return El valor del descuento a aplicar
     */
    private double calculateDiscount(double total) {
        if (total > DISCOUNT_THRESHOLD) {
            return total * DISCOUNT_PERCENTAGE;
        }
        return 0.0; // Sin descuento
    }
    
    public Order getOrder(String orderId) {
        return orders.get(orderId); // Obtenemos la orden del mapa interno
    }
    
    public double calculateTotal(Order order) {
        return order.getFinalTotal();
    }
}
