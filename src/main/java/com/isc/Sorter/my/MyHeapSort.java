package com.isc.Sorter.my;

import com.isc.Sorter.Sorter;
import com.isc.Sorter.SorterSupport;

/**
 * 堆排序
 * 思路:  1. 先建立【完全二叉树】结构的堆，
 *       2. 然后每个堆节点进行排序，将最大的元素(字节点和父节点)升级为父节点,
 *       3.  
 *       最终得到结果是从大到小
 * 注意: 这里的建立【完全二叉树】结构，是指将数组看作成完全二叉树结构就可以了
 */
public class MyHeapSort extends SorterSupport implements Sorter {


    @Override
    public void sort(int[] arr) {

    }

    /**
     * 获取指定的父节点
     *
     * @param nodeIndex
     * @return
     */
    public int getNode(int[] arr, int nodeIndex) {
        int indexPos = nodeIndex;
        return arr[indexPos];
    }

    /**
     * 获取指定节点的两个子节点
     *
     * @param nodeIndex
     * @return
     */
    public Integer[] getChildren(int[] arr, int nodeIndex) {
        Integer[] result = new Integer[2];
        int leftChild = nodeIndex + 1;
        int rightChild = nodeIndex + 2;
        if (leftChild >= arr.length) {
            return result;
        }

        result[0] = new Integer(arr[leftChild]);
        if (rightChild >= arr.length) {
            return result;
        }

        result[1] = new Integer(arr[rightChild]);
        return result;

    }

    /**
     * 打印成完全二叉树节点
     * { 3,   2,1 , 7,8 , 11,22 , 38,99 }
     *
     * @param arr
     */
    public void printTree(int[] arr) {

    }


}
