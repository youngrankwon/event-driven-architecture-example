package com.example.order.domain;


import com.example.common.domain.Money;

import javax.persistence.*;
import java.util.Collections;
import java.util.Map;


@Entity
@Table(name="Customer")
@Access(AccessType.FIELD)
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Embedded
  private Money creditLimit;

  @ElementCollection
  private Map<Long, Money> creditReservations;

  public Customer() {
  }

  public Customer(String name, Money creditLimit) {
    this.name = name;
    this.creditLimit = creditLimit;
    this.creditReservations = Collections.emptyMap();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Money getCreditLimit() {
    return creditLimit;
  }

  public void reserveCredit(Long orderId, Money orderTotal) {
	  // 사용 크래딧과 보유 크래딧 비교
    if (availableCredit().isGreaterThanOrEqual(orderTotal)) {
    	// 차감
      creditReservations.put(orderId, orderTotal);
    } else {
     // 만약 차감하려는 크래딧이 보유크래딧 보다 크다면 에러
    	throw new CustomerCreditLimitExceededException();
    }
  }

  Money availableCredit() {
    return creditLimit.subtract(creditReservations.values().stream().reduce(Money.ZERO, Money::add));
  }

}
