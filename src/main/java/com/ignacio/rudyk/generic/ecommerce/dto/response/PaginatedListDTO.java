package com.ignacio.rudyk.generic.ecommerce.dto.response;

import java.util.List;

public record PaginatedListDTO<T>(List<T> content, PaginationDTO pagination) {
}
