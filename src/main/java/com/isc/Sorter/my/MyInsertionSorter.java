package com.isc.Sorter.my;

import com.isc.Sorter.Sorter;
import com.isc.Sorter.SorterSupport;

/**
 * 插入排序(扑克抓牌时排序,重新插入排序)
 * 思路: 不断比较相邻的两个元素，找出较少的元素，并向左下沉(冒泡的相反方向),而较大的值进入下一次相邻比较循环。
 */
public class MyInsertionSorter extends SorterSupport implements Sorter {

    @Override
    public void sort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            insertValue(arr, i);
        }

    }

    /**
     * 将最小值下沉到最左边
     *
     * @param arr 数组
     * @param indexOfInsertValue 插入值的索引位置
     */
    private void insertValue(int arr[], int indexOfInsertValue) {
        pln("insert value at pos:" + indexOfInsertValue);

        int insertValue = arr[indexOfInsertValue];
        int prevIndex, prevValue;
        for (int i = indexOfInsertValue; i > 0; i--) {
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
                insertValue = arr[prevIndex];
            } else {
                logln("result of min value:  not found.  break this loop");
                break;
            }
        }
    }


}
