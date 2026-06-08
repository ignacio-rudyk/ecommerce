package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.GatewayRedirectURLDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.NewPaymentDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.PaymentDTO;
import com.ignacio.rudyk.generic.ecommerce.enumerate.PaymentStateEnum;
import com.ignacio.rudyk.generic.ecommerce.repository.IPaymentRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Payment;
import com.ignacio.rudyk.generic.ecommerce.service.IOrderService;
import com.ignacio.rudyk.generic.ecommerce.service.IPaymentMethodService;
import com.ignacio.rudyk.generic.ecommerce.service.IPaymentService;
import com.ignacio.rudyk.generic.ecommerce.service.IPaymentStateService;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    private IPaymentRepository paymentRepository;

    private IOrderService orderService;

    private IPaymentStateService paymentStateService;

    private IPaymentMethodService paymentMethodService;

    public PaymentService(IPaymentRepository paymentRepository,
                          IOrderService orderService,
                          IPaymentStateService paymentStateService,
                          IPaymentMethodService paymentMethodService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.paymentStateService = paymentStateService;
        this.paymentMethodService = paymentMethodService;
    }

    @Override
    public GatewayRedirectURLDTO createPayment(NewPaymentDTO newPayment) {
        Payment payment = new Payment();
        payment.setOrderId(newPayment.orderId());
        payment.setCreatedAt(new Date());
        payment.setAmount(orderService.getOrder(newPayment.orderId()).totalAmount());
        payment.setCurrency(Currency.getInstance(newPayment.currencyCode()));
        payment.setPaymentState(paymentStateService.findByCode(PaymentStateEnum.PENDIENTE.getCode()));
        payment.setPaymentMethod(paymentMethodService.findById(newPayment.paymentMethodId()));
        payment.setLastModification(new Date());
        paymentRepository.save(payment);
        return new GatewayRedirectURLDTO("www.gatewayredirecturl.com");
    }

    @Override
    public void refundPayment(Long paymentId) {
        //TODO
    }

    @Override
    public PaymentDTO getPayment(Long paymentId) {
        return null;//TODO
    }

    @Override
    public List<PaymentDTO> getPaymentsByUserId(Long paymentId) {
        return List.of();//TODO
    }

}