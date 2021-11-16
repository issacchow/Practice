package com.isc.states;

import com.isc.LogUtil;
import com.isc.OrderFSM;
import com.isc.OrderStateKey;
import com.isc.events.CreatePaymentOrderEvent;
import com.isc.events.NewOrderEvent;
import com.isc.events.PaymentCompleteEvent;
import com.isc.events.UserCancelOrderEvent;

/**
 * 订单状态适配器
 * 提供默认的行为
 * 子类无需每个方法都实现
 * 只需要实现自己关注的方法即可
 */
public abstract class OrderStateAdapter extends AbstractOrderState {


    @Override
    public void process_NewOrderEvent(NewOrderEvent event, OrderFSM fsm) {

    }

    @Override
    public void process_CreatePaymentOrderEvent(CreatePaymentOrderEvent event, OrderFSM fsm) {

    }

    @Override
    public void process_UserCancelOrderEvent(UserCancelOrderEvent event, OrderFSM fsm) {
        /** 切换到下一个状态，并触发【用户取消处理】事件 **/

        // 记录用户取消原因
        LogUtil.log("用户申请取消订单，订单号【%s】, 原因:%s",event.getOrderNo(), event.getReason());
        LogUtil.log("更新数据库: 订单状态为已取消");


        // 恢复库存
        LogUtil.log("更新数据库: 恢复库存...");

        // 切换【正在取消】状态
        fsm.changeState(OrderStateKey.CANCEL);
    }

    @Override
    public void process_PaymentCompleteEvent(PaymentCompleteEvent event, OrderFSM fsm) {

    }
}
