package edu.eci.arep.utils;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListMemory {
    private List<String> products = new ArrayList<>();

    public void addProduct(String product) {
        products.add(product);
    }

    public List<String> getProducts() {
        return products;
    }

    public void deleteProduct(String product){
        if(products.contains(product)){
            products.remove(product);
        }
    }
}
