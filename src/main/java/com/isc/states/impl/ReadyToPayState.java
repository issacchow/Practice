package com.isc.states.impl;

import com.isc.OrderFSM;
import com.isc.events.PaymentCompleteEvent;
import com.isc.states.OrderStateAdapter;

/**
 * 待支付
 */
public class ReadyToPayState extends OrderStateAdapter {

    @Override
    public String getName() {
        return "待支付";
    }

    /**
     * 支付完成后
     * 更新 支付单状态 为 已支付
     * 更新 订单状态 为 已支付
     * @param event
     * @param fsm
     */
    @Override
    public void process_PaymentCompleteEvent(PaymentCompleteEvent event, OrderFSM fsm) {


        event.getPaymentOrderNo();
    }
}
