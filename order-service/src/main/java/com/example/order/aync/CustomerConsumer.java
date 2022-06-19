package com.example.order.aync;


import com.example.common.domain.OrderDetail;
import com.example.common.domain.OrderState;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerConsumer {
    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "${customer.topic.name}", containerFactory = "customerKafkaListenerContainerFactory")
    public void customerOrderDetailListener(OrderDetail orderDetail) {
        try {
            // 주문 상태가 승인 상태로 왔다면 해당 주문 번호에 대해 승인 처리
            if(orderDetail.getState().equals(OrderState.APPROVED)) {
                orderService.approveOrder(orderDetail.getId());
            } else { // 그렇지 않다면 취소 처리
                orderService.rejectOrder(orderDetail.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
