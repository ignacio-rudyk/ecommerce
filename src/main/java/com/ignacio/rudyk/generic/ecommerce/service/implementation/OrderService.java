package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.*;
import com.ignacio.rudyk.generic.ecommerce.enumerate.OrderStateEnum;
import com.ignacio.rudyk.generic.ecommerce.exception.DataNotFoundException;
import com.ignacio.rudyk.generic.ecommerce.mapper.IOrderMapper;
import com.ignacio.rudyk.generic.ecommerce.repository.IOrderRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Order;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.OrderItem;
import com.ignacio.rudyk.generic.ecommerce.service.ICartService;
import com.ignacio.rudyk.generic.ecommerce.service.IOrderItemService;
import com.ignacio.rudyk.generic.ecommerce.service.IOrderService;
import com.ignacio.rudyk.generic.ecommerce.service.IOrderStateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private IOrderRepository orderRepository;

    private ICartService cartService;

    private IOrderItemService orderItemService;

    private IOrderStateService orderStateService;

    private IOrderMapper orderMapper;

    public OrderService(IOrderRepository orderRepository,
                        ICartService cartService,
                        IOrderItemService orderItemService,
                        IOrderStateService orderStateService,
                        IOrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.orderStateService = orderStateService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public void createOrder(Long cartId) {
        CartDTO cart = cartService.getCart(cartId);
        Order order = new Order();
        order.setCreatedAt(new Date());
        order.setLastModification(new Date());
        order.setUserId(cart.userId());
        order.setOrderState(orderStateService.findByCode(OrderStateEnum.EN_PROCESO.getCode()));
        order = orderRepository.save(order);
        List<OrderItem> items = orderItemService.saveItems(order.getId(), cart.products());
        order.setTotalAmount(calculateTotalAmount(items));
        orderRepository.save(order);
        cartService.emptyCart(cartId);
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        OrderDTO order = orderMapper.toDTO(findById(orderId));
        order = order.withItems(orderItemService.getItems(orderId));
        return order;
    }

    @Override
    public void updateOrderState(Long orderId, String orderStateCode) {
        Order order = findById(orderId);
        order.setOrderState(orderStateService.findByCode(orderStateCode));
        orderRepository.save(order);
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(OrderItem orderItem : items) {
            totalAmount = orderItem.getSubTotal().add(totalAmount);
        }
        return totalAmount;
    }

    private Order findById(Long orderId) {
        Optional<Order> opOrder = orderRepository.findById(orderId);
        if(opOrder.isEmpty())
            throw new DataNotFoundException("Orden no encontrada");
        return opOrder.get();
    }

}
