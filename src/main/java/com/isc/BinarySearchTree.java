package com.isc;

import java.util.LinkedList;
import java.util.List;

/**
 * 二叉搜索树
 * <p>
 * 概要:
 * 1. 二叉搜索树并不是完全二叉树
 * 2. 本质上是二分查找算法
 * 3. 右节点的所有超子节点的数值总比左节点本身大(当然也比左节点的所有子节点数值大)
 * <p>
 * 关于插入节点概要:
 * <p>
 * 1. 插入的节点如果是小于或等于父级节点值，则成为父级字节点的左节点
 * 2. 插入的节点如果是大于父级节点，则成为父级字节点的右节点
 *
 *
 * <p>
 * 重复数据: 即数据冲突，通常是key冲突。而key冲突是指两个不同的key 所hash的值是一样的。
 * 对于重复数据(数据冲突)的处理方式有两种:
 *
 * 1. 使用兼容方式解决冲突(与解决散列 key冲突的思路一样)
 *    插入重复数据: 将相同值的数据存储在同一个节点上(比如两个key一样的对象)，使用链表或数组存储
 *    查找数据: 按正常的数值匹配方式进行查找
 *    删除数据: 整个节点删除
 *
 * 2. 链表法:
 *    插入重复数据: 将同一个节点以链表变成一个桶，并将重复的数据追加到链表后。意思是同一个hash值的不同key 存储于同一个链表上。
 *    查找数据: 根据hash(key)值找到节点，并遍历该节点的链表结构上进行key值比对，如果比对成功则返回。
 *
 */
public class BinarySearchTree<T extends Comparable> {


    private Node<T> root;
    private int nodeCount = 0;

    public BinarySearchTree() {

    }


    public void insert(T... data) {
        for (int i = 0; i < data.length; i++) {
            insert(data[i]);
        }
    }

    /**
     * 插入数据，并返回该数据所在的节点
     * 规则:
     * 1. 插入的节点如果是小于或等于父级节点值，则成为父级字节点的左节点
     * 2. 插入的节点如果是大于父级节点，则成为父级字节点的右节点
     *
     * @param data
     * @return
     */
    public Node<T> insert(T data) {
        if (root == null) {
            root = new Node<>(data);
            root.setId(nodeCount++);
            root.setPosition(NodePosition.Middle);
            return root;
        }

        Node p = root;
        while (p != null) {

            int compare = data.compareTo(p.getData());


            // 尝试插入左节点
            if (compare <= 0) {

                if (p.getLeft() == null) {
                    Node leftNode = new Node(data);
                    leftNode.setId(nodeCount++);
                    leftNode.setPosition(NodePosition.Left);
                    p.setLeft(leftNode);
                    return leftNode;
                } else {
                    // 继续遍历
                    p = p.getLeft();
                    continue;
                }

            }


            //尝试插入右节点

            if (p.getRight() == null) {
                Node rightNode = new Node(data);
                rightNode.setId(nodeCount++);
                rightNode.setPosition(NodePosition.Right);
                p.setRight(rightNode);
                return rightNode;
            }

            // 继续遍历
            p = p.getRight();


        }

        return null;

    }




    public Node<T> delete(T data) {

        System.out.println("delete node :" + data);

        if (root == null) {
            return null;
        }




        Node p = root;
        Node parent = null;
        boolean curNodeIsLeft = false;

        int result = 0;
        int searchCount = 0;
        while (p != null) {


            searchCount++;
            result = data.compareTo(p.getData());

            // 从左边查找
            if (result < 0) {
                // 继续遍历
                curNodeIsLeft = true;
                parent = p;
                p = p.getLeft();
                continue;
            }

            // 从右边查找
            if (result > 0) {
                // 继续遍历
                curNodeIsLeft = false;
                parent = p;
                p = p.getRight();
                continue;
            }

            // 找到要删除的节点

            // 1. 要删除的节点，没有任何子节点
            if (p.getLeft() == null && p.getRight() == null) {
                if (parent == p) {
                    // 根节点，直接删除
                    root = null;
                    return root;
                }

                if (curNodeIsLeft) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
                return p;
            }


            // 2. 要删除的节点是否存在两个子节点
            if (p.getLeft() != null && p.getRight() != null) {
                // 这里不能直接使用 左节点 或 右节点替换当前被删除的节点，
                // 因为使用其中一个(例如左节点)代替后，需要把另外一个(例如右节点)迁移到新上位的节点的下面
                // 但这样会导致整棵树的深度加深, 会导致查找次数增多

                /** 所以这里使用右节点的最小节点，顶替当前被删除的节点,该节点肯定比左节点大，并且树的深度可以维持不变 **/

                // 找出最小的节点,该节点没有任何子节点
                Node rightNode = p.getRight();
                Node leftNode = p.getLeft();
                Node minNode = rightNode;
                Node minNodeParent = p;
                while( minNode.getLeft()!=null){
                    minNodeParent = minNode;
                    minNode = minNode.getLeft();
                }
                minNodeParent.setLeft(null);

                // 替换当前被删除的位置
                if (curNodeIsLeft) {
                    minNode.setPosition(NodePosition.Left);
                    parent.setLeft(minNode);
                } else {
                    minNode.setPosition(NodePosition.Right);
                    parent.setRight(minNode);
                }

                // 设置新上任节点的左右,右节点
                minNode.setRight(rightNode);
                minNode.setLeft(leftNode);
                // 同时将新上任节点的原来位置清空
                minNodeParent.setLeft(null);


                /** 还有另外一种方法，就是将节点标记为删除，实现比较简单，但会占用内存 **/

                return p;
            }


            // 3. 要删除的节点,只有单个子节点

            // 提取要留下的节点
            Node node;
            if (p.getLeft() != null) {
                node = p.getLeft();
            } else {
                node = p.getRight();
            }

            if (curNodeIsLeft) {
                node.setPosition(NodePosition.Left);
                parent.setLeft(node);
            } else {
                node.setPosition(NodePosition.Right);
                parent.setRight(node);
            }

            return p;


        }

        System.out.println("search count:" + searchCount);
        return null;
    }



