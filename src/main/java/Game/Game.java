package main.java.Game;

import main.java.Component.Poker;
import main.java.MainFrame;

import javax.swing.*;
import main.java.Util.Queue;
import java.util.Random;
import java.util.Vector;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/7 14:25
 * @Version 1.0
 */
public class Game {

    private static Vector<Poker> vector;
    private static DeckQueue deck; // 待发牌堆
    private static DeckQueue decked; // 已发牌堆
    private static DiscardStack[] discards; // 放置牌堆
    private static StairStack[] stairs; // 阶梯牌堆

    public static void gameInit() {
        vector = new Vector<>();
        stairs = new StairStack[7];
        discards = new DiscardStack[4];
        for (int i = 1; i < 14; i++) { // 生成扑克队列
            for (Poker.Suit suit: Poker.Suit.values()) {
                Poker poker = new Poker(i, suit);
                vector.add(poker);
            }
        }
        paintRect();
        for (int i = 0; i < 4; i++) { // 初始化出牌堆
            discards[i] = new DiscardStack();
        }
        for (int i = 0; i < 7; i++) { // 生成阶梯牌堆
            stairs[i] = new StairStack();
            for (int j = 0; j <= i; j++) {
                int random = new Random().nextInt(vector.size());
                Poker poker = vector.elementAt(random);
                vector.remove(poker);
                stairs[i].push(poker);
                poker.setRole("stair," + i);
                poker.setBounds(20 + i * 91,200 + j * 15, 71, 96);
                poker.setXY(20 + i * 91,200 + j * 15);
                MainFrame.getPane().add(poker, 1);
            }
            stairs[i].top().seen();
        }
        Queue<Poker> deckQueue = new Queue<>();
        while (!vector.isEmpty()) {
            int random = new Random().nextInt(vector.size());
            Poker poker = vector.elementAt(random);
            vector.remove(poker);
            poker.setRole("deck,1");
            poker.setBounds(20,20, 71, 96);
            poker.setXY(20,20);
            MainFrame.getPane().add(poker, 1);
            deckQueue.push(poker);
        }
        deck = new DeckQueue(deckQueue);
        decked = new DeckQueue(new Queue<>());
        deckQueue = null; // 等待回收
    }

    public static void paintRect() {
        for (int i = 1; i <= 4; i++) { // 绘制出牌堆
            Poker discard = new Poker(0, null);
            discard.setLocation(MainFrame.getFrame().getWidth() - 20 - 91 * i, 20);
            discard.setIcon(new ImageIcon("res/images/white.gif"));
            discard.setRole("discard," + (i - 1));
            MainFrame.getPane().add(discard, -1);
        }
        for (int i = 0; i < 7; i++) { // 绘制阶梯牌堆
            Poker stair = new Poker(0, null);
            stair.setLocation(20 + i * 91,200);
            stair.setIcon(new ImageIcon("res/images/white.gif"));
            stair.setRole("stair," + i);
            MainFrame.getPane().add(stair, -1);
        }
        Poker decks = new Poker(0, null);
        Poker deckOff = new Poker(0, null);
        decks.setLocation(20, 20);
        deckOff.setLocation(111, 20);
        decks.setIcon(new ImageIcon("res/images/white.gif"));
        MainFrame.getPane().add(decks, -1);
        deckOff.setIcon(new ImageIcon("res/images/white.gif"));
        deckOff.setRole("deckOff,0");
        MainFrame.getPane().add(deckOff, -1);
    }

    public static DeckQueue getDeck() {
        return deck;
    }

    public static DeckQueue getDecked() {
        return decked;
    }

    public static DiscardStack getDiscard(int i) {
        return discards[i];
    }

    public static StairStack getStair(int i) {
        return stairs[i];
    }

}
