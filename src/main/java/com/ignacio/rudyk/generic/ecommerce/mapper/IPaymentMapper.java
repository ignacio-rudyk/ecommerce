package com.ignacio.rudyk.generic.ecommerce.mapper;

import org.mapstruct.Mapper;

import com.ignacio.rudyk.generic.ecommerce.dto.PaymentDTO;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Payment;

@Mapper(componentModel = "spring")
public interface IPaymentMapper {

    PaymentDTO toDTO(Payment payment);

}