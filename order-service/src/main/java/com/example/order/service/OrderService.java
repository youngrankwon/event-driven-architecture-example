package com.example.order.service;

import com.example.common.domain.OrderDetail;
import com.example.common.domain.OrderDetails;
import com.example.order.aync.OrderProducer;
import com.example.order.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrderService {

  @Autowired
  private OrderProducer orderProducer;

  @Autowired
  private OrderRepository orderRepository;

  /**
   * 주문 생성
   * @param orderDetails
   * @return
   */
  public OrderDetail createOrder(OrderDetails orderDetails) {
    OrderDetail order =  OrderDetail.createOrder(orderDetails);
    orderRepository.save(order);
    System.out.println("[DEBUG-OrderDetail] " + order.toString()  );
    orderProducer.publishOrder(order);
    return order;
  }

  public void approveOrder(Long orderId) {
    OrderDetail order = orderRepository
            .findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("order with id %s not found", orderId)));
    order.noteCreditReserved();

  }

  public void rejectOrder(Long orderId) {
    OrderDetail order = orderRepository
            .findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("order with id %s not found", orderId)));
    order.noteCreditReservationFailed();

  }
}
