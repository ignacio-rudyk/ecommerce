package com.ignacio.rudyk.generic.ecommerce.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record CartDTO(Long id,
                      BigDecimal totalAmount,
                      BigDecimal subTotalAmount,
                      Date lastModification,
                      List<CartProductDTO> products,
                      Long userId) {

    public CartDTO withProducts(List<CartProductDTO> products) {
        return new CartDTO(id, totalAmount, subTotalAmount, lastModification, products, userId);
    }

}