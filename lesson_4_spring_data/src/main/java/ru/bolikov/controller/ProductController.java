package ru.bolikov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bolikov.entity.Product;
import ru.bolikov.repositories.ProductRepository;

import java.util.Optional;

@Controller
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final static Integer itemCount = 5;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String redirectProduct() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String allProducts(Model model,
                              @RequestParam(value = "page") Optional<Integer> page,
                              @RequestParam(value = "title", required = false) String name,
                              @RequestParam(value = "cost_min", required = false) Integer cost_min,
                              @RequestParam(value = "cost_max", required = false) Integer cost_max) {
        Page<Product> products;
        int currentPage = page.orElse(1);
        PageRequest pageRequest = PageRequest.of(currentPage -1, itemCount);
        if ((name == null || name.isEmpty()) & cost_min == null & cost_max == null) {
            products =  productRepository.findAll(pageRequest);
        } else {
            if (name != null & cost_min == null & cost_max == null) {
                products = productRepository.findByTitleLikeOrderByIdAsc("%" + name + "%", pageRequest);
                logger.info("Filter like %" + name + "%");
            } else if (name != null & cost_min != null & cost_max == null) {
                products = productRepository.findByTitleLikeAndCostGreaterThanEqualOrderByIdAsc("%" + name + "%", cost_min, pageRequest);
                logger.info("Filter like %" + name + "% and >= " + cost_min);
            } else if (name != null & cost_min == null & cost_max != null) {
                products = productRepository.findByTitleLikeAndCostLessThanEqualOrderByIdAsc("%" + name + "%", cost_max, pageRequest);
                logger.info("Filter like %" + name + "% and <= " + cost_max);
            } else if (name != null & cost_min != null & cost_max != null) {
                products = productRepository.findByTitleLikeAndCostGreaterThanEqualAndCostLessThanEqualOrderByIdAsc("%" + name + "%", cost_min, cost_max, pageRequest);
                logger.info("Filter like %" + name + "% and >= " + cost_min + " and <= " + cost_max);
            } else if (cost_min != null & cost_max == null) {
                products = productRepository.findByCostGreaterThanEqualOrderByIdAsc(cost_min, pageRequest);
                logger.info("Filter >= " + cost_min);
            } else if (cost_min == null) {
                products = productRepository.findByCostLessThanEqualOrderByIdAsc(cost_max, pageRequest);
                logger.info("Filter <= " + cost_max);
            } else {
                products = productRepository.findByCostGreaterThanEqualAndCostLessThanEqualOrderByIdAsc(cost_min, cost_max, pageRequest);
                logger.info("Filter >= " + cost_min + " and <= " + cost_max);
            }
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
