package com.isc;

/**
 * 订单事件处理器
 */
public interface OrderEventProcessor {
    /**
     * 处理事件,并切换到新状态
     * @param event 事件
     * @param fsm 当前状态机,可用于切换新的状态
     *
     */
    void processEvent(OrderEvent event,  OrderFSM fsm);
}
