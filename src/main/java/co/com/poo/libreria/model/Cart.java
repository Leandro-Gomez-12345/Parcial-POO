package co.com.poo.libreria.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    public Double totalAmount;

    public Cart(){
        this.items = new ArrayList<>();
    }

    public void addItem(){}

    public void removeItem(){}

    public void calculateTotal(){}

    public void clear(){}
}
