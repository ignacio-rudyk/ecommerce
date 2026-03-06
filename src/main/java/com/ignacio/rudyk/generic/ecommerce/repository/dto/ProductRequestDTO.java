package com.ignacio.rudyk.generic.ecommerce.repository.dto;

import java.math.BigDecimal;

public record ProductRequestDTO(String title, BigDecimal price, BigDecimal costPrice, String description, String shortDescription, Long categoryId, String base64Image, String urlImage) {
}