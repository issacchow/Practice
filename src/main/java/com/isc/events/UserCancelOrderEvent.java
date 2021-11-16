package com.isc.events;

import com.isc.OrderEvent;

public class UserCancelOrderEvent implements OrderEvent {

    private String orderNo;
    /**
     * 取消原因
     */
    private String reason;

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public UserCancelOrderEvent(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    @Override
    public String getName() {
        return "用户取消订单";
    }
}

