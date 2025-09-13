package co.com.poo.shoppingcart.application;

public class ShoppingCartApp {
    public static void main(String[] args) {
        // Inicializar repositorios
        CartRepository cartRepository = InMemoryCartManager.getInstance();
        ProductRepository productRepository = FileProductManager.getInstance();
        
        // Inicializar casos de uso
        ShoppingCartUseCase shoppingCartUseCase = new ShoppingCartUseCase(cartRepository, productRepository);
        ProductCatalogUseCase productCatalogUseCase = new ProductCatalogUseCase(productRepository);
        OrderUseCase orderUseCase = new OrderUseCase(cartRepository);
        
        // Aquí podrías iniciar una interfaz de usuario o un servidor web
        // para interactuar con los casos de uso
    }
}