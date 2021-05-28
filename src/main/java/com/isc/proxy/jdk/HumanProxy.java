package com.isc.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HumanProxy implements InvocationHandler {

    private Human delegate;

    public Human bind(Human delegate) {
        this.delegate = delegate;
        return (Human) Proxy.newProxyInstance(delegate.getClass().getClassLoader(), this.delegate.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("before say hello");
        Object result = method.invoke(this.delegate, args);
        System.out.println("after say hello");
        return result;
    }
}
