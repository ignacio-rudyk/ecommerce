package com.ignacio.rudyk.ecommerce.repository.entities;

import java.math.BigDecimal;
import java.util.List;

public class Cart {

    private Integer id;

    private List<Product> productList;

    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}