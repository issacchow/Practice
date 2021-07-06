package com.isc;

/**
 * 本例子主要研究【bitmap算法】     其实Jdk 自带了BitSet类.....
 * (请直接查看测试用例)
 *
 * bitmap: 叫位映射, 应该叫BitMapping会合适一点
 *
 * bitmap 作用:
 * 1. 存储多个数值, 可以省下内存
 * 2. 数值排序: 每个位都表示了一个数值，并且是位与位之间是有顺序的，每次数值映射到每个位上时，已经做了一次排序
 * 3. 无重复存储: 由于每个位都只存储0和1, 去标记是否有该数值，所以当判断是否有重复时，直接查看对应索引位置是否为1即可
 *
 */
public class Main {

    public static void main( String[] args )
    {
        // 请查看测试用例
    }






}
