package com.example.customer.aync;

import com.example.common.domain.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class CustomerProducer {
    @Autowired
    private KafkaTemplate<String, OrderDetail> customerKafkaTemplate;

    @Value(value = "${customer.topic.name}")
    private String customerTopicName;

    public void publishOrderDetail(OrderDetail orderDetail) {
        ListenableFuture<SendResult<String, OrderDetail>> future = customerKafkaTemplate.send(customerTopicName, orderDetail);

        future.addCallback(new ListenableFutureCallback<SendResult<String, OrderDetail>>() {
            private final Logger LOGGER = LoggerFactory.getLogger(CustomerProducer.class);

            @Override
            public void onSuccess(SendResult<String, OrderDetail> result) {
                OrderDetail g = result.getProducerRecord().value();
                LOGGER.info("Sent message=[" + g.getId() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable t) {
                // needed to do compensation transaction.
                LOGGER.error( "Unable to send message=[" + orderDetail.getId() + "] due to : " + t.getMessage(), t);
            }
        });
    }


}
