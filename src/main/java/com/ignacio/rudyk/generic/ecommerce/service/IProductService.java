package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.dto.ProductDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.ProductRequestDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.PaginatedListDTO;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    void createProduct(ProductRequestDTO newProduct);

    ProductDTO findById(Long productId);

    PaginatedListDTO<ProductDTO> findAll(Pageable pageable);

    void updateProduct(ProductRequestDTO updateProduct);

    void deleteProduct(Long productId);

}