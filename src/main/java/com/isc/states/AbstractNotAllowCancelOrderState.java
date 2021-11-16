package com.isc.states;

import com.isc.LogUtil;
import com.isc.OrderFSM;
import com.isc.events.UserCancelOrderEvent;

/**
 * 不允许取消订单状态抽象类
 * 派生的子类都不能进行取消订单操作
 * (一旦进入某些流程，就不可能取消)
 *
 */
public abstract class AbstractNotAllowCancelOrderState extends OrderStateAdapter{

    @Override
    public void process_UserCancelOrderEvent(UserCancelOrderEvent event, OrderFSM fsm) {
        LogUtil.log("不可取消订单");
    }
}
