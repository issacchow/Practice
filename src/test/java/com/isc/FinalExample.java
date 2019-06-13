package com.isc;

public class FinalExample {

    int i;
    final int j;
    static FinalExample obj;


    public FinalExample(){
        i = 1;
        j = 2;
    }

    static public void write(){
        obj = new FinalExample();
    }

    static public void read()  {
        int a = obj.i;
        int b = obj.j;
    }



    public void test(){

    }



    public class ConditionObject {

        public void a(){
            test();
        }

    }


}
