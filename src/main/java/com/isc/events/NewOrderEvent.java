package com.isc.events;

import com.isc.OrderEvent;

/**
 * 新订单事件
 */
public class NewOrderEvent implements OrderEvent {


    private String orderNo;
    private String phone;
    private String userName;

    @Override
    public String getName() {
        return "新订单事件";
    }

    public NewOrderEvent(String orderNo, String phone, String userName) {
        this.orderNo = orderNo;
        this.phone = phone;
        this.userName = userName;
    }
}