    /**
     * 查找所有匹配的节点
     * 用于查找重复数据的节点
     *
     * @param data
     * @return
     */
    public List<Node<T>> findAll(T data) {

        System.out.println("find all nodes...");
        List<Node<T>> list = new LinkedList<>();
        if (root == null) {
            return list;
        }

        Node p = root;

        int result = 0;
        int searchCount = 0;
        while (p != null) {

            searchCount++;
            System.out.println("search count:" + searchCount);
            result = data.compareTo(p.getData());
            if (result == 0) {

                list.add(p);

                // 从左边查找,因为插入节点时，只要少于等于父节点的节点都会插入到左边节点
                p = p.getLeft();
                continue;
            }

            // 从左边查找
            if (result < 0) {
                // 继续遍历
                p = p.getLeft();
                continue;
            }

            // 从右边查找
            if (result > 0) {
                // 继续遍历
                p = p.getRight();
                continue;
            }
        }

        return list;
    }

    /**
     * 查找第一个匹配的节点
     *
     * @param data
     * @return
     */
    public Node<T> findFirst(T data) {
        if (root == null) {
            return null;
        }

        Node p = root;

        int result = 0;
        int searchCount = 0;
        while (p != null) {

            searchCount++;
            result = data.compareTo(p.getData());
            if (result == 0) {
                System.out.println("search count:" + searchCount);
                return p;
            }

            // 从左边查找
            if (result < 0) {
                // 继续遍历
                p = p.getLeft();
                continue;
            }

            // 从右边查找
            if (result > 0) {
                // 继续遍历
                p = p.getRight();
                continue;
            }
        }

        System.out.println("search count:" + searchCount);
        return null;
    }


    public void print() {
        if (root != null) {
            printNode(root, PrintMode.PreOrder);
        }
    }

    public void printNode(Node node, PrintMode mode) {
        printNode__(node, mode, 0);
        System.out.println();
    }

    private void printNode__(Node node, PrintMode mode, int depth) {
        if (node == null) {
            return;
        }

        /** 打印tabs **/
        int tabs = depth;
        System.out.println();

        while (tabs-- > 0) {
            System.out.print("  ");
        }

        if (depth > 0) {
            System.out.printf("- %s ",node.getPosition());
        }
        System.out.printf("%s", node.getData());

        if (node.getLeft() != null) {
//            System.out.println();
//            System.out.printf("(depth:%s)", depth);
            printNode__(node.getLeft(), mode, depth + 1);
        }

        if (node.getRight() != null) {
//            System.out.println();
//            System.out.printf("(depth:%s)", depth);
            printNode__(node.getRight(), mode, depth + 1);
        }
    }


    /**
     * 打印模式
     */
    public enum PrintMode {


        /**
         * 前序
         */
        PreOrder(0),
        /**
         * 中序
         */
        MidOrder(1),
        /**
         * 后序
         */
        PostOrder(2);

        int value = -1;

        PrintMode(int value) {
            this.value = value;
        }
    }

    public enum NodePosition {

        /**
         * 中间节点，一般是根节点
         */
        Middle(0),

        /**
         * 左节点
         */
        Left(1),

        /**
         * 右节点
         */
        Right(2);

        private int value = 0;

        NodePosition(int value) {
            this.value = value;
        }


        @Override
        public String toString() {
            switch (value){
                case 0: return "(Middle)";
                case 1: return "(Left)";
                case 2: return "(Right)";
            }
            return "(Unknown)";
        }
    }


    /************ Node ************/

    /**
     * 树节点
     *
     * @param <T>
     */
    public class Node<T extends Comparable> {
        private T data;
        private Node left;
        private Node right;
        private int id;

        private NodePosition position;

        public NodePosition getPosition() {
            return position;
        }

        public void setPosition(NodePosition position) {
            this.position = position;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "node data:" + this.getData();
        }
    }


}
