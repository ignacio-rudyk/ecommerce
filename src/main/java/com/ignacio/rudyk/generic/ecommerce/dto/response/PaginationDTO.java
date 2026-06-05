package com.ignacio.rudyk.generic.ecommerce.dto.response;

public record PaginationDTO(int page, int size, long totalElements, int totalPages) {
}
