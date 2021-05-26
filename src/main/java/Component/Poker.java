package main.java.Component;

import main.java.Game.DiscardStack;
import main.java.Game.Game;
import main.java.Game.StairStack;
import main.java.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/5 22:21
 * @Version 1.0
 * @Description 扑克牌类
 */
public class Poker extends JLabel {

    public enum Suit {SPADE, HEART, CLUB, DIAMOND}; // 黑桃，红桃，梅花，方片

    private int value; // 牌值
    private Suit suit; // 花色
    private boolean Seen; // 是否可视
    private int X, Y;
    private int nowX, nowY;
    private int onPressX, onPressY;
    private String role; // 担任身份
    private int tmpLayer; // 原本的层数

    /**
     * @Author SDU边路刘德华
     */
    private Poker nextCard[]=null;
    int nextsize = 1;
    int pokerindex = 1;
    public int getPokerindex() {
        return pokerindex;
    }
    public void addPokerindex(int nextindex) {
        pokerindex += nextindex;
    }
    public void addNextPocker(Poker poker) {
        nextCard[nextsize]=poker;
        nextsize++;
    }
    public Poker[] getNextCard() {
        return nextCard;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        reshape(x, y, width, height);
    }

    public Poker(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.role = null;
        setLayout(null);
        setSize(71, 96);
        setOpaque(true);
        setBackground(new Color(255,255,255));
        if (suit == null) {
            setIcon(new ImageIcon("res/img/white.gif"));
        } else {
            setIcon(new ImageIcon(new ImageIcon("res/img/unknown.png").getImage().getScaledInstance(71, 96, Image.SCALE_SMOOTH)));
        }
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Seen) {
                    onPressX = e.getX();
                    onPressY = e.getY();
                    tmpLayer = MainFrame.getPane().getLayer(Poker.this);
                    MainFrame.getPane().setLayer(Poker.this, Integer.MAX_VALUE);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (role.split(",")[0].equals("deck")) {
                    if (Integer.parseInt(role.split(",")[1]) == 1) {
                        Game.getDeck().deal();
                    } else if (Integer.parseInt(role.split(",")[1]) == 0) {
                        Game.getDeck().recycle();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (Seen) {
                    setLocation(X, Y);
                    String dest = moveCard(nowX + onPressX, nowY + onPressY);
                    int destX = Integer.parseInt(dest.split(",")[0]);
                    int destY = Integer.parseInt(dest.split(",")[1]);
                    setLocation(destX, destY);
                    if (role.split(",")[0].equals("stair")) {
                        if (destX != X || destY != Y) {
                            X = destX;
                            Y = destY;
                            tmpLayer = (Y - 200) / 15 + 1;
                        }
                    } else if (role.split(",")[0].equals("discard")) {
                        X = destX;
                        Y = destY;
                        tmpLayer = value;
                    }
                    MainFrame.getPane().setLayer(Poker.this, tmpLayer);
                    Component destination = MainFrame.getPane().getComponentAt(nowX + onPressX, nowY + onPressY);
                    if (Poker.class.isInstance(destination)) {
                        Poker poker = (Poker) destination;
                        for (int i = 1; i < pokerindex; i++) {
                            nextCard[i].setRole(poker.role);
                            nextCard[i].setLocation(destX,destY + 15 * i);
                            MainFrame.getPane().setLayer(nextCard[i], tmpLayer + i);
                        }
                    }
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (role.split(",")[0].equals("stair")) {
                    StairStack stairStack = Game.getStair(Integer.parseInt(role.split(",")[1]));
                }
                if (Seen) {
                    nowX = getX() + e.getX() - onPressX;
                    nowY = getY() + e.getY() - onPressY;
                    setLocation(nowX, nowY);
                }
                for (int i = 1; i <pokerindex; i++) {
                    nextCard[i].setLocation(nowX,nowY+15*i);
                }
            }
        });
        /**
         * @Author SDU边路刘德华
         */
        nextCard=new Poker[14];
        for(int k=0;k<14;k++) {
            nextCard[k]=null;
        }
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void seen() {
        Seen = true;
        setIcon(new ImageIcon("res/img/" + (Suit.valueOf(String.valueOf(suit)).ordinal() + 1) + "-" + value + ".gif"));
    }

    public void setXY(int x, int y) {
        X = x;
        Y = y;
    }

    public String moveCard(int x, int y) {
        Component destination = MainFrame.getPane().getComponentAt(x, y);
        int destX = X;
        int destY = Y;
        if (Poker.class.isInstance(destination)) {
            Poker poker = (Poker) destination;
            if (poker.role != null) {
                String where = poker.role.split(",")[0];
                int index = Integer.parseInt(poker.role.split(",")[1]);
                switch (where) {
                    case "stair": // 试图将扑克牌移入阶梯牌堆
                        StairStack stairStack = Game.getStair(index);
                        Poker top = stairStack.top();
                        if (top == null && Poker.this.value == 13) {
                            if (Poker.this.role.split(",")[0].equals("deckOff")) {
                                Game.getDeck().getPoker();
                            } else {
                                StairStack stair = Game.getStair(Integer.parseInt(role.split(",")[1]));
                                for (int i = 0; i < pokerindex; i++) {
                                    Poker stairTop = stair.leave();
                                    stairTop.setRole("stair," + index);
                                }
                                if (stair.length() > 0 && !stair.top().isSeen()) {
                                    stair.seen();
                                }
                            }
                            stairStack.push(Poker.this);
                            role = "stair," + index;
                            destX = 20 + index * 91;
                            destY = 200;
                        } else if (stairStack.add(Poker.this)) {
                            switch (role.split(",")[0]) {
                                case "stair":
                                    StairStack stair = Game.getStair(Integer.parseInt(role.split(",")[1]));
                                    for (int i = 0; i < pokerindex; i++) {
                                        Poker stairTop = stair.leave();
                                        stairTop.setRole("stair," + index);
                                    }
                                    if (stair.length() > 0 && !stair.top().isSeen()) {
                                        stair.seen();
                                    }
                                    break;
                                case "deckOff":
                                    Poker deck = Game.getDeck().getPoker();
                                    deck.setRole("stair," + index);
                            }
                            role = top.role;
                            destX = top.getX();
                            destY = top.getY() + 15;
                        }
                        break;
                    case "discard": // 试图将扑克牌置入出牌堆
                        DiscardStack discardStack = Game.getDiscard(index);
                        if (discardStack.push(Poker.this)) {
                            switch (role.split(",")[0]) {
                                case "stair":
                                    StairStack stair = Game.getStair(Integer.parseInt(role.split(",")[1]));
                                    for (int i = 0; i < pokerindex; i++) {
                                        Poker stairTop = stair.leave();
                                        stairTop.setRole("discard," + index);
                                    }
                                    if (stair.length() > 0 && !stair.top().isSeen()) {
                                        stair.seen();
                                    }
                                    break;
                                case "deckOff":
                                    Poker deck = Game.getDeck().getPoker();
                                    deck.setRole("discard," + index);
                            }
                            role = poker.role;
                            destX = poker.getX();
                            destY = poker.getY();
                        }
                        Seen = false; // 禁止再次移动
                        break;
                }
            }
        }
        return destX + "," + destY;
    }

    public void hidden() {
        Seen = false;
        setIcon(new ImageIcon(new ImageIcon("res/img/unknown.png").getImage().getScaledInstance(71, 96, Image.SCALE_SMOOTH)));
    }

    public boolean isSeen() {
        return Seen;
    }

    public void setRole(String str) {
        role = str;
    }

}
