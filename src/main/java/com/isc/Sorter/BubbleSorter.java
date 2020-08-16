package com.isc.Sorter;

/**
 * 冒泡排序
 * 思路:
 * 1. 将数组当作成【栈】，将数组尾部作为【栈底】。这个栈里的元素，其实就是已排序空间
 * 2. 将相邻的两个元素进行比较，找出较大的元素，并将较大的元素通过交换进行向右移动，直到推入栈底已存在元素之上
 * 注意: 相邻的两个元素相同时，不进行交换位置
 *
 *
 *
 * 步骤:
 * 1. 设数组最右边存放最大的元素
 * 2. 不断循环数组，每次循环时,找出最大值
 * 3. 将最大值迁移到数组右边的第N个最大的位置, 意思是数组从右到左的顺序，是第一最大、第二最大...
 * 4. 进入下一次循环时, 循环的下限边界为 数组右边的第N个最大值的位置【之前】
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
