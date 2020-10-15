package ru.bolikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bolikov.entity.Product;
import ru.bolikov.exception.NotFoundException;
import ru.bolikov.repositories.ProductRepository;
import ru.bolikov.entity.specification.ProductSpecification;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ProductController {
    private static String sort = "asc";
    private static String parameter_sort = "id";
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
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "cost_min", required = false) Integer cost_min,
                              @RequestParam(value = "cost_max", required = false) Integer cost_max,
                              @RequestParam(value = "sort_id", required = false) String sort_id,
                              @RequestParam(value = "sort_title", required = false) String sort_title,
                              @RequestParam(value = "sort_cost", required = false) String sort_cost) {
        int currentPage = page.orElse(1);
        PageRequest pageRequest = PageRequest.of(currentPage - 1, itemCount);
        Specification<Product> specification = ProductSpecification.trueLiteral();

        if (sort_id != null && !sort_id.isEmpty()) {
            sort = sort_id;
            parameter_sort = "id";
        } else if (sort_title != null && !sort_title.isEmpty()) {
            sort = sort_title;
            parameter_sort = "title";
        } else if (sort_cost != null && !sort_cost.isEmpty()) {
            sort = sort_cost;
            parameter_sort = "cost";
        }

        specification = specification.and(ProductSpecification.productOrderBy(sort, parameter_sort));

        if (title != null && !title.isEmpty()) {
            specification = specification.and(ProductSpecification.productTitleLike(title));
        }
        if (cost_min != null) {
            specification = specification.and(ProductSpecification.productCostGreaterThanOrEqualTo(cost_min));
        }
        if (cost_max != null) {
            specification = specification.and(ProductSpecification.productCostLessThanOrEqualTo(cost_max));
        }

        model.addAttribute("products", productRepository.findAll(specification, pageRequest));
        model.addAttribute("sort", sort);
        model.addAttribute("parameter_sort", "sort_" + parameter_sort);
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
    public String addProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product";
        }
        productRepository.save(product);
        return "redirect:/index";
    }

    @GetMapping("/product/{id}/edit")
    public String getProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(new NotFoundException(null, "Product"));
        model.addAttribute("product", product);
        return "product";
    }

    @DeleteMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productRepository.deleteById(id);
        return "redirect:/index";
    }
}
