package main.java.Game;

import main.java.Component.Poker;
import main.java.Util.Stack;

import java.util.Iterator;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/21 20:52
 * @Version 1.0
 * @Description 阶梯牌堆类
 */
public class StairStack {

    private Stack<Poker> pokers;

    public StairStack() {
        pokers = new Stack<>();
    }

    // 向牌堆中置入扑克牌
    public void push(Poker poker) {
        pokers.push(poker);
        /**
         * @Author SDU边路刘德华
         */
        for(int q = 1; q < poker.getPokerindex(); q++) {
            pokers.push(poker.getNextCard()[q]);
        }
    }

    public boolean add(Poker poker) {
        if (top().getValue() - 1 == poker.getValue()) {
            if ((Poker.Suit.valueOf(String.valueOf(top().getSuit())).ordinal() % 2) == (Poker.Suit.valueOf(String.valueOf(poker.getSuit())).ordinal() % 2)) {
                System.out.println("花色不对！");
                return false;
            } else {
                for (Iterator<Poker> iter = pokers.iterator(); iter.hasNext();) {
                    Poker tmp = iter.next();
                    if (tmp.isSeen()) {
                        tmp.addPokerindex(poker.getPokerindex());
                        tmp.addNextPocker(poker);
                        for(int q = 1;q < poker.getPokerindex(); q++) {
                            tmp.addNextPocker(poker.getNextCard()[q]);
                        }
                    }
                }
                pokers.push(poker);
                return true;
            }
        } else {
            System.out.println("牌号不对！");
            return false;
        }
    }

    // 从牌堆中移出扑克牌
    public Poker leave() {
        Poker poker = pokers.pop();
        return poker;
    }

    /**
     * @Author SDU边路刘德华
     * @Description 从牌堆中移出扑克牌
     */
    public Poker leave(Poker poker) {
        for(int q=1;q<poker.getPokerindex();q++) {
            Poker poker1 = pokers.pop();
        }
        return poker;
    }

    public Poker top() {
        if (pokers.isEmpty()) {
            return null;
        } else {
            return pokers.top();
        }
    }

    public int length() {
        return pokers.size();
    }

    public int indexOf(Poker poker) {
        int i = pokers.lastIndexOf(poker);
        if (i >= 0) {
            return length() - i;
        }
        return -1;
    }

    public Poker search(int index) {
        return pokers.get(index);
    }

    public void seen() {
        Poker[] arr = new Poker[pokers.size()];
        int i = 0;
        for (Iterator<Poker> iter = pokers.iterator(); iter.hasNext();) {
            arr[i++] = iter.next();
        }
        for (int j = i - 1; j >= 0; j--) {
            if (!arr[j].isSeen()) {
                arr[j].seen();
                return;
            }
        }
    }

}
