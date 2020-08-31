package ru.bolikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bolikov.products.Product;
import ru.bolikov.products.ProductRepository;

import java.util.List;

//Класс контроллера для работы без БД

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String redirectProduct() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String allProducts(Model model) {
        List<Product> products = productRepository.getAllProduct();
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
    public String addProduct(Product product) {
        List<Product> products = productRepository.getAllProduct();
        if (product.getId() != null) {
            Product currentProduct = products.get(product.getId() - 1);
            currentProduct.setCost(product.getCost());
            currentProduct.setTitle(product.getTitle());
        } else {
            productRepository.addProduct(product);
        }
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String getProduct(@PathVariable("id") Integer id, Model model) {
        List<Product> products = productRepository.getAllProduct();
        model.addAttribute("product", products.get(id - 1));
        return "product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        List<Product> products = productRepository.getAllProduct();
        products.remove(id - 1);
        return "redirect:/index";
    }
}
