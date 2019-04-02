package com.isc;

import org.junit.Assert;
import org.junit.Test;

/**
 * 演示静态属性何时被初始化
 */
public class TheInitiationInStaticClass
{

    /**
     * 通过引用静态变量触发初始化静态变量
     */
    @Test
    public void initByUsingStaticField(){
        System.out.println("initByUsingStaticField start");
        System.out.println(SomeClass.value);
        System.out.println("initByUsingStaticField end");

        Assert.assertTrue(SomeClass.value==10);
    }

    /**
     * 通过实例化触发初始化静态变量
     */
    @Test
    public void initByInstating(){
        System.out.println("initByInstating start");
        new SomeClass();
        System.out.println("initByInstating end");

        Assert.assertTrue(SomeClass.value==10);
    }




    public static class SomeClass{

        private static Integer value;
        static {
            value = 10;
            System.out.println("init");
        }

    }

}
