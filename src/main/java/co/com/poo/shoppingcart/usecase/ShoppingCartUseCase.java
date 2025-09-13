package co.com.poo.shoppingcart.usecase;

public class ShoppingCartUseCase {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    
    public ShoppingCartUseCase(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }
    
    public void addProductToCart(String productId, Integer quantity) {
        Product product = productRepository.getProductById(Integer.parseInt(productId));
        if (product != null) {
            Cart cart = cartRepository.getCart();
            cart.addItem(new CartItem(product, quantity));
            cartRepository.saveCart(cart);
        }
    }
    
    // Implementar los demás métodos según el diagrama
    // updateQuantity, removeItem, viewCart, etc.
}
