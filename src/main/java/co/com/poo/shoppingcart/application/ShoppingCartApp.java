package co.com.poo.shoppingcart.application;

import co.com.poo.shoppingcart.service.CartRepository;
import co.com.poo.shoppingcart.service.InMemoryCartManager;
import co.com.poo.shoppingcart.service.ProductRepository;
import co.com.poo.shoppingcart.service.FileProductManager;
import co.com.poo.shoppingcart.service.OrderRepository;
import co.com.poo.shoppingcart.usecase.ShoppingCartUseCase;
import co.com.poo.shoppingcart.usecase.ProductCatalogUseCase;
import co.com.poo.shoppingcart.usecase.OrderUseCase;

import java.util.Scanner;

public class ShoppingCartApp {
    public static void main(String[] args) {
        // Inicializar repositorios
        CartRepository cartRepository = InMemoryCartManager.getInstance();
        ProductRepository productRepository = FileProductManager.getInstance();
        
        // Inicializar casos de uso
        ShoppingCartUseCase shoppingCartUseCase = new ShoppingCartUseCase(cartRepository, productRepository);
        ProductCatalogUseCase productCatalogUseCase = new ProductCatalogUseCase(productRepository);
        
        // Para OrderUseCase, necesitarías implementar OrderRepository
        // OrderRepository orderRepository = new InMemoryOrderRepository();
        // OrderUseCase orderUseCase = new OrderUseCase(cartRepository, orderRepository);
        
        // Aquí podrías iniciar una interfaz de usuario simple
        startConsoleInterface(shoppingCartUseCase, productCatalogUseCase);
    }
    
    private static void startConsoleInterface(ShoppingCartUseCase shoppingCartUseCase, ProductCatalogUseCase productCatalogUseCase) {
        Scanner scanner = new Scanner(System.in);
        boolean activo = true;
        
        System.out.println("Bienvenido al Carrito de Compras");
        
        while (activo) {
            System.out.println("\n1. Ver productos\n2. Añadir producto al carrito\n3. Ver carrito\n4. Actualizar cantidad\n5. Eliminar producto\n6. Salir");
            System.out.print("Seleccione una opción: ");
            
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (option) {
                case 1:
                    System.out.println("\nProductos disponibles:");
                    productCatalogUseCase.getProductList().forEach(p -> 
                        System.out.println(p.getId() + ". " + p.getName() + " - $" + p.getPrice())
                    );
                    break;
                case 2:
                    System.out.print("Ingrese ID del producto: ");
                    String productId = scanner.nextLine();
                    System.out.print("Ingrese cantidad: ");
                    int quantity = scanner.nextInt();
                    shoppingCartUseCase.addProductToCart(productId, quantity);
                    System.out.println("Producto añadido al carrito.");
                    break;
                case 3:
                    System.out.println("\nCarrito actual:");
                    shoppingCartUseCase.viewCart().getItems().forEach(item -> 
                        System.out.println(item.getProduct().getName() + " - Cantidad: " + 
                                          item.getQuantity() + " - Subtotal: $" + item.getSubtotal())
                    );
                    System.out.println("Total: $" + shoppingCartUseCase.viewCart().getTotalAmount());
                    break;
                case 4:
                    System.out.print("Ingrese ID del producto: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Ingrese nueva cantidad: ");
                    int newQuantity = scanner.nextInt();
                    shoppingCartUseCase.updateQuantity(updateId, newQuantity);
                    System.out.println("Cantidad actualizada.");
                    break;
                case 5:
                    System.out.print("Ingrese ID del producto a eliminar: ");
                    String removeId = scanner.nextLine();
                    shoppingCartUseCase.removeItem(removeId);
                    System.out.println("Producto eliminado del carrito.");
                    break;
                case 6:
                    activo = false;
                    System.out.println("¡Gracias por usar nuestro carrito de compras!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        
        scanner.close();
    }
}