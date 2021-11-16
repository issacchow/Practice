package com.isc.states.impl;

import com.isc.OrderFSM;
import com.isc.events.UserCancelOrderEvent;
import com.isc.states.OrderStateAdapter;

/**
 * 订单已取消
 * 订单结束,不做任何操作
 */
public class CancelState extends OrderStateAdapter {

    @Override
    public String getName() {
        return "已取消";
    }

    @Override
    public void process_UserCancelOrderEvent(UserCancelOrderEvent event, OrderFSM fsm) {
        // 取消父类的方法，避免重复订单
    }
}
