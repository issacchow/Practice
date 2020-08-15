package com.isc;

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

//        myBubbleSort(arr);
        bubbleSort(arr);
        System.out.println();
        System.out.println();
        System.out.println("result:");
        dumpArr(arr);
    }



    static void myBubbleSort(int[] arr) {

        int curValue, nextValue, curPos, nextPos;
        // 判断每个数是否有冒泡
        boolean hasBubble = false;
        for (int i = 0; i < arr.length - 1; ) {

            System.out.println("\nloop :" + i);
            // 当前要冒泡的值
            curValue = arr[i];
            curPos = i;
            System.out.println("cur value:" + curValue);
            System.out.println("before sort:");
            dumpArr(arr);

            hasBubble = false;
            for (nextPos = i + 1; nextPos < arr.length; nextPos++) {
                // 下一个值
                nextValue = arr[nextPos];

                if (curValue < nextValue) {
                    // 不能再冒泡则表示已经找到对应位置
                    break;
                }

                if (curValue == nextValue) {
                    // 值相同时，不交换，也不中断(只有少于
                    continue;
                }

                if (curValue > nextValue) {
                    swap(arr, curPos, nextPos);
                    curPos++;
                    hasBubble = true;
                }
            }

            // 如果没有冒泡,则从当前位置继续冒泡，即同一个位置进行至少两次冒泡过程
            if (hasBubble == false) {
                i++;
            }

            System.out.println("after sort:");
            dumpArr(arr);
        }

    }

    /**
     * 标准方法
     *
     * @param arr
     */
    static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) swap(arr, j, j + 1);
            }
        }
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
