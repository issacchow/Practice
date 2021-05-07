package com.isc;

/**
 * 完成查找
 * 还没完成插入、删除
 */
public class Main {

    public static void main(String[] args) {
        SkipList<Integer> list = new SkipList();
        int len = 100000;
        for (int i = 1; i < len; i++) {
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
        // 跳表搜索
        LinkedList<Integer>.Node<Integer> node;
        node = list.findFromCache(99998);
        System.out.println();
        System.out.println(node.toString());


        node = list.findFromCache(5000);
        System.out.println();
        System.out.println(node.toString());


        node = list.findFromCache(1);
        System.out.println();
        System.out.println(node.toString());


        node = list.findFromCache(2000000);
        System.out.println();
        System.out.println(node==null);

        // 直接链表搜索
        LinkedList<Integer>.Node<Integer> node2 = list.find(99998);
        System.out.println(node2.toString());

    }


}
