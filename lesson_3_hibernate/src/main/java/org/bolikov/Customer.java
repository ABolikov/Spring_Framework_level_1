package org.bolikov;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch=FetchType.EAGER
    )
    public List<OrderItem> orderItems;

    public Customer() {
    }

    public Customer(Long customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                '}';
    }
}
