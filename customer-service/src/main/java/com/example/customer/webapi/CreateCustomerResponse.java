package com.example.customer.webapi;


import java.math.BigDecimal;

public class CreateCustomerResponse {
  private Long customerId;
  private BigDecimal amount;
  private String name;

  public BigDecimal getAmount() {
    return amount;
  }

  public String getName() {
    return name;
  }

  public CreateCustomerResponse(long customerId, String name, BigDecimal amount) {
    this.customerId = customerId;
    this.name = name;
    this.amount = amount;
  }

  public CreateCustomerResponse(Long customerId) {
    this.customerId = customerId;
  }


  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }
}
