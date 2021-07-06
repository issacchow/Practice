package com.isc;

/**
 * 本例子会实现一个一致性hash算法的类
 * <p>
 * 关键问题-- hash函数的平衡性:
 * 节点计算出来的hashcode 与 key的hashcode偏差太大，
 * 导致节点在hash环上聚集在一起，而key远离聚集区，从而导致计算相邻节点时，大概率只能找到其中一个节点
 * 这是因为节点聚集在一起，并没有分散到环上各个点上造成的。
 * <p>
 * 解决方法:
 * 使用一个平衡性较好的hash函数, MurmurHash3
 */
public class Main {

    public static void main(String[] args) {


        addSimpleNodes();


        addVirtualNodes();


    }

    static void addVirtualNodes() {

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("**** add virtual nodes ****");
        System.out.println();
        System.out.println();

        Router router = new Router(Integer.MAX_VALUE);

        Node node1 = new Node("192.168.1.1", "node-1");
        Node node2 = new Node("192.168.1.2", "node-2");
        Node node3 = new Node("192.168.1.3", "node-3");
        Node node4 = new Node("192.168.1.4", "node-4");

        router.setVirtualNodesCount(5);
        router.addVirtualNode(node1);
        router.addVirtualNode(node2);
        router.addVirtualNode(node3);
        router.addVirtualNode(node4);

        System.out.println("## 均衡测试 ##");

        String key = "userid-001-carts";
        String key2 = "userid-9999-carts";
        String key3 = "userid-1000009-carts";
        String key4 = "userid-9999999-carts";
        String key5 = "userid-50000-carts";
        String key6 = "userid-3123457-carts";


        Node result1 = router.findNode(key);
        Node result2 = router.findNode(key2);
        Node result3 = router.findNode(key3);
        Node result4 = router.findNode(key4);
        Node result5 = router.findNode(key5);
        Node result6 = router.findNode(key6);

        System.out.println(" 节点比较: ");
        System.out.println(key + "  ==>  " + result1);
        System.out.println(key2 + "  ==>  " + result2);
        System.out.println(key3 + "  ==>  " + result3);
        System.out.println(key4 + "  ==>  " + result4);
        System.out.println(key5 + "  ==>  " + result5);
        System.out.println(key6 + "  ==>  " + result6);


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(" 删除节点测试 ");

        router.removeVirtualNode(node1);

        Node result6_2 = router.findNode(key6);

        System.out.println("重新查找:" + key6 + " 的节点:");
        System.out.println(key6 + "  ==>  " + result6);

        System.out.println();
        System.out.println("key:" + key6 + " 查找节点的前后对比:");
        System.out.println(result6);
        System.out.println(result6_2);


    }


    static void addSimpleNodes() {

        System.out.println();
        System.out.println();
        System.out.println("**** add simple nodes ****");
        System.out.println();
        System.out.println();


        Router router = new Router(Integer.MAX_VALUE);

        Node node = new Node("192.168.1.2", "node-1");
        Node node2 = new Node("192.168.1.3", "node-2");
        Node node3 = new Node("192.168.1.4", "node-3");
        Node node4 = new Node("192.168.1.5", "node-4");

        router.addNode(node);
        router.addNode(node2);
        router.addNode(node3);
        router.addNode(node4);


        String key = "userid-001-carts";
        String key2 = "userid-9999-carts";
        String key3 = "userid-1000009-carts";
        String key4 = "userid-9999999-carts";


        System.out.println("node :" + router.findNode(key));

        System.out.println("node :" + router.findNode(key2));

        System.out.println("node :" + router.findNode(key3));

        System.out.println("node :" + router.findNode(key4));




    }


}
