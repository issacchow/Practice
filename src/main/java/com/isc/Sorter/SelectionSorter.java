package com.isc.Sorter;

public class SelectionSorter extends SorterSupport implements Sorter {

    @Override
    public void sort(int[] arr) {
        pln("array length:" + arr.length);
        int temp;
        for (int i = 0; i < arr.length; i++) {
            pln("for :" + i);
            findMinValue(arr, i);
        }


    }


    /**
     * 查找最小的值，并将最小的值替换到指定的位置
     *
     * @param arr
     * @return
     */
    public void findMinValue(int[] arr, int minValueIndex) {

        pln("find min value");

        if (minValueIndex == arr.length - 1)
            return;

        int minValue = arr[minValueIndex];
        int index = -1;
        for (int i = minValueIndex + 1; i < arr.length; i++) {
            if (arr[i] < minValue) {
                // 找到更小的值,则交换位置
                swap(arr, minValueIndex, i);
                // 并更新最新的最小值
                minValue = arr[minValueIndex];
            }
        }

        pln("min value:" + minValue);
        pln("min index:" + index);
    }
}
