package com.isc.Sorter.my;

import com.isc.Sorter.Sorter;
import com.isc.Sorter.SorterSupport;

/**
 * 归并排序(分治算法)
 * 思路: 通过二分法，将排序的原始数组分成两个阵型的数组，然后每个阵型依次从第一个派出一个代表出来进行PK, PK赢了就排队到名次队列中。
 * 但前提下，两个阵型内部本身就已经排序好，意思是两边数组都进一步再细分很多个数组，直到出现最小数组(即长度为2),然后再比较两个元素
 * <p>
 * 思路2: 类似选举，从底层一直选举到高层,即从最小圈子里排序，然后再往上一层与其他同等大小的圈子进行选举
 * <p>
 * 注意:
 * 需要注意数组长度为 奇偶 情况下，两边分组的长度, 当为奇数时，左边数组比右边数组长度 多1
 * 例子: {3, 8, 10, 3}; {8 ,10 ,3,3 }
 */
public class MyMergeSort extends SorterSupport implements Sorter {

    @Override
    public void sort(int[] arr) {

//        merge(arr, 0, arr.length / 2, arr.length-1);
        merge(arr, 0, arr.length - 1);
//        System.arraycopy(result, 0, arr, 0, arr.length);
    }


    private void merge(int[] arr, int rangeStart, int rangeEnd) {

        logln();
        logln(" ## sort range from:" + rangeStart + " to " + rangeEnd);

        logln();
        logln("raw array:");
//        dumpArr(arr);

        logln();
        logln("range of array:");
//        dumpArr(arr, rangeStart, rangeEnd);


        if (rangeEnd == rangeStart) {
            logln("only one element, return");
            return;
        }

        // 如果要排序的只是两个元素，则直接比较
        if (rangeEnd - 1 == rangeStart) {
            log("only two element,compare:");
            if (arr[rangeEnd] <= arr[rangeStart]) {
                swap(arr, rangeStart, rangeEnd);
                log("swap");
            } else {
                log("no swap");
            }
            return;
        }

        // 要比较的区间的整个长度
        final int LEN_OF_RANGE = rangeEnd - rangeStart + 1;

        /** 二分法，分成两个组，分别用两个指针指向两个数组的开始位置 **/

        // 中间坐标
        int MID = LEN_OF_RANGE / 2 + rangeStart;
        logln("MID:" + MID);
        if (LEN_OF_RANGE % 2 == 0) {
            //MID--;
        }
        int rightPtr = MID + 1;
        int leftPtr = rangeStart;


        log("leftPtr:%s - MID:%s - rightPtr:%s - length of range:%s", leftPtr, MID, rightPtr, LEN_OF_RANGE);
        logln();
        log("left value:%s - MID value:%s - right value:%s", arr[leftPtr], arr[MID], arr[rightPtr]);

        logln();
        logln("left array:");
//        dumpArr(arr,leftPtr,MID);

        logln();
        logln("right array:");
//        dumpArr(arr,rightPtr,rangeEnd);


        /** 分别将左右两边数组进一步细分排序 **/

        // 计算左边数组的中间位置
        logln();

        log(" - sort <left> range from %s to %s", rangeStart, MID);
        merge(arr, rangeStart, MID);

        // 计算右边数组的中间位置
        logln();
        log(" - sort <right> range from %s to %s", MID, rangeEnd);
        merge(arr, MID + 1, rangeEnd);


        /** 大组排序 **/

        logln();
        log(" merge from %s to %s", leftPtr, rightPtr);

        int[] mergeArr = new int[LEN_OF_RANGE];
        int mergeArrPtr = 0;


        while (leftPtr <= MID && rightPtr <= rangeEnd) {

            // 左边值较小 或 两边值相等
            if (arr[leftPtr] <= arr[rightPtr]) {
                mergeArr[mergeArrPtr++] = arr[leftPtr++];
                continue;
            }

            // 右边值较小
            mergeArr[mergeArrPtr++] = arr[rightPtr++];
        }

        // 尝试填充剩余没有被比较的数，到合并数组上
        while (leftPtr <= MID) {
            mergeArr[mergeArrPtr++] = arr[leftPtr++];
        }

        while (rightPtr <= rangeEnd) {
            mergeArr[mergeArrPtr++] = arr[rightPtr++];
        }

        logln();
        logln();
//        logln("merge array:");
//        dumpArr(mergeArr);

        // 到此为止已经将当前数组，按照二分法分成两部分，并且排序
        System.arraycopy(mergeArr, 0, arr, rangeStart, LEN_OF_RANGE);

        logln();
//        logln("merge finish:");
//        dumpArr(arr);


    }

    private void merge(int[] arr, int leftPtr, int rightPtr, int EOR) {


        // 如果要排序的只是两个元素，则直接比较
        if (rightPtr - 1 == leftPtr) {
            if (arr[rightPtr] <= arr[leftPtr]) {
                swap(arr, leftPtr, rightPtr);
            }

            return;
        }

        // 左边数组最大值
        final int EOL = rightPtr;

        // 要进行排序区间的长度
        final int RANGE = EOR - leftPtr;

        // 左边数组长度
        final int LEN_OF_LEFT = rightPtr - leftPtr;
        // 右边数组长度
        final int LEN_OF_RIGHT = EOR - rightPtr;


        int[] mergeArr = new int[arr.length];
        int mergeArrPtr = 0;
        /**/

        while (leftPtr < EOL && rightPtr < EOR) {

            // 左边值较小 或 两边值相等
            if (arr[leftPtr] <= arr[rightPtr]) {
                mergeArr[mergeArrPtr++] = arr[leftPtr++];
                continue;
            }

            // 右边值较小
            mergeArr[mergeArrPtr++] = arr[rightPtr++];
        }

        // 尝试填充剩余没有被比较的数，到合并数组上
        while (leftPtr < EOL) {
            mergeArr[mergeArrPtr++] = arr[leftPtr++];
        }

        while (rightPtr < EOR) {
            mergeArr[mergeArrPtr++] = arr[rightPtr++];
        }

        // 到此为止已经将当前数组，按照二分法分成两部分，并且排序
        System.arraycopy(mergeArr, leftPtr, arr, leftPtr, RANGE);

        // 进一步细分这两部分

        // 计算左边数组的中间位置
        int MID_OF_LEFT = (leftPtr - LEN_OF_LEFT) + LEN_OF_LEFT / 2;
        merge(arr, leftPtr, MID_OF_LEFT, leftPtr + LEN_OF_LEFT);

//        // 计算右边数组的中间位置
//        int MID_OF_RIGHT = (rightPtr + LEN_OF_RIGHT) / 2;
//        merge(arr, )

    }


    // 测试原型
    private int[] ___merge___(int[] arr) {

        final int MID = arr.length / 2;
        final int LEN = arr.length;
        int leftPtr = 0;
        int rightPtr = MID;


        int[] mergeArr = new int[arr.length];
        int mergeArrPtr = 0;


        while (leftPtr < MID && rightPtr < LEN) {

            // 左边值较小 或 两边值相等
            if (arr[leftPtr] <= arr[rightPtr]) {
                mergeArr[mergeArrPtr++] = arr[leftPtr++];
                continue;
            }

            // 右边值较小
            mergeArr[mergeArrPtr++] = arr[rightPtr++];
        }

        // 尝试填充剩余没有被比较的数，到合并数组上
        while (leftPtr < MID) {
            mergeArr[mergeArrPtr++] = arr[leftPtr++];
        }

        while (rightPtr < LEN) {
            mergeArr[mergeArrPtr++] = arr[rightPtr++];
        }


        return mergeArr;

    }

}
