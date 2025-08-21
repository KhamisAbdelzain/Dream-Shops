package com.khamis.dreamshops.service.order;

import com.khamis.dreamshops.dto.OrderDTO;
import com.khamis.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order orderPlace(Long userId);
    OrderDTO getOrder(Long orderId);

    List<OrderDTO> orders(Long userId);

    OrderDTO convertToDto(Order order);
}
