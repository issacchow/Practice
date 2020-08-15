package com.isc.Sorter;

/**
 * 冒泡排序
 * 思路: 每次循环数组，找出最大的值，然后将数组当作成【栈】，将数组尾部作为【栈底】，不断将最大值入栈到数组尾部
 *
 * 步骤:
 * 1. 设数组最右边存放最大的元素
 * 2. 不断循环数组，每次循环时,找出最大值
 * 3. 将最大值迁移到数组右边的第N个最大的位置, 意思是数组从右到左的顺序，是第一最大、第二最大...
 * 3. 进入下一次循环时, 循环的下限边界为 数组右边的第N个最大值的位置【之前】
 */
public class BubbleSorter extends SorterSupport implements Sorter{
    @Override
    public void sort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) swap(arr, j, j + 1);
            }
        }
    }
}
