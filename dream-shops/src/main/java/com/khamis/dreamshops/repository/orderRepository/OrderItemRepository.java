package com.khamis.dreamshops.repository.orderRepository;

import com.khamis.dreamshops.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
