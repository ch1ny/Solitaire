package main.java;

import main.java.Game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/5 22:11
 * @Version 1.0
 */
public class MainFrame extends JFrame {

    private static JFrame frame;
    private static JLayeredPane pane;

    public MainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        frame = new JFrame("Solitaire 纸牌游戏");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) (width * 0.8), (int) (height * 0.8));
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("res/icon/logo.png"));
        pane = new JLayeredPane();
        pane.setBackground(new Color(0,75,0));
        frame.setContentPane(pane);
        Game.gameInit();
        frame.setVisible(true);
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static JLayeredPane getPane() {return pane;}

}
