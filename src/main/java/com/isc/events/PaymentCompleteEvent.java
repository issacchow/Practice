package com.isc.events;

import com.isc.OrderEvent;

public class PaymentCompleteEvent implements OrderEvent {
    @Override
    public String getName() {
        return "支付成功事件";
    }


    /**
     * 支付单
     */
    private String paymentOrderNo;

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }
}
