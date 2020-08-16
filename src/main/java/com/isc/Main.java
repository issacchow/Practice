package com.isc;

import com.isc.Sorter.my.MyInsertionSorter;

import java.util.Random;

/**
 * 冒泡排序
 */
public class Main {

    public static void main(String[] args) {
//        int[] arr = randomArray();
        int[] arr = {1, 10, 3, 3, 8, 2, 3, 2, 2};

        System.out.println("array to sort:");
        dumpArr(arr);
        System.out.println();

        /**** 排序 ****/

        // 冒泡排序
//        new MyBubbleSorter().sort(arr);
//        new BubbleSorter().sort(arr);

        // 选择排序
//        new MySelectionSorter().sort(arr);

        // 插入排序
        new MyInsertionSorter().sort(arr);
//        new InsertionSort().sort(arr);

        System.out.println();
        System.out.println();
        System.out.println("result:");
        dumpArr(arr);
    }






    static void dumpArr(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static int[] randomArray() {
        Random random = new Random();
        int[] arr = new int[2000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

}
