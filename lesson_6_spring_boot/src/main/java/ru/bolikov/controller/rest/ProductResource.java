package ru.bolikov.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bolikov.entity.Product;
import ru.bolikov.exception.NotFoundException;
import ru.bolikov.exception.rest.ProductErrorResponse;
import ru.bolikov.repositories.ProductRepository;
import ru.bolikov.specification.ProductSpecification;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Product findById(@PathVariable("id") int id) {
        return productRepository.findById(id).orElseThrow(new NotFoundException("Product not found", null));
    }

    @PostMapping(consumes = "application/json")
    public Product createProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("При создании нового пользователя параметр \"id\" не должен быть заполнен");
        }
        productRepository.save(product);
        return product;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Product updateProduct(@RequestBody Product product) {
        productRepository.save(product);
        return product;
    }

    @DeleteMapping(path = "/{id}/id", produces = "application/json")
    public void deleteById(@PathVariable("id") int id) {
        productRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> notFoundExceptionHandler(NotFoundException exc) {
        ProductErrorResponse studentsErrorResponse = new ProductErrorResponse();
        studentsErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        studentsErrorResponse.setMessage(exc.getMessage());
        studentsErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(studentsErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException exc) {
        ProductErrorResponse studentsErrorResponse = new ProductErrorResponse();
        studentsErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        studentsErrorResponse.setMessage(exc.getLocalizedMessage());
        studentsErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(studentsErrorResponse, HttpStatus.BAD_REQUEST);
    }


}
