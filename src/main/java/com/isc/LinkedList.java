package com.isc;

import java.util.Arrays;
import java.util.Iterator;

public class LinkedList<T extends Comparable> {

    private int size = 0;


    protected Node<T> first;
    protected Node<T> last;

    public int getSize() {
        return size;
    }

    public Node<T> getFirst() {
        return first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void print() {
        Node next = first;
        while (next != null) {
            System.out.println(next.getData());
            next = next.getNext();
        }

    }

    public void insert(T... data) {
        for (T item : data) {
            rawInsert(item);
        }

        // 每次插入完后都需要从新排序,保证从小到大
        this.sort();
    }


    public Node<T> insert(T data) {
        Node<T> node = rawInsert(data);
        return node;
    }

    public Node<T> find(T data) {

        System.out.println();
        System.out.println("start find :" + data);
        Node<T> node = first;
        int findCount = 0;
        while (node != null) {
            findCount++;
            if (node.getData().compareTo(data) == 0) {
                System.out.println("data found, find count:" + findCount);
                return node;
            }
            node = node.getNext();
        }

        System.out.println("data not found, find count:" + findCount);
        return null;
    }

    protected Node<T> rawInsert(T data) {
        if (first == null) {
            first = new Node<>(data);
            last = first;
            size++;
            return first;
        }


        Node<T> next = new Node<>(data);
        last.setNext(next);
        last = next;
        size++;


        return next;
    }


    public Iterator<Node<T>> iterator(final int step) {
        return new IteratorImpl(first, step);
    }

    public void sort() {
        if (size == 0) {
            return;
        }

        Object[] arr = this.toArray();
        Arrays.sort(arr);
        Node<T> node = first;
        int i = 0;
        while (node != null) {
            node.setData((T) arr[i++]);
            node = node.getNext();
        }
    }

    private Object[] toArray() {

        Object[] arr = new Object[size];
        Node next = first;
        int i = 0;
        while (next != null) {
            arr[i++] = next.getData();
            next = next.getNext();

        }

        return arr;

    }


    public class Node<T> {
        private T data;
        private Node next;
        private Node down;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getDown() {
            return down;
        }

        public void setDown(Node down) {
            this.down = down;
        }

        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "(node) " + data + " address: " + super.toString();
        }
    }

    private class IteratorImpl implements java.util.Iterator<Node<T>> {

        private Node<T> current;
        private int step = 1;
        boolean isFirst = true;

        public IteratorImpl(Node<T> current, int step) {
            this.current = current;
            this.step = step;
        }

        @Override
        public void remove() {

        }

        @Override
        public boolean hasNext() {

            if (isFirst) {
                return current != null;
            }

            int count = step;
            Node node = current;
            while (count-- > 0 && node != null) {
                node = node.getNext();
            }

            return node != null;
        }

        @Override
        public Node<T> next() {

            if (isFirst) {
                isFirst = false;
                return current;
            }

            int count = step;
            Node node = current;
            while (count-- > 0) {
                node = node.getNext();
            }
            current = node;
            return node;
        }
    }
}
