package com.isc.Sorter;

/**
 * 插入排序
 * 思路: 等同 打扑克抓牌时，每抓一张就牌，就从手里已抓到的牌里进行有序插入。
 * 分成两个区域，已排序区域 和 未排序区域, 手里已经抓的排为【已排序区域】，待抓的排为【未排区域】。
 * 对于数组来说，第一个元素则为初始化的 【已排序区域】，然后将其他当作【未排序区域】的牌，进行重新插入。
 *
 */
public class InsertionSort extends SorterSupport implements Sorter {
    @Override
    public void sort(int[] arr) {
        //for(int i=1;i<arr.length;i++) {
            insertionSort(arr, arr.length);
        //}
    }


    // 插入排序，a表示数组，n表示数组大小
    public void insertionSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j+1] = a[j];  // 数据移动
                    //只将较大的数移动，而没有移动较小的数，最后才移动较小的数，这样可以减少指令次数
                } else {
                    break;
                }
            }

            a[j+1] = value; // 插入数据
        }
    }



    public void insertSort2(int[] arr){
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && arr[j]< arr[j - 1]; j--) {
                swap(arr, j, j - 1);
            }
        }
    }
}
