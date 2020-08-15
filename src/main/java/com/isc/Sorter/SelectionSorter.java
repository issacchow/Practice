package com.isc.Sorter;

/**
 * 选择排序
 * 思路: 与冒泡排序相似， 将数组看作成【栈】,与冒泡排序相反地，数组的左边为【栈底】，
 *       每次循环找出最小值，并推入栈底（即数组最左边依次为各个最小值)。
 *       有点打擂台那样，比拼赢了就继续站在台上
 * 步骤:
 * 1. 以第N个位置作为 皇冠 位置，并将该位置作为最小值
 * 2. 然后循环该位置以后的所有元素，找出【更小】的值
 * 3. 如果找到则将【更小】的值替替换成当前第N个位置，表示这是【最新】的最小值
 */
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
