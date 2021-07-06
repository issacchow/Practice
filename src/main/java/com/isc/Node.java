package com.isc;

import lombok.Data;
import org.apache.commons.codec.digest.MurmurHash3;

/**
 * 存储数据的节点元数据
 */
@Data
public class Node {


    private String ip;
    private String id;
    /**
     * 关联的实际节点
     * 只有当前节点是虚拟节点时，才有值
     */
    private Node realNode;

    public Node(String ip, String id) {
        this.ip = ip;
        this.id = id;
    }

    @Override
    public int hashCode() {
        // 使用有一定平衡性的hash算法
        return MurmurHash3.hash32(ip.getBytes());
    }

    @Override
    public String toString() {
        return "Node{" +
                "ip='" + ip + '\'' +
                ", id='" + id + '\'' +
                ", realNode=" + (realNode == null ? "(not found)" : "(found)") +
                ", hash code=" + this.hashCode() +
                '}';
    }
}
