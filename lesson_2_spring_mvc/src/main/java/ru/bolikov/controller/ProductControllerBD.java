package ru.bolikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bolikov.products.Product;
import ru.bolikov.products.ProductRepository;
import ru.bolikov.products.ProductRepositoryDB;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ProductControllerBD {

    @Autowired
    private ProductRepositoryDB productRepositoryDB;

    private Product currentProduct = null;

    @GetMapping("/")
    public String redirectProduct() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String allProducts(Model model) throws SQLException {
        List<Product> products = productRepositoryDB.getAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("add")
    public String addProductRedirect() {
        return "redirect:/product";
    }

    @GetMapping("/product")
    public String formAddProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/add")
    public String addProduct(Product product) throws SQLException {
        if (currentProduct != null) {
                    productRepositoryDB.update(product);
                    currentProduct = null;
        } else {
            productRepositoryDB.insert(product);
        }
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String getProduct(@PathVariable("id") Integer id, Model model) throws SQLException {
        currentProduct =  productRepositoryDB.findByProduct(id);
        model.addAttribute("product", currentProduct);
        return "product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) throws SQLException {
        productRepositoryDB.delete(id);
        return "redirect:/index";
    }
}
