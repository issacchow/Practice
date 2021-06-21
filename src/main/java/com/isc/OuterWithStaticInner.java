package com.isc;

/**
 * 带有静态内部类
 * 静态内部类可用于外部直接实例化
 * 所以，该内部类不能引用宿主类的方法或变量
 */
public class OuterWithStaticInner {

    private int outer_x = 0;

    public static class Inner{

        public void printOuterX(){

            // 这里编译错误，不能引用变量
            //outer_x++
        }

    }

}
