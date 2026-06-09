package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.dto.GatewayRedirectURLDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.NewPaymentDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.PaymentDTO;

import java.util.List;

public interface IPaymentService {

    GatewayRedirectURLDTO createPayment(NewPaymentDTO newPayment);

    void refundPayment(Long paymentId);

    PaymentDTO getPayment(Long paymentId);

    List<PaymentDTO> getPaymentsByUserId(Long userId);

}
