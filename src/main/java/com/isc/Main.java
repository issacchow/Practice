package com.isc;

import com.isc.Sorter.my.MyMergeSort;

import java.util.Random;

/**
 * 冒泡排序
 */
public class Main {

    public static void main(String[] args) {
//        int[] arr = randomArray();
//        int[] arr = {1, 10, 3, 3, 8, 2, 3, 2, 2};

//        int[] arr = {3, 8, 10, 3};
        int[] arr = {8 ,10 ,3,3 };


        int[] copyArr = new int[arr.length];
//        System.arraycopy(arr, 0, copyArr, 0, arr.length);

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
//        new MyInsertionSorter().sort(arr);
//        new InsertionSort().sort(arr);


        // 归并排序
        new MyMergeSort().sort(arr);

        System.out.println();
        System.out.println();
        System.out.println("result:");
        dumpArr(arr);

        checkOrder(arr);
    }


    static void dumpArr(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static int[] randomArray() {
        Random random = new Random();
        int[] arr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

    /**
     * 检查顺序是否有错误
     *
     * @param arr
     */
    static void checkOrder(int[] arr) throws RuntimeException {

        System.out.println();
        System.out.println("check order");


        for (int i = 0; i < arr.length-1; i++) {
            if (arr[i] > arr[i + 1]) {

                System.out.println("this is disorder");
                System.out.println("current value:" + arr[i] + ", next value:" + arr[i + 1]);
                return;
            }
        }

        System.out.println("success");
    }

}
