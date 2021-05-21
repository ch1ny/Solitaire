package main.java.Util;

import java.util.EmptyStackException;
import java.util.Vector;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/5 22:31
 * @Version 1.0
 */
public class Stack<E> extends Vector<E> {

    public Stack() {}

    public int size() {
        return super.size();
    }

    public void push(E item) {
        addElement(item);
    }

    public E top() {
        int length = size();
        if (length == 0) {
            throw new EmptyStackException();
        }
        return elementAt(length - 1);
    }

    public synchronized E pop() {
        E item = top();
        removeElement(item);
        return item;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public synchronized int indexOf(Object obj) {
        int index = lastIndexOf(obj);
        if (index >= 0) {
            return size() - index;
        }
        return -1;
    }

}
