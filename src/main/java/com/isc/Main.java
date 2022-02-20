package com.isc;

public class Main {

    public static void main( String[] args )
    {
        // 这里断点到classloader的 loadClass(String name, boolean resolve) 方法看看加载过程
        // 断点时加上断点条件为:  name.equal("com.isc.SomeClz")
        SomeClz clz = new SomeClz();
    }




}
