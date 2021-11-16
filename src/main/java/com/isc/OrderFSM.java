package com.isc;

/**
 * 订单有限状态机
 * 每条订单记录 都应该分配对应一个实例
 * 内部状态的切换大部分是自动的,只有首次状态是手动设定的
 *
 * ## 自动切换状态 = 自动走流程
 * 自动切换状态是由每个状态完成切换后，自动根据业务逻辑切换到下一个状态
 */
public class OrderFSM {

    private OrderState state;
    private OrderStateProvider provider;


    public OrderFSM() {

        provider = new OrderStateProvider();
        this.state = provider.getState(OrderStateKey.READY_TO_CREATE);
    }


    public void triggerEvent(OrderEvent event) {
        LogUtil.log("--- 正在触发新事件:【%s】", event.getName());
        this.state.processEvent(event, this);
    }

    public void changeState(OrderStateKey key) {
        OrderState newState = provider.getState(key);
        if(newState==null){
            LogUtil.log("没有找到 [%s] 对应的状态实例", key.name());
        }

        LogUtil.log("--- 当前状态:【%s】， 切换到新状态:【%s】", getCurrentState().getName(), newState.getName());



        this.state = newState;
    }

    public OrderState getCurrentState() {
        return state;
    }
}
