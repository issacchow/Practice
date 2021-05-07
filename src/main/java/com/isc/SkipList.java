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

    private List<LinkedList<T>> listCache = new java.util.LinkedList<>();


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
    public Node<T> findFromCache(T data) {

        // 从最上层索引开始查找
        if (listCache.size() == 0) {
            // 直接从链表查找....
            return super.find(data);
        }


        int cacheIndex = listCache.size() - 1;
        LinkedList indexLayer = listCache.get(cacheIndex--);
        /**
         * 查找区间开始与结束的节点
         */
        Node<T> start = indexLayer.getFirst();
        Node<T> end = start;


        int findCount = 0;

        while (end != null) {

            findCount++;
            System.out.println();
            System.out.printf("find count:%s ,  find [%s] from %s   , in cache layer: %s", findCount, data, end.getData(), cacheIndex);

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

                if (start != null && start.getNext() != null) {
                    // 原本只有 end = start, 但为了减少一次循环，这里向后进一步遍历
                    end = start.getNext();
                } else {
                    end = start;
                }

                // 记录跳到下层的层数
                if (start != null) {
                    cacheIndex--;
                }
                continue;
            }


            // 找到比当前数据小的节点,继续查找 相等或大于当前数据的节点
            start = end;
            end = end.getNext();

            if (end == null) {
                // 当前索引层找不到比目标数据要大的数据(即当前缓存层找到最后一个点 <= 目标数值)
                // 从下层查找

                // 比如查找100,在缓存层:
                // 1, 2, 3, ...,99
                // 中找不到比100大的点，则使用最后一个点向下层搜索


                start = start.getDown();

                if (start != null && start.getNext() != null) {
                    // 原本只有 end = start, 但为了减少一次循环，这里向后进一步遍历
                    end = start.getNext();
                } else {
                    end = start;
                }

                // 记录跳到下层的层数
                if (start != null) {
                    cacheIndex--;
                }
            }
        }

        System.out.println();
        System.out.println("data not found:" + data);

        return null;

    }

    public void printAllIndexes(boolean printDetails) {
        System.out.println("print all indexes...");
        System.out.println();
        Iterator<LinkedList<T>> iterator = listCache.iterator();
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
        listCache.clear();


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

            listCache.add(upList);

            downIndex = upList;
            size = downIndex.getSize();

        }


    }
}
