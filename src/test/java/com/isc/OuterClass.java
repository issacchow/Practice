package com.isc;

public class OuterClass {

    public void outerMethod(){
        System.out.println("outer method invoked!");
    }

    public class InnerClass{

        public void innerMethod(){
            System.out.println("inner method invoked!");
            outerMethod();
        }
    }


    public InnerClass buildInnerClass(){
        return new InnerClass();
    }



}
