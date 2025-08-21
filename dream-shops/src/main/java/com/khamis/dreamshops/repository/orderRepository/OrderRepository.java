package com.khamis.dreamshops.repository.orderRepository;

import com.khamis.dreamshops.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order getOrderByUserId(Long userId);

    List<Order> findByUserId(Long userId);
}
