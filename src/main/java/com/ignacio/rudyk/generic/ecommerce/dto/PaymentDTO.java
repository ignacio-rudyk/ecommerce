package com.ignacio.rudyk.generic.ecommerce.dto;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public record PaymentDTO(Long id,
    Long orderId,
    Date createdAt,
    Date lastModification,
    BigDecimal amount,
    Currency currency,
    PaymentStateDTO paymentState,
    PaymentMethodDTO paymentMethod) {
}
