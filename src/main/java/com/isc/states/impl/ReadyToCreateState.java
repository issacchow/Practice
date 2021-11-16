package com.isc.states.impl;

import com.isc.LogUtil;
import com.isc.OrderFSM;
import com.isc.OrderStateKey;
import com.isc.entity.OrderEntity;
import com.isc.events.CreatePaymentOrderEvent;
import com.isc.events.NewOrderEvent;
import com.isc.states.OrderStateAdapter;

/**
 * 订单待创建状态
 */
public class ReadyToCreateState extends OrderStateAdapter {

    @Override
    public String getName() {
        return "订单待创建";
    }


    @Override
    public void process_NewOrderEvent(NewOrderEvent event, OrderFSM fsm) {
        // 插入数据库
        LogUtil.log("创建订单，执行SQL: Insert Order....");


        // 切换下一个状态，并触发事件
        fsm.changeState(OrderStateKey.CREATED);
        fsm.triggerEvent(new CreatePaymentOrderEvent(new OrderEntity()));
    }


}