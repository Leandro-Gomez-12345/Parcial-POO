package co.com.poo.shoppingcart.application;

import co.com.poo.shoppingcart.service.CartRepository;
import co.com.poo.shoppingcart.service.InMemoryCartManager;
import co.com.poo.shoppingcart.service.ProductRepository;
import co.com.poo.shoppingcart.service.FileProductManager;
import co.com.poo.shoppingcart.model.Order;
import co.com.poo.shoppingcart.usecase.OrderUseCase;
import co.com.poo.shoppingcart.usecase.ProductCatalogUseCase;
import co.com.poo.shoppingcart.usecase.ShoppingCartUseCase;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ShoppingCartApp {
    private static boolean orderClosed = false; // Para controlar si el pedido está cerrado

    public static void main(String[] args) {
        // Inicializar repositorios
        CartRepository cartRepository = InMemoryCartManager.getInstance();
        ProductRepository productRepository = FileProductManager.getInstance();

        // Inicializar casos de uso
        co.com.poo.shoppingcart.usecase.ShoppingCartUseCase shoppingCartUseCase = new co.com.poo.shoppingcart.usecase.ShoppingCartUseCase(cartRepository, productRepository);
        ProductCatalogUseCase productCatalogUseCase = new ProductCatalogUseCase(productRepository);
        OrderUseCase orderUseCase = new OrderUseCase(cartRepository);

        // Aquí podrías iniciar una interfaz de usuario simple
        startConsoleInterface(shoppingCartUseCase, productCatalogUseCase, orderUseCase);
    }

    private static void startConsoleInterface(ShoppingCartUseCase shoppingCartUseCase,
                                              ProductCatalogUseCase productCatalogUseCase,
                                              OrderUseCase orderUseCase) {
        Scanner scanner = new Scanner(System.in);
        boolean activo = true;
        String currentOrderId = null; // Para almacenar el ID de la orden actual

        System.out.println("Bienvenido al Carrito de Compras");

        while (activo) {
            System.out.println("\n1. Ver catálogo de productos\n2. Añadir producto al carrito\n3. Ver carrito\n" +
                    "4. Actualizar cantidad\n5. Eliminar producto\n6. Cerrar pedido\n7. Cancelar pedido\n8. Ver orden\n9. Salir");

            int opcion;
            // Se pide la opcion al usuario y se verifica que sea un numero entero válido
            while (true){
                try {
                    System.out.print("Seleccione una opción (1-9): ");
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea

                    // Validar que la opción esté en el rango válido
                    if (opcion >= 1 && opcion <= 9) {
                        break; // Salir del bucle si la entrada es válida
                    } else {
                        System.out.println("Opción no válida. Por favor seleccione un número entre 1 y 9.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor ingrese un número.");
                    scanner.nextLine(); // Limpiar el buffer del scanner
                }
            }

            switch (opcion) {
                case 1:
                    System.out.println("\nCatálogo de productos disponibles:");
                    productCatalogUseCase.getProductList().forEach(p ->
                            System.out.println(p.getId() + ". " + p.getName() + " - Categoría: " + p.getCategory() + " - $" + p.getPrice())
                    );
                    break;

                case 2:
                    if (orderClosed) {
                        System.out.println("No se pueden agregar productos. El pedido ya está cerrado.");
                        break;
                    }

                    int productId;
                    // Validación del ID del producto con try-catch
                    while (true) {
                        try {
                            System.out.print("Ingrese ID del producto (1-15): ");
                            String input = scanner.nextLine();
                            productId = Integer.parseInt(input);

                            if (productId >= 1 && productId <= 15) {
                                // Verificar que el producto existe
                                if (productCatalogUseCase.getProductById(String.valueOf(productId)) != null) {
                                    break; // ID válido y producto existe
                                } else {
                                    System.out.println("El producto con ID " + productId + " no existe.");
                                }
                            } else {
                                System.out.println("El ID debe estar entre 1 y 15.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Solo puede ingresar un número entero válido.");
                        }
                    }

                    int quantity;
                    // Validación de la cantidad con try-catch
                    while (true) {
                        try {
                            System.out.print("Ingrese cantidad: ");
                            String quantityInput = scanner.nextLine();
                            quantity = Integer.parseInt(quantityInput);

                            if (quantity >= 1) {
                                break; // Cantidad válida
                            } else {
                                System.out.println("La cantidad debe ser mayor o igual a 1.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Solo puede ingresar un número entero válido.");
                        }
                    }

                    shoppingCartUseCase.addProductToCart(String.valueOf(productId), quantity);
                    System.out.println("Producto añadido al carrito.");
                    break;

                case 3:
                    if (shoppingCartUseCase.viewCart().isEmpty()) {
                        System.out.println("\nEl carrito está vacío.");
                    } else {
                        System.out.println("\nCarrito actual:");
                        shoppingCartUseCase.viewCart().getItems().forEach(item ->
                                System.out.println(item.getProduct().getName() + " - Cantidad: " +
                                        item.getQuantity() + " - Subtotal: $" + item.getSubtotal())
                        );
                        System.out.println("Total: $" + shoppingCartUseCase.viewCart().getTotalAmount());
                    }
                    break;

                case 4:
                    if (orderClosed) {
                        System.out.println("No se puede actualizar el carrito. El pedido ya está cerrado.");
                        break;
                    }

                    if (shoppingCartUseCase.viewCart().isEmpty()) {
                        System.out.println("El carrito está vacío. No hay productos para actualizar.");
                        break;
                    }

                    int updateProductId;
                    // Validación del ID para actualizar
                    while (true) {
                        try {
                            System.out.print("Ingrese ID del producto a actualizar (1-15): ");
                            String updateInput = scanner.nextLine();
                            updateProductId = Integer.parseInt(updateInput);

                            if (updateProductId >= 1 && updateProductId <= 15) {
                                if (productCatalogUseCase.getProductById(String.valueOf(updateProductId)) != null) {
                                    break;
                                } else {
                                    System.out.println("El producto con ID " + updateProductId + " no existe.");
                                }
                            } else {
                                System.out.println("El ID debe estar entre 1 y 15.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Solo puede ingresar un número entero válido.");
                        }
                    }

                    int newQuantity;
                    // Validación de la nueva cantidad
                    while (true) {
                        try {
                            System.out.print("Ingrese nueva cantidad: ");
                            String newQuantityInput = scanner.nextLine();
                            newQuantity = Integer.parseInt(newQuantityInput);

                            if (newQuantity >= 0) {
                                break;
                            } else {
                                System.out.println("La cantidad debe ser mayor o igual a 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Solo puede ingresar un número entero válido.");
                        }
                    }

                    shoppingCartUseCase.updateQuantity(String.valueOf(updateProductId), newQuantity);
                    if (newQuantity == 0) {
                        System.out.println("Producto eliminado del carrito.");
                    } else {
                        System.out.println("Cantidad actualizada.");
                    }
                    break;

                case 5:
                    if (orderClosed) {
                        System.out.println("No se puede modificar el carrito. El pedido ya está cerrado.");
                        break;
                    }

                    if (shoppingCartUseCase.viewCart().isEmpty()) {
                        System.out.println("El carrito está vacío. No hay productos para eliminar.");
                        break;
                    }

                    int removeProductId;
                    // Validación del ID para eliminar
                    while (true) {
                        try {
                            System.out.print("Ingrese ID del producto a eliminar (1-15): ");
                            String removeInput = scanner.nextLine();
                            removeProductId = Integer.parseInt(removeInput);

                            if (removeProductId >= 1 && removeProductId <= 15) {
                                if (productCatalogUseCase.getProductById(String.valueOf(removeProductId)) != null) {
                                    break;
                                } else {
                                    System.out.println("El producto con ID " + removeProductId + " no existe.");
                                }
                            } else {
                                System.out.println("El ID debe estar entre 1 y 15.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Solo puede ingresar un número entero válido.");
                        }
                    }

                    shoppingCartUseCase.removeItem(String.valueOf(removeProductId));
                    System.out.println("Producto eliminado del carrito.");
                    break;

                case 6: // Cerrar pedido
                    if (orderClosed) {
                        System.out.println("Ya existe un pedido cerrado. Use la opción 'Cancelar pedido' primero si desea crear uno nuevo.");
                        break;
                    }

                    // Validar que existan productos en el carrito
                    if (shoppingCartUseCase.viewCart().isEmpty()) {
                        System.out.println("No se puede cerrar el pedido. El carrito está vacío.");
                        break;
                    }

                    Order newOrder = orderUseCase.createOrder();
                    currentOrderId = newOrder.getOrderId();
                    orderClosed = true;

                    // Mostrar resumen completo del pedido
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    System.out.println("\nResumen del pedido:");
                    System.out.println("ID del pedido: " + currentOrderId);
                    System.out.println("Fecha/Hora: " + dateFormat.format(newOrder.getDate()));
                    System.out.println("\nLista de ítems:");
                    newOrder.getItems().forEach(item ->
                            System.out.println("- " + item.getProduct().getName() +
                                    " | Cantidad: " + item.getQuantity() +
                                    " | Precio unitario: $" + item.getProduct().getPrice() +
                                    " | Subtotal: $" + item.getSubtotal())
                    );
                    System.out.println("\nTotal antes de descuento: $" + newOrder.getSubtotal());
                    System.out.println("Descuento aplicado: $" + newOrder.getDiscount());
                    System.out.println("TOTAL FINAL: $" + newOrder.getFinalTotal());

                    // Vaciar el carrito después del cierre
                    shoppingCartUseCase.clearCart();
                    System.out.println("\nPedido cerrado exitosamente. El carrito ha sido vaciado.");
                    break;

                case 7: // Cancelar pedido
                    if (!orderClosed) {
                        // Si no hay pedido cerrado, solo vaciar el carrito
                        if (shoppingCartUseCase.viewCart().isEmpty()) {
                            System.out.println("No hay pedido para cancelar. El carrito ya está vacío.");
                        } else {
                            shoppingCartUseCase.clearCart();
                            System.out.println("Carrito vaciado.");
                        }
                    } else {
                        // Si hay un pedido cerrado, permitir cancelarlo
                        orderClosed = false;
                        currentOrderId = null;
                        shoppingCartUseCase.clearCart();
                        System.out.println("Pedido cancelado. Puede crear un nuevo pedido.");
                    }
                    break;

                case 8: // Ver orden
                    if (currentOrderId != null && orderClosed) {
                        Order order = orderUseCase.getOrder(currentOrderId);
                        if (order != null) {
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            System.out.println("\nDetalles del pedido:");
                            System.out.println("ID del pedido: " + currentOrderId);
                            System.out.println("Fecha/Hora: " + dateFormat2.format(order.getDate()));
                            System.out.println("\nProductos:");
                            order.getItems().forEach(item ->
                                    System.out.println("- " + item.getProduct().getName() +
                                            " | Cantidad: " + item.getQuantity() +
                                            " | Subtotal: $" + item.getSubtotal())
                            );
                            System.out.println("\nSubtotal: $" + order.getSubtotal());
                            System.out.println("Descuento: $" + order.getDiscount());
                            System.out.println("Total final: $" + order.getFinalTotal());
                        } else {
                            System.out.println("No se encontró la orden.");
                        }
                    } else {
                        System.out.println("No hay pedido cerrado para mostrar.");
                    }
                    break;

                case 9:
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