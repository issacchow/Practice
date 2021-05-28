package com.isc.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class LadyProxyFactory
{

    private Lady lady;
    public LadyProxyFactory(Lady lady) {
        this.lady = lady;
    }

    public Lady create(){
        Enhancer enhancer = new Enhancer();
        // 设置被代理的类
        enhancer.setSuperclass(lady.getClass());
        // 设置代理的类
        enhancer.setCallback(new Interceptor(lady));
        return (Lady) enhancer.create();
    }

    public static class Interceptor implements MethodInterceptor{

        private Lady lady;
        public Interceptor(Lady lady) {
            this.lady = lady;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object result = null;
            try{
                System.out.println("before lady saying");
                result = proxy.invoke(this.lady,args);
                System.out.println("after lady said");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
}
