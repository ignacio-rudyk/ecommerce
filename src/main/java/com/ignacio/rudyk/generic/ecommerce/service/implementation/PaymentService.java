package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.GatewayRedirectURLDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.NewPaymentDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.PaymentDTO;
import com.ignacio.rudyk.generic.ecommerce.enumerate.OrderStateEnum;
import com.ignacio.rudyk.generic.ecommerce.enumerate.PaymentStateEnum;
import com.ignacio.rudyk.generic.ecommerce.exception.DataNotFoundException;
import com.ignacio.rudyk.generic.ecommerce.mapper.IPaymentMapper;
import com.ignacio.rudyk.generic.ecommerce.repository.IPaymentRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Payment;
import com.ignacio.rudyk.generic.ecommerce.service.IOrderService;
import com.ignacio.rudyk.generic.ecommerce.service.IPaymentMethodService;
import com.ignacio.rudyk.generic.ecommerce.service.IPaymentService;
import com.ignacio.rudyk.generic.ecommerce.service.IPaymentStateService;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    private IPaymentRepository paymentRepository;

    private IOrderService orderService;

    private IPaymentStateService paymentStateService;

    private IPaymentMethodService paymentMethodService;

    private IPaymentMapper paymentMapper;

    public PaymentService(IPaymentRepository paymentRepository,
                          IOrderService orderService,
                          IPaymentStateService paymentStateService,
                          IPaymentMethodService paymentMethodService,
                          IPaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.paymentStateService = paymentStateService;
        this.paymentMethodService = paymentMethodService;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
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
        orderService.updateOrderState(newPayment.orderId(), OrderStateEnum.FINALIZADA.getCode());
        return new GatewayRedirectURLDTO("www.gatewayredirecturl.com");
    }

    @Override
    @Transactional
    public void refundPayment(Long paymentId) {
        Payment payment = findById(paymentId);
        payment.setLastModification(new Date());
        payment.setPaymentState(paymentStateService.findByCode(PaymentStateEnum.REEMBOLSADO.getCode()));
        orderService.updateOrderState(payment.getOrderId(), OrderStateEnum.REEMBOLSADA.getCode());
        paymentRepository.save(payment);
    }

    @Override
    public PaymentDTO getPayment(Long paymentId) {
        return paymentMapper.toDTO(findById(paymentId));
    }

    @Override
    public List<PaymentDTO> getPaymentsByUserId(Long userId) {
        List<Payment> paymentList = paymentRepository.findByUserId(userId);
        return paymentList.stream().map(e -> paymentMapper.toDTO(e)).toList();
    }

    private Payment findById(Long paymentId) {
        Optional<Payment> paymentOp = paymentRepository.findById(paymentId);
        if (paymentOp.isEmpty())
            throw new DataNotFoundException("Pago no encontrado");
        return paymentOp.get();
    }

}