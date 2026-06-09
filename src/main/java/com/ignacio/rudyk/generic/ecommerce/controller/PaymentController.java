package com.ignacio.rudyk.generic.ecommerce.controller;

import com.ignacio.rudyk.generic.ecommerce.dto.NewPaymentDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.ResponseDTO;
import com.ignacio.rudyk.generic.ecommerce.service.IPaymentService;
import com.ignacio.rudyk.generic.ecommerce.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private IPaymentService paymentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment")
    private ResponseEntity<ResponseDTO> createPayment(HttpServletRequest httpServletRequest, @RequestBody NewPaymentDTO newPayment) {
        LOGGER.info("Llamado al servicio /create-payment - body: {}", newPayment);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, paymentService.createPayment(newPayment));
    }

    @PostMapping("/refund-payment/{id}")
    private ResponseEntity<ResponseDTO> refundPayment(HttpServletRequest httpServletRequest, @PathVariable Long id) {
        LOGGER.info("Llamado al servicio /refund-payment - ID: {}", id);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, null);
    }

    @GetMapping("/by-id/{id}")
    private ResponseEntity<ResponseDTO> getPaymentById(HttpServletRequest httpServletRequest, @PathVariable Long id) {
        LOGGER.info("Llamado al servicio /get-payment-by-id - ID: {}", id);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, paymentService.getPayment(id));
    }

    @GetMapping("/by-user-id/{user-id}")
    private ResponseEntity<ResponseDTO> getPaymentByUser(HttpServletRequest httpServletRequest, @PathVariable("user-id") Long userId) {
        LOGGER.info("Llamado al servicio /get-payment-by-user - userId: {}", userId);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, paymentService.getPaymentsByUserId(userId));
    }

}