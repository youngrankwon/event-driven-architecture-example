package com.example.order.webapi;


import java.math.BigDecimal;

public class CreateOrderResponse {
  private Long orderId;
  private int orderState;
  private Long customerId;
  private BigDecimal orderTotal;


  public CreateOrderResponse(Long orderId, Long customerId, int orderState, BigDecimal orderTotal) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.orderState = orderState;
    this.orderTotal = orderTotal;
  }

  public CreateOrderResponse(Long orderId) {
    this.orderId = orderId;
  }


  public Long getOrderId() {
    return orderId;
  }

  public String getOrderState() {
    String state = "";
    if(orderState == 2) {
      state = "REJECTED";
    } else if (orderState == 1) {
      state = "APPROVED";
    } else {
      state = "PENDING";
    }
    return state;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public BigDecimal getOrderTotal() {
    return orderTotal;
  }

}
