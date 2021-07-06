package com.isc;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 路由器, 一致性hash路由器
 * 通过路由器找到对应的节点
 */

public class Router {

    /**
     * 哈希环，环上的key取值范围为 0 ~ 2的32次方-1
     */
    private SortedMap<Integer, Node> hashRing;

    /**
     * 虚拟节点数量
     */
    private int virtualNodesCount = 8;


    public int getVirtualNodesCount() {
        return virtualNodesCount;
    }

    public void setVirtualNodesCount(int virtualNodesCount) {
        this.virtualNodesCount = virtualNodesCount;
    }

    /**
     * 环的大小
     */
    private int ringSize = 1024;

    public Router(int ringSize) {
        this.ringSize = ringSize;
        this.hashRing = new TreeMap<>();
    }

    private Node buildVirtualNode(Node realNode, int number) {
        String ip = realNode.getIp() + "#" + number;
        String id = ip;
        Node virtualNode = new Node(ip, id);
        virtualNode.setRealNode(realNode);
        return virtualNode;
    }

    /**
     * 添加虚拟节点
     * 虚拟节点的数量请查看virtualNodesCount属性
     *
     * @param realNode 真实节点
     * @see #setVirtualNodesCount(int)
     */
    public void addVirtualNode(Node realNode) {


        System.out.println("-- add virtual nodes begin");
        System.out.println("* real node:" + realNode);
        int virtualCount = getVirtualNodesCount();
        for (int number = 0; number < virtualCount; number++) {
            Node virtualNode = buildVirtualNode(realNode, number + 1);
            this.addNode(virtualNode);
        }
        System.out.println("-- add virtual nodes complete");
        System.out.println();
    }


    public void removeVirtualNode(Node realNode) {
        System.out.println("-- remove virtual nodes begin");
        System.out.println("* real node:" + realNode);
        int virtualCount = getVirtualNodesCount();
        for (int number = 0; number < virtualCount; number++) {
            Node virtualNode = buildVirtualNode(realNode, number + 1);
            this.removeNode(virtualNode);
        }
        System.out.println("-- remove virtual nodes complete");
        System.out.println();
    }

    /**
     * 添加一个节点
     *
     * @param node
     */
    public void addNode(Node node) {

        int hash = node.hashCode();
        hash = getRingHashCode(hash);
        System.out.println("-- add node, hash code: " + node.hashCode() + " ring hash code:" + hash + " , node:" + node);
        hashRing.putIfAbsent(hash, node);

    }


    /**
     * 删除一个节点
     *
     * @param node
     */
    public void removeNode(Node node) {
        int hash = node.hashCode();
        hash = getRingHashCode(hash);
        System.out.println("-- remove node, hash code:" + node.hashCode() + " ring hash code:" + hash + " , node:" + node);
        Node old = hashRing.remove(hash);
        if (old == null) {
            System.out.println(" 移除失败 ");

        }
    }


    public Node findNode(Object key) {

        System.out.println("-- start find node");
        System.out.println("key : " + key);

        int hash = key.hashCode();
        hash = getRingHashCode(hash);
        System.out.println("ring hash code of key:" + hash);


        if (this.hashRing.isEmpty()) {
            return null;
        }


        SortedMap<Integer, Node> map = this.hashRing.tailMap(hash);
        if (map == null || map.isEmpty()) {
            // 没有找到比当前key的hash要大的节点, 则从hash环的起始位置查找
            map = this.hashRing.tailMap(0);
        }

        Integer integer = map.firstKey();
        if (integer == null) {
            return null;
        }

        Node node = map.get(integer);
        if (node == null) {
            return null;
        }

        System.out.println("result:");

        if (node.getRealNode() != null) {
            System.out.println("found a virtual node: " + node + " , hash code:" + node.hashCode() + " , ring hash code:" + getRingHashCode(node.hashCode()));
            // 当前节点为虚拟节点，返回实际节点
            System.out.println("return real node:" + node.getRealNode());
            System.out.println();
            return node.getRealNode();
        }

        System.out.println("return real node:" + node + " , hash code:" + node.hashCode() + " , ring hash code:" + getRingHashCode(node.hashCode()));
        System.out.println();
        return node;
    }

    private int getRingHashCode(int hash) {
        return Math.abs(hash % ringSize);
    }

}
