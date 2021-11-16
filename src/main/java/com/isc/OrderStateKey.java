package com.isc;

/**
 * 订单状态的key
 * 通过key 来引用全局唯一的{@link OrderState }实例,
 * 代替直接使用 {@link OrderState}作为参数
 */
public enum OrderStateKey {

    READY_TO_CREATE(0),
    /**
     * 订单已创建
     */
    CREATED(1),
    /**
     * 订单已取消
     */
    CANCEL(2),

    READY_TO_PAY(3),
    PAYMENT_COMPLETE(4),
    PAYMENT_TIMEOUT(5);

    private int value;
    OrderStateKey(int value){
        this.value = value;
    }

}
