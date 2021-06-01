package com.isc;

/**
 * 测试一下CopyOnWrite在返回地址后读取，与，在写入时替换整个数据地址，是否存在冲突
 */
public class Main {

    public static void main( String[] args )
    {

        CopyOnWriteImitation imitation = new CopyOnWriteImitation();
        for (int i = 0; i < 10; i++) {
            imitation.setValue(i,i);
        }

        Integer[] orgArray = imitation.getArray();


        // 更新数值
        Integer[] arr = new Integer[10];
        imitation.setArray(arr);
        for (int i = 0; i < 10; i++) {
            imitation.setValue(i,i+1000);
        }


        // 在更新数值后，查看原来获取的引用是否被修改
        for (int i = 0; i < orgArray.length; i++) {
            System.out.println(i);
        }

        // 结果原来的引用orgArray不会被修改
        // 结论: getArray()方法返回了一个旧的地址, 当使用setArray时，不会改写orgArray的指向



    }




}
