package com.example.customer.service;


import com.example.common.domain.Money;
import com.example.common.domain.OrderDetail;
import com.example.common.domain.OrderDetails;
import com.example.customer.domain.Customer;
import com.example.customer.domain.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;

@Transactional
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Value(value = "${customer.topic.name}")
  private String customerTopicName;


  /**
   * name과 creditLimit은 화면으로부터 넘어옴
   * @param name
   * @param creditLimit
   * @return
   */
  public Customer createCustomer(String name, Money creditLimit) {
    Customer customer = new Customer(name, creditLimit);

    customerRepository.save(customer);


    return customer;
  }

  /**
   * 포인트 차감
   * @param orderDetail
   */
  public void reserveCredit(OrderDetail orderDetail) {
    //Order 서비스에서 받은 OrderDetails 정보 가져오기
    OrderDetails orderDetails = orderDetail.getOrderDetails();
    // orderDetails에 기록된 customerId로 customer 객체를 가져온다.
    Customer customer = findCustomer(orderDetails.getCustomerId());
    System.out.println("orderDetails.getCustomerId(): " + orderDetails.getCustomerId()
            + "\tCustomer.name: " + customer.getName());

    // 보유한 금액 보다 주문한 금액이 크면 CustomerCreditLimitExceededException 발생
    // 주문 이력 정보 저장
    customer.reserveCredit(orderDetail.getId(), orderDetail.getOrderDetails().getOrderTotal());

    System.out.println("차감 후 현재 보유한 금액: " + customer.getCreditLimit().getAmount().toString());
    orderDetail.noteCreditReserved();
    // 주문상태 APPROVED로 업데이트
    orderDetail.noteCreditReserved();

  }


  public Customer findCustomer(long id) {
    return customerRepository.findById(id);
  }
}
