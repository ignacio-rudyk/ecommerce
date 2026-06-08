package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.CartProductDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.OrderItemDTO;
import com.ignacio.rudyk.generic.ecommerce.exception.BadRequestException;
import com.ignacio.rudyk.generic.ecommerce.exception.DataNotFoundException;
import com.ignacio.rudyk.generic.ecommerce.mapper.IOrderItemMapper;
import com.ignacio.rudyk.generic.ecommerce.repository.IOrderItemRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.OrderItem;
import com.ignacio.rudyk.generic.ecommerce.service.IOrderItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    private IOrderItemRepository orderItemRepository;

    private IOrderItemMapper orderItemMapper;

    public OrderItemService(IOrderItemRepository orderItemRepository,
                            IOrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public List<OrderItem> saveItems(Long orderId, List<CartProductDTO> products) {
        List<OrderItem> items = new ArrayList<>();
        if(products.isEmpty())
            throw new BadRequestException("La lista de productos esta vacia");
        for(CartProductDTO cartProduct : products) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartProduct.product().id());
            orderItem.setOrderId(orderId);
            orderItem.setProductTitle(cartProduct.product().title());
            orderItem.setUnitPrice(cartProduct.product().price().price());
            orderItem.setSubTotal(calculateSubtotalItem(cartProduct.product().price().price(), cartProduct.quantity()));
            orderItem.setQuantity(cartProduct.quantity());
            items.add(orderItem);
        }
        orderItemRepository.saveAll(items);
        return items;
    }

    @Override
    public List<OrderItemDTO> getItems(Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        if(items.isEmpty())
            throw new DataNotFoundException("Los items de la orden no se encuntran");
        return items.stream().map(e->orderItemMapper.toDTO(e)).toList();
    }

    private BigDecimal calculateSubtotalItem(BigDecimal price, Long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
