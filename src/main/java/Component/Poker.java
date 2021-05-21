package main.java.Component;

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
    private int onPressX, onPressY;

    @Override
    public void setBounds(int x, int y, int width, int height) {
        reshape(x, y, width, height);
    }

    public Poker(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        setCh();
        setLayout(null);
        setSize(71, 96);
        setOpaque(true); // JLabel默认是透明的，所以将其设置为非透明，否则设置背景色是没有意义的
        setBackground(new Color(255,255,255));
//        setBackground(new Color(255,255,255, 125));
//        setIcon(new ImageIcon("res/images/unknown.gif"));
        if (suit == null) {
            setIcon(new ImageIcon("res/images/white.gif"));
        } else {
            setIcon(new ImageIcon("res/images/" + (Suit.valueOf(String.valueOf(suit)).ordinal() + 1) + "-" + value + ".gif"));
        }
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onPressX = e.getX();
                onPressY = e.getY();
                System.out.println(Poker.this.getValue());
                MainFrame.getPane().setLayer(Poker.this, 2);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (Seen) {
                    setLocation(X, Y);
                    MainFrame.getPane().setLayer(Poker.this, 1);
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (Seen) {
                    setLocation(getX() + e.getX() - onPressX, getY() + e.getY() - onPressY);
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
    }

    public void setXY(int x, int y) {
        X = x;
        Y = y;
    }

}
