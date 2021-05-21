package main.java.Util;

import java.util.EmptyStackException;
import java.util.Vector;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/5 22:40
 * @Version 1.0
 */
public class Queue<E> extends Vector<E> {

    public Queue() {}

    public int size() {
        return super.size();
    }

    public void push(E item) {
        addElement(item);
    }

    public E front() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return elementAt(0);
    }

    public E back() {
        int length = size();
        if (length == 0) {
            throw new EmptyStackException();
        }
        return elementAt(length - 1);
    }

    // 从队首移出
    public synchronized E pop() {
        E item = front();
        removeElement(item);
        return item;
    }

    // 队尾元素离开队列
    public synchronized E leave() {
        E item = back();
        removeElement(item);
        return item;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public synchronized int indexOf(Object obj) {
        return lastIndexOf(obj);
    }

}
