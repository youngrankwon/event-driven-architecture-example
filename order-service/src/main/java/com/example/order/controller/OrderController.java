package com.example.order.controller;

import com.example.common.domain.OrderDetail;
import com.example.common.domain.OrderDetails;
import com.example.order.aync.CustomerConsumer;
import com.example.order.domain.OrderRepository;
import com.example.order.service.OrderService;
import com.example.order.webapi.CreateOrderRequest;
import com.example.order.webapi.CreateOrderResponse;
import com.example.order.webapi.GetOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

  private OrderService orderService;

  private OrderRepository orderRepository;

  private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  public OrderController(OrderService orderService, OrderRepository orderRepository) {
    this.orderService = orderService;
    this.orderRepository = orderRepository;
  }

  @RequestMapping(value = "/orders", method = RequestMethod.POST)
  public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
    OrderDetail order = orderService.createOrder(new OrderDetails(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal()));
    LOGGER.info("Return OrderController");
    return new CreateOrderResponse(order.getId());
  }

  @RequestMapping(value="/orders/{orderId}", method= RequestMethod.GET)
  public ResponseEntity<GetOrderResponse> getOrder(@PathVariable Long orderId) {
     return orderRepository
            .findById(orderId)
            .map(order -> new ResponseEntity<>(new GetOrderResponse(order.getId(), order.getState()), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
