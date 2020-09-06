package org.bolikov;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "cost")
    private Double cost;

    public Product() {
    }

    public Product(Long productId, String productName, Double cost) {
        this.productId = productId;
        this.productName = productName;
        this.cost = cost;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Double getCost() {
        return cost;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @ManyToMany(
            cascade = CascadeType.REMOVE,
            fetch=FetchType.EAGER
    )
    @JoinTable(
            name = "market",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    public List<Customer> customers;

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", cost=" + cost +
                '}';
    }
}
