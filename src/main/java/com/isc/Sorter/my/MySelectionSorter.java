package com.isc.Sorter.my;

import com.isc.Sorter.Sorter;
import com.isc.Sorter.SorterSupport;

/**
 * 选择排序
 */
public class MySelectionSorter extends SorterSupport implements Sorter {

    @Override
    public void sort(int[] arr) {
        pln("array length:" + arr.length);
        int temp;
        for (int i = 0; i < arr.length; i++) {
            pln("for :" + i);
            int indexOfMin = indexOfMinValue(arr, i);
            if (indexOfMin > -1) {
                swap(arr, indexOfMin, i);
            }
        }


    }


    /**
     * 返回最小值的下标
     *
     * @param arr
     * @return
     */
    public int indexOfMinValue(int[] arr, int start) {

        pln("find min at position:" + start);

        if (start + 1 == arr.length)
            return start;

        int min = arr[start];
        int index = -1;
        for (int i = start + 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
                index = i;
            }
        }

        pln("min value:" + min);
        pln("min index:" + index);
        return index;
    }
}
