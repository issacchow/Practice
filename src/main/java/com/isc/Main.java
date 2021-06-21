package com.isc;

/**
 * 主要测试一下内部类，不通过super关键字直接引用"父"类(寄主类)的私有方法和变量
 *
 * 疑问: 为什么Inner在Outer没有实例化的时候，能引用Outer的方法??
 * 答案: 其实因为内部类实例化操作的约束，必须Outer实例化后才能实例化Inner
 *
 * 内部类直接引用宿主类方法或变量的前提条件:
 *    内部类不能使用static, 这样意味着内部类(Outer.Inner)不能在外部实例化
 *    而只能通过宿主类(Outer)实例化，所以需要先实例化Outer ，后再实例化Inner,
 *    这个时候，疑问的答案就出来了 -- Inner所引用的Outer其实已经实例化了
 *
 * 附加分析: 内部类，可以作为宿主类身体的一部分， 类似C# 里的 partial class可以将一个类的定义分散到不同文件中
 *
 */
public class Main {

    public static void main( String[] args )
    {

        // 不能直接new一个Inner实例，如果要new 一定要加static关键字
        // 但加了关键字以后，则Inner类，不能直接引用父类的 【私有】属性 和方法。
        // new Outer.Inner()


        Outer outer = new Outer();

        Outer.Inner inner = outer.getInner();
        inner.test();
        outer.outerMethod();


        Outer outer2 = new Outer();
        Outer.Inner inner2 = outer2.getInner();
        inner2.test();
        outer2.outerMethod();



    }




}
