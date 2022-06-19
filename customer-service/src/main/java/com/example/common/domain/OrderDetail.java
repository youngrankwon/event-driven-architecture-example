package com.example.common.domain;


import javax.persistence.*;


@Entity(name="com.example.common.domain.OrderDetail")
@Table(name="orders")
@Access(AccessType.FIELD)
public class OrderDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private OrderState state;

  @Embedded
  private OrderDetails orderDetails;

  public OrderDetail() {
  }

  public OrderDetail(OrderDetails orderDetails) {
    this.orderDetails = orderDetails;
    this.state = OrderState.PENDING;
  }

  public static OrderDetail createOrder(OrderDetails orderDetails) {
    OrderDetail order = new OrderDetail(orderDetails);
    return order;
  }

  public Long getId() {
    return id;
  }

  public void noteCreditReserved() {
    this.state = OrderState.APPROVED;
  }

  public void noteCreditReservationFailed() {
    this.state = OrderState.REJECTED;
  }

  public OrderState getState() {
    return state;
  }

  public OrderDetails getOrderDetails() {
    return orderDetails;
  }

  @Override
  public String toString() {
    return "OrderDetail{" +
            "id=" + id +
            ", state=" + state +
            ", orderDetails=" + orderDetails +
            '}';
  }
}
