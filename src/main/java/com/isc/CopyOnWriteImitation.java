package com.isc;

/**
 * CopyOnWriteArrayList 模仿器
 */
public class CopyOnWriteImitation {

    volatile private Integer[] arr = new Integer[10];

    public Integer[] getArray(){
        return arr;
    }

    public void setArray(Integer[] array){
        this.arr = array;
    }

    public void setValue(int index,Integer value){
        this.arr[index] = value;
    }

}
