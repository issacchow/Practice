package com.isc.states;

import com.isc.OrderEvent;
import com.isc.OrderFSM;
import com.isc.OrderState;
import com.isc.events.CreatePaymentOrderEvent;
import com.isc.events.NewOrderEvent;
import com.isc.events.PaymentCompleteEvent;
import com.isc.events.UserCancelOrderEvent;

/**
 * 抽象订单状态(事件分发)
 * 实现了事件分发(即触发某一状态下的某一个动作)
 */
public abstract class AbstractOrderState implements OrderState {

    @Override
    public void processEvent(OrderEvent event, OrderFSM fsm) {


        if(event instanceof CreatePaymentOrderEvent){
            process_CreatePaymentOrderEvent((CreatePaymentOrderEvent) event,fsm);
            return;
        }


        if(event instanceof NewOrderEvent){
            process_NewOrderEvent((NewOrderEvent) event,fsm);
            return;
        }

        if(event instanceof PaymentCompleteEvent){
            process_PaymentCompleteEvent((PaymentCompleteEvent) event,fsm);
            return;
        }

        if(event instanceof UserCancelOrderEvent){
            process_UserCancelOrderEvent((UserCancelOrderEvent) event,fsm);
            return;
        }

    }





}
