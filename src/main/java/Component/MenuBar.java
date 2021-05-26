package main.java.Component;

import main.java.Game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/25 11:18
 * @Version 1.0
 */
public class MenuBar extends JMenuBar {

    JMenu game = new JMenu("Game");
    JMenu help = new JMenu("Help");

    JMenuItem start = new JMenuItem("Start");
    JMenuItem pause = new JMenuItem("Pause");
    JMenuItem guide = new JMenuItem("Guide");

    public MenuBar() {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Dimension dimension = new Dimension();
        dimension.width = 75;
        dimension.height = 25;
        start.setPreferredSize(dimension);
        pause.setEnabled(false);
        pause.setPreferredSize(dimension);
        game.setPreferredSize(dimension);
        game.add(start);
        game.addSeparator();
        game.add(pause);
        this.add(game);
        guide.setPreferredSize(dimension);
        help.setPreferredSize(dimension);
        help.add(guide);
        this.add(help);
        bindButtonFunction();
    }

    private void bindButtonFunction() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newGame = JOptionPane.showConfirmDialog(null, "是否要开始新游戏？", "新游戏", JOptionPane.YES_NO_OPTION);
                if (newGame == 0) {
                    Game.gameInit();
                    pause.setText("Pause");
                    pause.setEnabled(true);
                }
            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pause.getText().equals("Pause")) {
                    Game.pause();
                    pause.setText("Go on");
                } else {
                    Game.go_on();
                    pause.setText("Pause");
                }
            }
        });
        guide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Guide();
            }
        });
    }

}
