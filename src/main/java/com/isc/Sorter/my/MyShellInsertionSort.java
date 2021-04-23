package com.isc.Sorter.my;

import com.isc.Sorter.Sorter;
import com.isc.Sorter.SorterSupport;

/**
 * 希尔排序(改进版的插入排序)
 * 插入排序对于数量比较大时，比较很慢，所以分组进行排序,最后再应用插入排序
 * <p>
 * 思路:
 * 设置分组个数为 G, G初始值为某一个值, 从该值一直分组插入排序，直至G为1
 */
public class MyShellInsertionSort extends SorterSupport implements Sorter {


    @Override
    public void sort(int[] arr) {

        int h = 4;
        int N =12;
        while (h < N / 3) {
            h = 3 * h + 1; // 1, 4, 13, 40, ...
        }
        System.out.println(h);
    }


}
