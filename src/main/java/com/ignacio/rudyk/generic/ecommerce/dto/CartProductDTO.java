package com.ignacio.rudyk.generic.ecommerce.dto;

public record CartProductDTO(Long id,
                             Long cartId,
                             ProductDTO product,
                             Long quantity) {
}
