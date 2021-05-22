package main.java.Game;

import main.java.Component.Poker;
import main.java.MainFrame;
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

    public int cycleLayer() {
        if (recycle.isEmpty()) {
            return 0;
        } else {
            return MainFrame.getPane().getLayer(recycle.back());
        }
    }

    // 发牌
    public void deal(Poker poker) {
        recycle.push(poker);
    }

    public void deal() {
        Poker deal = deck.pop();
        deal.setRole("deckOff,1");
        deal.setXY(111, 20);
        deal.setLocation(111, 20);
        deal.seen();
        MainFrame.getPane().setLayer(deal, cycleLayer() + 1);
        recycle.push(deal);
    }

    public Poker front() {
        if (!deck.isEmpty()) {
            return deck.pop();
        }
        return null;
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
