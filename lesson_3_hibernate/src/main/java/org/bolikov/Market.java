package org.bolikov;

import javax.persistence.*;

@Entity
@Table(name = "market")
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "product_id")
    private Long productId;

    public Market() {
    }

    public Market(Long marketId, Long customerId, Long productId) {
        this.marketId = marketId;
        this.customerId = customerId;
        this.productId = productId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
