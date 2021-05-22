package main.java.Component;

import main.java.Game.DeckQueue;
import main.java.Game.DiscardStack;
import main.java.Game.Game;
import main.java.Game.StairStack;
import main.java.Main;
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
    private String ch; // 牌符
    private Suit suit; // 花色
    private boolean Seen; // 是否可视
    private int X, Y;
    private int nowX, nowY;
    private int onPressX, onPressY;
    private String role; // 担任身份

    @Override
    public void setBounds(int x, int y, int width, int height) {
        reshape(x, y, width, height);
    }

    public Poker(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.role = null;
        setCh();
        setLayout(null);
        setSize(71, 96);
        setOpaque(true);
        setBackground(new Color(255,255,255));
//        setIcon(new ImageIcon("res/images/unknown.gif"));
        if (suit == null) {
            setIcon(new ImageIcon("res/img/white.gif"));
        } else {
            setIcon(new ImageIcon(new ImageIcon("res/img/unknown.png").getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH)));
        }
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Seen) {
                    onPressX = e.getX();
                    onPressY = e.getY();
                    MainFrame.getPane().setLayer(Poker.this, Integer.MAX_VALUE);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (role.split(",")[0].equals("deck")) {
                    if (Integer.parseInt(role.split(",")[1]) == 1) {
                        Game.getDeck().deal();
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
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (role.split(",")[0].equals("stair")) {
                    StairStack stairStack = Game.getStair(Integer.parseInt(role.split(",")[1]));
                    for (int i = 0; i < stairStack.indexOf(Poker.this); i++) {

                    }
                }
                if (Seen) {
                    nowX = getX() + e.getX() - onPressX;
                    nowY = getY() + e.getY() - onPressY;
                    setLocation(nowX, nowY);
                }
            }
        });
    }

    private void setCh() {
        switch (value) {
            case 1:
                ch = "A";
                break;
            case 11:
                ch = "J";
                break;
            case 12:
                ch = "Q";
                break;
            case 13:
                ch = "K";
                break;
            default:
                ch = String.valueOf(value);
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
            int layer = MainFrame.getPane().getLayer(MainFrame.getPane().getComponentAt(x, y));
            MainFrame.getPane().setLayer(Poker.this, layer + 1);
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
                                stair.leave();
                            }
                            stairStack.push(Poker.this);
                            role = "stair," + index;
                            destX = 20 + index * 91;
                            destY = 200;
                        } else if (stairStack.add(Poker.this)) {
                            switch (role.split(",")[0]) {
                                case "stair":
                                    StairStack stair = Game.getStair(Integer.parseInt(role.split(",")[1]));
                                    Poker stairTop = stair.leave();
                                    stairTop.setRole("stair," + index);
                                    if (stair.length() > 0) {
                                        stair.top().seen();
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
                                    Poker stairTop = stair.leave();
                                    stairTop.setRole("discard," + index);
                                    if (stair.length() > 0) {
                                        stair.top().seen();
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
        X = destX;
        Y = destY;
        return destX + "," + destY;
    }

    public void setRole(String str) {
        role = str;
    }

    public String getRole() {
        return role;
    }

}
