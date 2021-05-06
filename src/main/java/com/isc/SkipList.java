package com.isc;

import java.util.Iterator;
import java.util.List;

/**
 * 跳表
 * <p>
 * 作用: 简化有序单链表搜索速度
 * <p>
 * 原理: 在有序单链表基础上，增加多级索引，从下而上地创建每层索引。每层索引都源自下面的一层，每层比下层的数据量都要少。
 */
public class SkipList<T extends Comparable> extends LinkedList<T> {

    private List<LinkedList<T>> listIndexes = new java.util.LinkedList<>();


    public SkipList() {

    }


    @Override
    public Node<T> insert(T data) {
        Node node = super.insert(data);
        return node;
    }


    @Override
    public void insert(T... data) {
        super.insert(data);
    }


    /**
     * 查找指定数据的节点
     *
     * @param data
     * @return
     */
    @Override
    public Node<T> find(T data) {

        // 从最上层索引开始查找
        if (listIndexes.size() == 0) {
            // 直接从链表查找....
            return super.find(data);
        }


        // 从索引层查找
//        for (int i = listIndexes.size() - 1; i > 0; i--) {
//            LinkedList index = listIndexes.get(i);
//
//
//
//
//
//
//            // 查找到比查找数据上限边界节点
//            Iterator it = index.iterator(1);
//
//            Node<T> prev = null;
//
//            while(it.hasNext()){
//                Node<T> next = (Node<T>)it.next();
//
//                int compare = next.getData().compareTo(data);
//
//                if(compare==0){
//                    // 找到索引节点
//
//                    // 往下层找到原始节点
//                    Node down = next;
//                    while(down!=null){
//                        down = down.getDown();
//                    }
//                    return down;
//                }
//
//                if(compare>0){
//                    // 找到比当前数据大的节点,即找到某一个区间
//                    // 展开该区间，从下层索引继续查找
//
//                    // prev 为区间开始位置
//                    // next 为区间结束位置
//                    Node start = prev;
//                    Node end = next;
//
//
//                }
//                prev = next;
//            }
//        }
//
//        return null;


        int cacheIndex = listIndexes.size() - 1;
        LinkedList indexLayer = listIndexes.get(cacheIndex--);
        /**
         * 查找区间开始与结束的节点
         */
        Node<T> start = indexLayer.getFirst();
        Node<T> end = start;


        int findCount = 0;

        while (end != null && indexLayer != null) {

            findCount++;
            System.out.println("find count:" + findCount + " cache index:" + cacheIndex);

            int compare = end.getData().compareTo(data);

            if (compare == 0) {
                // 找到索引节点

                // 往下层找到原始节点
                Node down = end;
                Node prev = down;
                while (down != null) {
                    prev = down;
                    down = down.getDown();
                }
                return prev;
            }

            if (compare > 0) {
                // 找到比当前数据大的节点,即找到某一个区间
                // 展开该区间，从下层索引继续查找
                start = start.getDown();
                end = start;
                //indexLayer = listIndexes.get(cacheIndex--);
                continue;
            }


            // 找到比当前数据小的节点,继续查找 相等或大于当前数据的节点
            start = end;
            end = end.getNext();

            if (end == null) {
                // 当前索引层找不到，从下层查找
                if(cacheIndex==0){
                    // 已经没有下层，直接返回null
                    return null;
                }

                indexLayer = listIndexes.get(cacheIndex--);
                start = indexLayer.getFirst();
                end = start;
            }


        }


        return null;

    }

    public void printAllIndexes(boolean printDetails) {
        System.out.println("print all indexes...");
        System.out.println();
        Iterator<LinkedList<T>> iterator = listIndexes.iterator();
        int depth = 0;
        while (iterator.hasNext()) {
            depth++;
            System.out.println();
            for (int i = 0; i < depth; i++) {
                System.out.print("-");
            }

            LinkedList<T> next = iterator.next();
            System.out.println("depth:" + depth + ", size:" + next.getSize());
            if (printDetails) {
                next.print();
            }
        }
    }


    /**
     * 重建索引
     *
     * @param step 每层索引索引的节点步长
     */
    public void rebuildIndex(int step) {

        System.out.println("rebuild index");
        listIndexes.clear();


        // 下层索引
        LinkedList downIndex = this;


        // 每次提取节点间的步长
        int size = downIndex.getSize();


        while (size > step) {

            Node lastUpNode;
            Iterator iterator = downIndex.iterator(step);

            // 创建上层索引
            LinkedList upList = new LinkedList();

            while (iterator.hasNext()) {

                Node<T> downNode = (Node<T>) iterator.next();

                Node<T> upNode = upList.insert(downNode.getData());
                //指向下层节点
                upNode.setDown(downNode);
            }

            listIndexes.add(upList);

            downIndex = upList;
            size = downIndex.getSize();

        }


    }
}
