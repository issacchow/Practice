package com.isc.states.impl;

import com.isc.LogUtil;
import com.isc.OrderFSM;
import com.isc.OrderStateKey;
import com.isc.events.CreatePaymentOrderEvent;
import com.isc.states.OrderStateAdapter;

public class OrderCreatedState extends OrderStateAdapter {
    @Override
    public String getName() {
        return "订单已创建";
    }

    @Override
    public void process_CreatePaymentOrderEvent(CreatePaymentOrderEvent event, OrderFSM fsm) {

        LogUtil.log("已生成支付单: xxxx");

        fsm.changeState(OrderStateKey.READY_TO_PAY);

        // 这一代码由外部触发
        //fsm.triggerEvent(new PaymentCompleteEvent());
    }
}
