package ru.bolikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bolikov.entity.Product;
import ru.bolikov.repositories.ProductRepository;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String redirectProduct() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String allProducts(Model model, @RequestParam(value = "title", required = false) String name) {
        List<Product> products;
        if (name == null || name.isEmpty()) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByTitleLike("%" + name + "%");
        }
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
        productRepository.save(product);
        return "redirect:/index";
    }

    @GetMapping("/product/{id}/edit")
    public String getProduct(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "product";
    }

    @DeleteMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productRepository.deleteById(id);
        return "redirect:/index";
    }
}
