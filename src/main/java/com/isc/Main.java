package com.isc;

import java.util.Random;

/**
 * XXX排序基础代码
 */
public class Main {

    public static void main(String[] args) {
//        int[] arr = randomArray();
        int[] arr = {1, 10, 3, 3, 8, 2, 3, 2, 2};

        System.out.println("array to sort:");
        dumpArr(arr);
        System.out.println();

        // 执行排序:

        System.out.println();
        System.out.println();
        System.out.println("result:");
        dumpArr(arr);
    }




    static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    static void dumpArr(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static int[] randomArray() {
        Random random = new Random();
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

}
