package main.java.Game;

import main.java.Component.Poker;
import main.java.Component.Time;
import main.java.MainFrame;

import javax.swing.*;
import main.java.Util.Queue;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/7 14:25
 * @Version 1.0
 */
public class Game {

    private static Vector<Poker> backup;
    private static DeckQueue deck; // 待发牌堆
    private static DeckQueue decked; // 已发牌堆
    private static DiscardStack[] discards; // 放置牌堆
    private static StairStack[] stairs; // 阶梯牌堆
    private static Vector<Poker> hasSeen;

    private static Time timer;

    public static void gameInit() {
        MainFrame.getPane().removeAll();
        Vector<Poker> vector = new Vector<>();
        stairs = new StairStack[7];
        discards = new DiscardStack[4];
        for (int i = 1; i < 14; i++) { // 生成扑克队列
            for (Poker.Suit suit: Poker.Suit.values()) {
                Poker poker = new Poker(i, suit);
                vector.add(poker);
            }
        }
        backup = (Vector<Poker>) vector.clone();
        hasSeen = new Vector<>();
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
                MainFrame.getPane().setLayer(poker, j + 1);
                poker.setRole("stair," + i);
                poker.setBounds(20 + i * 91,200 + j * 15, 71, 96);
                poker.setXY(20 + i * 91,200 + j * 15);
                MainFrame.getPane().add(poker, 1);
            }
            stairs[i].seen();
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
        timer = new Time();
        timer.setBounds(MainFrame.getFrame().getWidth() - 200, MainFrame.getFrame().getHeight() - 200, 150, 75);
        MainFrame.getPane().add(timer, 1);
        MainFrame.getPane().add(timer, 1);
        timer.start();
        timer.go_on();
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
        decks.setRole("deck,0");
        MainFrame.getPane().add(decks, -1);
        deckOff.setIcon(new ImageIcon("res/images/white.gif"));
        deckOff.setRole("deckOff,0");
        MainFrame.getPane().add(deckOff, -1);
    }

    public static void pause() {
        timer.pause();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Iterator<Poker> iter = backup.iterator(); iter.hasNext();) {
                    Poker poker = iter.next();
                    if (poker.isSeen()) {
                        hasSeen.add(poker);
                        poker.hidden();
                    }
                }
            }
        }).start();
    }

    public static void go_on() {
        timer.go_on();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Iterator<Poker> iter = hasSeen.iterator(); iter.hasNext();) {
                    iter.next().seen();
                }
                hasSeen.clear();
            }
        }).start();
    }

    public static DeckQueue getDeck() {
        return deck;
    }

    public static DiscardStack getDiscard(int i) {
        return discards[i];
    }

    public static StairStack getStair(int i) {
        return stairs[i];
    }

    public static void win() {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            total += discards[i].getTop();
        }
        if (total == 13 * 4) {
            pause();
            JOptionPane.showMessageDialog(null, "恭喜！您已获胜！" + timer.getText());
        }
    }

}
