package com.isc;

import com.isc.events.NewOrderEvent;
import com.isc.events.PaymentCompleteEvent;
import com.isc.events.UserCancelOrderEvent;

/**
 * 状态机示例
 * FSM: Finite State Machine
 * 使用订单状态流转场景作为应用实例
 */
public class Main {

    public static void main( String[] args )
    {

        LogUtil.log("******** 创建新订单 ********");
        createOrder();

        LogUtil.log("\n\n");

        LogUtil.log("******** 取消订单 ********");
        cancelOrder();
    }


    static void createOrder(){

        OrderFSM fsm = new OrderFSM();

        // 默认状态
        // fsm.changeState(OrderStateKey.READY_TO_CREATE);

        // 触发事件: 新订单
        fsm.triggerEvent(new NewOrderEvent("orderNo-00001","13511112222","issac chow"));

        // 触发事件: 支付成功
        fsm.triggerEvent(new PaymentCompleteEvent());
    }


    static void cancelOrder(){

        OrderFSM fsm = new OrderFSM();

        // 默认状态
        fsm.changeState(OrderStateKey.READY_TO_PAY);
        fsm.triggerEvent(new UserCancelOrderEvent("orderNo-00001"));
    }



}
