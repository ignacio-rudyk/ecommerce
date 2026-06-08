package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.dto.CartDTO;

public interface ICartService {

    void createCart(Long userId);

    void deleteCart(Long userId);

    void emptyCart(Long cartId);

    CartDTO getCart(Long cartId);

    void addProduct(Long cartId, Long productId);

    void deleteProduct(Long cartId, Long productId);

}