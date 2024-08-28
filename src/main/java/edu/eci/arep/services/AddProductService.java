
package edu.eci.arep.services;

import edu.eci.arep.utils.ShoppingListMemory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class AddProductService implements RestService {

    private ShoppingListMemory cartService;

    public AddProductService(ShoppingListMemory cartService) {
        this.cartService = cartService;
    }

    @Override
    public String response(String request) {

        String product = request.split("=")[1];
        product = product.replace("%20", " "); // Reemplazar espacios codificados con espacios normales

        product = java.net.URLDecoder.decode(product, java.nio.charset.StandardCharsets.UTF_8);

        if (cartService.getProducts().contains(product)) {
            return "{\"message\":\"Product already exists in the cart.\"}";
        } else {
            cartService.addProduct(product);
            return "{\"message\":\"Product added successfully.\"}";
        }
    }
}

