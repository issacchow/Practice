package com.isc;


/**
 * 不带static 的内部类
 * 该内部类可以看做成是Outer实例的一个部分
 * 因为Inner类需要先实例化Outer后才能使用
 * 并且Inner类的实例能直接引用Outer的私有方法或变量
 *
 */
public class Outer {

    private int x = 0;

    public void outerMethod(){
        System.out.println("outer method: x=" + this.x);
    }

    public Inner getInner(){
        return new Inner();
    }


   class Inner{

        public void test(){
            //直接访问 x
            x++;
            System.out.println(x);


        }

    }

}
