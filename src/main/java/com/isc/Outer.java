package com.isc;

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
