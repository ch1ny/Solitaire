package main.java.Game;

import main.java.Component.Poker;
import main.java.Util.Queue;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/5 22:56
 * @Version 1.0
 * @Description 发牌堆逻辑类
 */
public class DeckQueue {

    private Queue<Poker> deck;
    private Queue<Poker> recycle; // 回收牌堆

    public DeckQueue(Queue<Poker> queue) {
        deck = queue;
        recycle = new Queue<>();
    }

    // 发牌
    public Poker deal() {
        Poker Poker = deck.pop();
        recycle.push(Poker);
        return Poker;
    }

    // 回收牌堆
    public void recycle() {
        deck = recycle;
        recycle.clear();
    }

    // 从回收牌堆取牌
    public Poker getPoker() {
        return recycle.leave();
    }

}
