package com.isc;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        testBinarySearchTree();
    }


    static void testBinarySearchTree() {


        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(33);
        tree.insert(16, 50);
        tree.insert(13, 18, 34, 58);
        tree.insert(15, 17, 25, 51, 66);
        //重复节点 55
        tree.insert(19, 27, 55, 55);
        tree.print();

        // 查找第一个匹配的节点
        BinarySearchTree<Integer>.Node<Integer> node = tree.findFirst(55);
        System.out.println();
        System.out.println(node);

        // 查找所有匹配的节点(这里有重复的节点55)
        List<BinarySearchTree<Integer>.Node<Integer>> list = tree.findAll(55);
        System.out.println();
        System.out.println("nodes found:");
        for (BinarySearchTree.Node item : list) {
            System.out.println("node id:" + item.getId());
        }


        // 删除其中一个节点
        tree.delete(13);
        tree.delete(18);
        tree.delete(55);
        tree.print();

        /**
         * 退化树的形成演示
         */
        BinarySearchTree<Integer> disablizeTree = new BinarySearchTree<>();
        disablizeTree.insert(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        disablizeTree.print();


        BinarySearchTree<Integer> disablizeTree2 = new BinarySearchTree<>();
        disablizeTree2.insert(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        disablizeTree2.print();

    }


}
