package co.com.poo.shoppingcart.usecase;

import co.com.poo.shoppingcart.model.Product;
import co.com.poo.shoppingcart.service.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductCatalogUseCase {
    private final ProductRepository productRepository;
    
    public ProductCatalogUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> getProductList() {
        return productRepository.loadProducts();
    }
    
    public Product getProductById(String id) {
        return productRepository.getProductById(Integer.parseInt(id));
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.loadProducts().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}
