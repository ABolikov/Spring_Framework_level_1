package ru.bolikov.products;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private Integer idProduct = 0;

    private Map<Integer,Product> products = new HashMap<>();

    public Map<Integer, Product> getAllProduct() {
        return products;
    }

    public void addProduct(Product product) {
        idProduct++;
        products.put(idProduct, product);
        product.setId(idProduct);
    }

    public void editProduct(int index, Product product) {
        products.put(index, product);
        product.setId(index);
    }

}
