package co.com.poo.shoppingcart.usecase;

public class OrderUseCase {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository; // Necesitarías crear esta interfaz
    
    public OrderUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    
    public Order createOrder() {
        Cart cart = cartRepository.getCart();
        Order order = new Order(cart.getItems(), cart.getTotalAmount(), 0.0); // Sin descuento por ahora
        // Aquí podrías guardar la orden en un repositorio
        return order;
    }
    
    // Implementar los demás métodos según el diagrama
}
