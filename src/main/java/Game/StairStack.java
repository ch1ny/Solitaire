package main.java.Game;

import main.java.Component.Poker;
import main.java.Util.Stack;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/21 20:52
 * @Version 1.0
 * @Description 阶梯牌堆类
 */
public class StairStack {

    private static Stack<Poker> pokers;

    public StairStack() {
        pokers = new Stack<>();
    }

    // 向牌堆中置入扑克牌
    public void push(Poker poker) {
        pokers.push(poker);
    }

    // 从牌堆中移出扑克牌
    public Poker leave() {
        Poker poker = pokers.pop();
        return poker;
    }

    public Poker top() {
        return pokers.top();
    }

    public int length() {
        return pokers.size();
    }

}
