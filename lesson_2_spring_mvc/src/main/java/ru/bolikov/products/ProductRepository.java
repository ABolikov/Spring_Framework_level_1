package ru.bolikov.products;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> products = new ArrayList<>();

    public List<Product> getAllProduct() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        product.setId(products.size());
    }

    public void removeProduct(int index, Product product) {
        products.add(index, product);
        product.setId(products.size());
    }

}
