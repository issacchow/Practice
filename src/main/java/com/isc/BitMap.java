package com.isc;

/**
 * 位映射类( 其实jdk本身就自带 BitSet类........ )
 * 是一个超级byte,存储多个值的容器。
 * 只适合存储及快速定位
 */
public class BitMap {

    private byte[] bytes;
    // 位的长度
    private int maxNumber;

    /**
     * 构造函数
     *
     * @param maxNumber 可以表示的最大的数值，同时也表示最多可以存储maxNumber+1个数值
     */
    public BitMap(int maxNumber) {
        bytes = new byte[maxNumber / 8 + 1];
        this.maxNumber = maxNumber;
    }

    /**
     * 查找位的索引所关联的byte索引位置
     *
     * @param bitIndex
     * @return
     */
    private int indexOfBytes(int bitIndex) {
//        if (bitIndex > bytes.length * 8) {
//            throw new IllegalArgumentException("索引位置超出了映射范围");
//        }


        // 计算对应byte的位置 (int整除后，会去掉小数点)
        return bitIndex / 8;
    }

    /**
     * 设置指定位置的位的真假标记
     *
     * @param bitIndex 索引位置，从0开始
     * @param flag     标志位
     */
    private void setBitFlag(int bitIndex, boolean flag) {

        int bytePos = indexOfBytes(bitIndex);


        // 计算位在target的位置
        int bitPos = (bitIndex % 8);
        bitPos = bitPos == 0 ? 1 : 1 << bitPos;

        if (flag == true) {
            bytes[bytePos] |= bitPos;
        } else {
            bytes[bytePos] &= ~bitPos;
        }
    }

    /**
     * 判断是否含有指定的数值
     *
     * @param number
     * @return
     */
    public boolean has(int number) {

        int bytePos = indexOfBytes(number);

        byte target = bytes[bytePos];

        // 计算位在target的位置
        int bitPos = (number % 8);
        bitPos = bitPos == 0 ? 1 : 1 << bitPos;


        return (target & bitPos) != 0;
    }

    /**
     * 添加一个数值
     *
     * @param number
     */
    public void addNumber(int number) {
        setBitFlag(number, true);
    }

    /**
     * 删除一个数值
     *
     * @param number
     */
    public void removeNumber(int number) {
        setBitFlag(number, false);
    }



}
