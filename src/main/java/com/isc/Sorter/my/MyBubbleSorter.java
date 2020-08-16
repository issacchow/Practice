package com.isc.Sorter.my;

import com.isc.Sorter.Sorter;
import com.isc.Sorter.SorterSupport;

/**
 * 冒泡排序(有问题版本)
 */
public class MyBubbleSorter extends SorterSupport implements Sorter {

    @Override
    public void sort(int[] arr) {
        int curValue, nextValue, curPos, nextPos;
        // 判断每个数是否有冒泡
        boolean hasBubble = false;
        for (int i = 0; i < arr.length - 1; ) {

            System.out.println("\nloop :" + i);
            // 当前要冒泡的值
            curValue = arr[i];
            curPos = i;
            pln("cur value:" + curValue);
            pln("before sort:");
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

            pln("after sort:");
            dumpArr(arr);
        }
    }
}
