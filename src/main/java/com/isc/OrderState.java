package com.isc;


import com.isc.events.CreatePaymentOrderEvent;
import com.isc.events.NewOrderEvent;
import com.isc.events.PaymentCompleteEvent;
import com.isc.events.UserCancelOrderEvent;

/**
 * 订单状态(状态模式)
 * 定义了每个订单状态下的不同行为(事件处理行为)
 */
public interface OrderState extends OrderEventProcessor {

    String getName();


    /**
     * 事件: 新订单
     * @param event
     * @param fsm
     */
    void process_NewOrderEvent(NewOrderEvent event, OrderFSM fsm);

    /**
     * 事件: 创建支付单
     * @param event
     * @param fsm
     */
    void process_CreatePaymentOrderEvent(CreatePaymentOrderEvent event, OrderFSM fsm);

    /**
     * 事件: 用户取消订单
     * @param event
     * @param fsm
     */
     void process_UserCancelOrderEvent(UserCancelOrderEvent event, OrderFSM fsm);

    /**
     * 事件: 支付完成
     * @param event
     * @param fsm
     */
    void process_PaymentCompleteEvent(PaymentCompleteEvent event, OrderFSM fsm);



}
