package org.bolikov;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal cost;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch=FetchType.EAGER
    )
    public List<OrderItem> orderItems;

    public Product() {
    }

    public Product(Long productId, String productName, BigDecimal cost) {
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

    public BigDecimal getCost() {
        return cost;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /*@ManyToMany(
            cascade = CascadeType.REMOVE,
            fetch=FetchType.EAGER
    )
    @JoinTable(
            name = "market",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )*/


    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", cost=" + cost +
                '}';
    }
}
