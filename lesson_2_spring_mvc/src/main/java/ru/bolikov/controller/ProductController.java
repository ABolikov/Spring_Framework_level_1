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

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    private Product currentProduct = null;

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
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/add")
    public String addProduct(Product product) {
        //TODO Стоить сделать проверку на наличие такого объекта
        if (currentProduct != null) {
            List<Product> products = productRepository.getAllProduct();
            for (Product pr : products) {
                if (pr.equals(currentProduct)) {
                    products.remove(pr);
                    productRepository.addProduct(product);
                    currentProduct= null;
                    break;
                }
            }
        } else {
            productRepository.addProduct(product);
        }
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String getProduct(@PathVariable("id") Integer id, Model model) {
        List<Product> products = productRepository.getAllProduct();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                currentProduct = product;
                break;
            }
        }
        model.addAttribute("product", currentProduct);
        return "product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        List<Product> products = productRepository.getAllProduct();
        for (int i = 0; i <= products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                break;
            }
        }
        return "redirect:/index";
    }
}
