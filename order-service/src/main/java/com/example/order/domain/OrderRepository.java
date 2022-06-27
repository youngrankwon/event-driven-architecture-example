package com.example.order.domain;

import com.example.common.domain.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderDetail, Long> {
}
