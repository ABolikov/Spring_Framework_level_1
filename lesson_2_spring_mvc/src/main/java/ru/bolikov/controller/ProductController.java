package ru.bolikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bolikov.products.Product;
import ru.bolikov.products.ProductRepository;

import java.util.Map;

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
        Map<Integer, Product> products = productRepository.getAllProduct();
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

    @PostMapping("/product/add")
    public String addProduct(Product product) {
        if (product.getId() != null) {
            productRepository.editProduct(product.getId(), product);
        } else {
            productRepository.addProduct(product);
        }
        return "redirect:/index";
    }

    @GetMapping("/product/{id}/edit")
    public String getProduct(@PathVariable("id") Integer id, Model model) {
        Map<Integer, Product> products = productRepository.getAllProduct();
        model.addAttribute("product", products.get(id));
        return "product";
    }

    @DeleteMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) {
        Map<Integer, Product> products = productRepository.getAllProduct();
        products.remove(id);
        return "redirect:/index";
    }
}
