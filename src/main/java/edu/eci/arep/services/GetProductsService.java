package edu.eci.arep.services;

import edu.eci.arep.utils.ShoppingListMemory;

import java.util.List;
import java.util.stream.Collectors;

public class GetProductsService implements RestService {

    private ShoppingListMemory cartService;

    public GetProductsService(ShoppingListMemory cartService) {
        this.cartService = cartService;
    }

    @Override
    public String response(String request) {
        List<String> products = cartService.getProducts();

        // Convertir la lista de productos en una cadena JSON válida
        String jsonProducts = products.stream()
                .map(product -> "\"" + product + "\"")
                .collect(Collectors.joining(","));
        return "{\"products\":[" + jsonProducts + "]}";
    }
}
