package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.dto.OrderDTO;

public interface IOrderService {

    void createOrder(Long cartId);

    OrderDTO getOrder(Long orderId);

    void updateOrderState(Long orderId, String orderStateCode);

}