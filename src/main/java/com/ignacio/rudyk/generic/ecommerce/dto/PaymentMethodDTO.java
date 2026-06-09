package com.ignacio.rudyk.generic.ecommerce.dto;

public record PaymentMethodDTO(Long id,
                               String code,
                               String name,
                               String title,
                               String description) {

}
