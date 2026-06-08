package com.ignacio.rudyk.generic.ecommerce.controller;

import com.ignacio.rudyk.generic.ecommerce.dto.response.ResponseDTO;
import com.ignacio.rudyk.generic.ecommerce.service.IOrderService;
import com.ignacio.rudyk.generic.ecommerce.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private IOrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order/{cartId}")
    public ResponseEntity<ResponseDTO> createOrder(HttpServletRequest httpServletRequest, @PathVariable Long cartId) {
        LOGGER.info("Llamado al servicio /create-order - cart-id: {}", cartId);
        orderService.createOrder(cartId);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, null);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDTO> getOrder(HttpServletRequest httpServletRequest, @PathVariable Long orderId) {
        LOGGER.info("Llamado al servicio /get-order - ID: {}", orderId);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, orderService.getOrder(orderId));
    }

}