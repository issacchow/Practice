package com.isc;

import org.junit.Test;

import java.util.BitSet;

import static junit.framework.TestCase.assertTrue;


/**
 * BitMap测试
 */
public class BitMapTest {

    @Test
    public void addTest() {

        BitMap map = new BitMap(10);

        /** 添加及存在校验测试 **/
        map.addNumber(0);
        map.addNumber(1);
        map.addNumber(10);

        assertTrue(map.has(0));
        assertTrue(map.has(1));
        assertTrue(map.has(10));


        /** 移除测试 **/
        map.removeNumber(10);
        assertTrue(map.has(10) == false);
    }

    @Test
    public void removeTest() {

        BitMap map = new BitMap(10);

        /** 移除测试 **/
        map.addNumber(10);
        map.removeNumber(10);
        assertTrue(map.has(10) == false);
    }


    @Test
    public void maskTest() {
        byte c = (byte) 0b11111111;
        byte b = (byte) (c << 1);
        System.out.println(b);
        b = (byte) (c << 1);
        System.out.println(b);


        System.out.println("用位移操作代替取模(循环偏移),由于byte跟short没有字面量，导致存在int转换，所以无法实现取模效果(循环偏移)");
        long o1 = 1L;
        long o2 = 1L << 64;
        assertTrue(o1 == o2);

        o1 = 2L;
        o2 = 1L << 65;
        assertTrue(o1 == o2);


        int d1 = 1;
        int d2 =  (1 << 32);
        assertTrue(d1 == d2);


    }

    @Test
    public void bitSetTest() {

        BitSet bitSet = new BitSet(90);
        bitSet.set(90);

    }

}
