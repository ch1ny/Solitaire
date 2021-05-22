package main.java.Game;

import main.java.Component.Poker;
import main.java.Util.Stack;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/5 22:57
 * @Version 1.0
 * @Description 出牌堆逻辑类
 */
public class DiscardStack {

    private Poker.Suit suit;
    private Stack<Poker> disPoker;
    private int top; // 栈顶牌号

    public DiscardStack() {
        suit = null;
        disPoker = new Stack<>();
        top = 0;
    }

    // 向牌堆中置入扑克牌
    public boolean push(Poker Poker) {
        if (suit == null) {
            suit = Poker.getSuit();
        } else if (suit != Poker.getSuit()) {
            System.out.println("花色不匹配！");
            return false;
        }
        if (top + 1 == Poker.getValue()) {
            disPoker.push(Poker);
            top++;
        } else {
            System.out.println("牌号不对！");
            return false;
        }
        return true;
    }

    // 从牌堆中移出扑克牌
    public Poker leave() {
        Poker Poker = disPoker.pop();
        top--;
        if (top == 0) {
            suit = null;
        }
        return Poker;
    }

    public int getTop() {
        return top;
    }

}
