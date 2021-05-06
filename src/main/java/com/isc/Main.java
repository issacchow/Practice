package com.isc;

/**
 * 还没完成查找，插入、删除
 */
public class Main {

    public static void main(String[] args) {
        SkipList<Integer> list = new SkipList();
        int len = 1000;
        for (int i = 0; i < len; i++) {
            list.insert(i);
        }

        list.sort();
//        list.print();

//        System.out.println("iterate: ");
//        Iterator iterator = list.iterator(4);
//        while (iterator.hasNext()) {
//            LinkedList<Integer>.Node<Integer> node = (LinkedList<Integer>.Node<Integer>) iterator.next();
//            System.out.println(node.getData());
//
//        }

        list.rebuildIndex(2);
        list.printAllIndexes(true);

        System.out.println();
        System.out.println("start find node:");
        LinkedList<Integer>.Node<Integer> node = list.find(999);
        System.out.println(node.toString());

    }


}
