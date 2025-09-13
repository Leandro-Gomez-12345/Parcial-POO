package co.com.poo.shoppingcart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<CartItem> items = new ArrayList<>();
    private Double totalAmount = 0.0;

    public void addItem(CartItem item) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
        calculateTotal();
    }

    public void removeItem(String productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
        calculateTotal();
    }

    public void updateItem(String productId, Integer quantity) {
        if (quantity <= 0) {
            removeItem(productId);
            return;
        }
        for (CartItem item : items) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        calculateTotal();
    }

    public Double calculateTotal() {
        totalAmount = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        return totalAmount;
    }

    public void clear() {
        items.clear();
        totalAmount = 0.0;
    }

    public Boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Integer getItemCount() {
        return items.size();
    }
}
