package com.isc.Sorter;

import java.util.Random;

public class SorterSupport {

    protected void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    protected void dumpArr(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    protected void dumpArr(int[] arr,int start,int end) {

        for (int i = start; i <= end; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    protected void log(String format, Object... args) {
        System.out.printf(format, args);
    }

    protected void logln() {
        pln();
    }

    protected void logln(Object content) {
        pln(content);
    }

    protected void pln() {
        System.out.println();
    }

    protected void pln(Object content) {
        System.out.println(content);
    }

    protected void print(Object content) {
        pln(content);
    }

    int[] randomArray() {
        Random random = new Random();
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }


}
