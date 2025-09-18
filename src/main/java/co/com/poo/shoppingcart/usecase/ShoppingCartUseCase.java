package co.com.poo.shoppingcart.usecase;

import co.com.poo.shoppingcart.model.Cart;
import co.com.poo.shoppingcart.model.CartItem;
import co.com.poo.shoppingcart.model.Product;
import co.com.poo.shoppingcart.service.CartRepository;
import co.com.poo.shoppingcart.service.ProductRepository;

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

    public void updateQuantity(String productId, Integer quantity) {
        Cart cart = cartRepository.getCart();
        cart.updateItem(productId, quantity);
        cartRepository.saveCart(cart);
    }

    public void removeItem(String productId) {
        Cart cart = cartRepository.getCart();
        cart.removeItem(productId);
        cartRepository.saveCart(cart);
    }

    public Cart viewCart() {
        return cartRepository.getCart();
    }

    // Nuevo método para limpiar el carrito
    public void clearCart() {
        Cart cart = cartRepository.getCart();
        cart.clear();
        cartRepository.saveCart(cart);
    }
}