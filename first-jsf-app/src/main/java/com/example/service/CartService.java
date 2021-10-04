package com.example.service;

import com.example.service.dto.LineItem;
import com.example.service.dto.ProductDto;

import javax.ejb.Stateful;
import java.util.*;

@Stateful
public class CartService {

    private final Map<LineItem, Integer> lineItems = new HashMap<>();

    public List<LineItem> findAll() {
        lineItems.forEach((lineItem, qty) -> lineItem.setQty(qty));
        ArrayList<LineItem> arr = new ArrayList<>(lineItems.keySet());
        System.out.println(arr);
        return arr;
    }

    public void addToCart(ProductDto product, Integer qty) {
        // TODO
        Optional<Map.Entry<LineItem, Integer>> lineItem = lineItems.entrySet().stream()
                .filter(item -> item.getKey().getProduct().getId().equals(product.getId()))
                .findFirst();
        if(lineItem.isPresent()) {
            lineItem.get().setValue(lineItem.get().getValue() + 1);
        }else {
            lineItems.putIfAbsent(new LineItem(product, qty, product.getPrice()), qty);
        }
    }

    public void removeProduct(ProductDto product) {
        // TODO
        LineItem lineItem = lineItems.entrySet().stream()
                .filter(item -> item.getKey().getProduct().getId().equals(product.getId()))
                .findFirst().get().getKey();
        if(lineItems.get(lineItem) > 1) {
            lineItems.put(lineItem, lineItems.get(lineItem) - 1);
        } else {
            lineItems.remove(lineItem);
        }
    }
}
