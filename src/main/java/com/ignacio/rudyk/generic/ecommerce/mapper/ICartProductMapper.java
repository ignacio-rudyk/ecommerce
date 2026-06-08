package com.ignacio.rudyk.generic.ecommerce.mapper;

import com.ignacio.rudyk.generic.ecommerce.dto.CartProductDTO;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.CartProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartProductMapper {

    CartProductDTO toDTO(CartProduct cartProduct);

}