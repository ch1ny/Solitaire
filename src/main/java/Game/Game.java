package main.java.Game;

import main.java.Component.Poker;
import main.java.MainFrame;

import javax.swing.*;
import java.util.Random;
import java.util.Vector;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/7 14:25
 * @Version 1.0
 */
public class Game {

    private static Vector<Poker> vector;
    private static DeckQueue deck; // 发牌堆
    private static DiscardStack[] discards; // 放置牌堆
    private static StairStack[] stairs; // 阶梯牌堆

    public static void gameInit() {
        vector = new Vector<>();
        discards = new DiscardStack[4];
        stairs = new StairStack[7];
        for (int i = 1; i < 14; i++) { // 生成扑克队列
            for (Poker.Suit suit: Poker.Suit.values()) {
                Poker poker = new Poker(i, suit);
                vector.add(poker);
            }
        }
        for (int i = 1; i <= 4; i++) { // 绘制出牌堆
            Poker discard = new Poker(0, null);
            discard.setLocation(MainFrame.getFrame().getWidth() - 20 - 91 * i, 20);
            discard.setIcon(new ImageIcon("res/images/white.gif"));
            MainFrame.getPane().add(discard);
        }
        for (int i = 0; i < 7; i++) {
            stairs[i] = new StairStack();
            for (int j = 0; j <= i; j++) {
                int random = new Random().nextInt(vector.size());
                Poker poker = vector.elementAt(random);
                vector.remove(poker);
                stairs[i].push(poker);
                poker.setBounds(20 + i * 91,200 + j * 15, 71, 96);
                poker.setXY(20 + i * 91,200 + j * 15);
                MainFrame.getPane().add(poker, 0);
            }
            System.out.println(stairs[i].top().getSuit() + ":" + stairs[i].top().getValue());
            stairs[i].top().seen();
        }
        int random = new Random().nextInt(vector.size());
        Poker poker = vector.elementAt(random);
        vector.remove(poker);
        poker.setBounds(20,20, 71, 96);
        MainFrame.getPane().add(poker);
    }

}
