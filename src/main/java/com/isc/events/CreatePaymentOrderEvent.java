package com.isc.events;

import com.isc.OrderEvent;
import com.isc.entity.OrderEntity;

/**
 * 生成支付单事件
 */
public class CreatePaymentOrderEvent implements OrderEvent {

    private OrderEntity order;

    @Override
    public String getName() {
        return "生成支付单";
    }


    public CreatePaymentOrderEvent(OrderEntity order) {
        this.order = order;
    }

    public OrderEntity getOrder() {
        return order;
    }
}
