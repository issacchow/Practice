package com.isc.Sorter;

/**
 * 插入排序
 * 思路: 不断比较相邻的两个元素，找出较少的元素，并向左下沉(冒泡的相反方向),而较大的值进入下一次相邻比较循环。
 */
public class MyInsertSorter extends SorterSupport implements Sorter {

    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            pressMinValue(arr, i);
        }

    }

    /**
     * 将最小值下沉到最左边
     *
     * @param arr
     * @param start
     */
    private void pressMinValue(int arr[], int start) {
        pln("press min value at pos:" + start);
        int prevIndex, prevValue;
        for (int i = start; i > 0; i--) {
            // 如果下一个元素较少则替换
            prevIndex = i - 1;
            prevValue = arr[prevIndex];
            logln();
            logln("loop:" + i);
            logln("current: value - " + arr[i] + " pos - " + i);
            logln("prev: index - " + prevIndex + ", pos - " + prevValue);

            if (arr[i] < prevValue) {
                logln("result of min value: ===> " + arr[i]);
                swap(arr, i, prevIndex);
            } else {
                logln("result of min value:  not found.  break this loop");
                break;
            }
        }
    }


}
