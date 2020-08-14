package com.isc;

/**
 * 选择排序
 * 思路:不断循环数组，每次循环找出最小值或最大值，然后排在前面
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = {3,6,7,9,1,0,5,3,4};

        MySelectionSort sort = new MySelectionSort();
//        arr = sort.selectionSort(arr);
        arr = sort.selectionSort2(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(i + " ");
        }
    }




}
