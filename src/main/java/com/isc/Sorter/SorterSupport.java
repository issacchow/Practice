package com.isc.Sorter;

import java.util.Random;

public class SorterSupport {

    void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    void dumpArr(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    void logln(){
        pln();
    }

    void logln(String content){
        pln(content);
    }

    void pln(){
//        System.out.println();
    }
    void pln(String content){
//        System.out.println(content);
    }

    int[] randomArray() {
        Random random = new Random();
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

    /**
     * 检查顺序是否有错误
     * @param arr
     */
    void checkOrder(int[] arr) throws RuntimeException {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]>arr[i+1]){
                throw new RuntimeException("this is disorder");
            }
        }
    }
}
