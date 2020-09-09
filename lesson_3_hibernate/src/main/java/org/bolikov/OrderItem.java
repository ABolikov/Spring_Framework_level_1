package org.bolikov;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long orderItemId;

    @ManyToOne()
    private Customer customer;

    @ManyToOne()
    private Product product;

    private BigDecimal cost;

    public OrderItem() {
    }

    public OrderItem(Long orderItemId, Customer customerId, Product productId, BigDecimal cost) {
        this.orderItemId = orderItemId;
        this.customer = customerId;
        this.product = productId;
        this.cost = cost;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setOrderItemId(Long marketId) {
        this.orderItemId = marketId;
    }

    public void setCustomerId(Customer customer) {
        this.customer = customer;
    }

    public void setProductId(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "customer=" + customer +
                ", product=" + product +
                ", cost=" + cost +
                '}';
    }
}
