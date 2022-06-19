package com.example.customer.aync;

import com.example.common.domain.OrderDetail;
import com.example.customer.domain.CustomerCreditLimitExceededException;
import com.example.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerProducer customerProducer;

    @KafkaListener(topics = "${order.topic.name}", containerFactory = "orderKafkaListenerContainerFactory")
    public void orderListener(OrderDetail orderDetail) {
        Long orderId=null, customerId=null, orderAmount=null;
        try {
            orderId = orderDetail.getId();
            customerId = orderDetail.getOrderDetails().getCustomerId();
            orderAmount = orderDetail.getOrderDetails().getOrderTotal().getAmount().longValue();

            System.out.println("============> Recieved orderId: " + orderId + "\t"
                    + "customerId:" + customerId +"\t orderAmount: " + orderAmount);

            // 보유 크래딧 보다 주문 금액이 많으면 CustomerCreditLimitExceededException 발생
            customerService.reserveCredit(orderDetail);
            // 주문상태 APPROVED로 업데이트
            orderDetail.noteCreditReserved();
            customerProducer.publishOrderDetail(orderDetail);
        } catch (CustomerCreditLimitExceededException e) {
            // 주문상태 CANCEL로 업데이트
            System.out.println("다음의 주문이 보유 크래딧을 초과해 주문 실패 했으요... orderId: " + orderId + "\t"
                    + "customerId:" + customerId +"\t orderAmount: " + orderAmount);
            orderDetail.noteCreditReservationFailed();
            customerProducer.publishOrderDetail(orderDetail);
        } catch (Exception e) {
            // 주문상태 CANCEL로 업데이트
            orderDetail.noteCreditReservationFailed();
            customerProducer.publishOrderDetail(orderDetail);
        }
    }
}
