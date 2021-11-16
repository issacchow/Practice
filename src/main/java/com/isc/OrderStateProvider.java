package com.isc;

import com.isc.states.impl.CancelState;
import com.isc.states.impl.OrderCreatedState;
import com.isc.states.impl.ReadyToCreateState;
import com.isc.states.impl.ReadyToPayState;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态单例提供器
 * 使用key 代替 实例引用
 */
public class OrderStateProvider {


    private Map<OrderStateKey, OrderState> instances;

    public OrderStateProvider() {
        registerAll();

    }

    /**
     * 注册所有状态
     */
    private void registerAll() {

        instances = new HashMap<>(10);

        instances.put(OrderStateKey.READY_TO_CREATE, new ReadyToCreateState());
        instances.put(OrderStateKey.CREATED, new OrderCreatedState());
        instances.put(OrderStateKey.READY_TO_PAY, new ReadyToPayState());
        instances.put(OrderStateKey.CANCEL, new CancelState());

    }


    public OrderState getState(OrderStateKey key) {
        return instances.get(key);
    }


}
