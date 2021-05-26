package main.java.Component;

import main.java.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/25 11:42
 * @Version 1.0
 */
public class Time extends JLabel {

    private Timer timer;

    public Time() {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setLayout(null);
        setSize(150, 75);
        setText("耗时：00:00");
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("simsum", Font.PLAIN, 25));
        setForeground(new Color(255,255,255));
    }

    public void setTime(String str) {
        setText(str);
    }

    public void start() {
        if (timer != null && timer.isAlive()) {
            timer.stop();
            timer = null;
        }
        timer = new Timer();
        timer.start();
        timer.suspend();
    }

    public void go_on() {
        timer.resume();
    }

    public void pause() {
        timer.suspend();
    }

    private String TimeFormat(long time) {
        long second = time % 60;
        long minute = (time - second) / 60;
        String sstr = String.valueOf(second);
        String mstr = String.valueOf(minute);
        if (sstr.length() < 2) {
            sstr = "0" + sstr;
        }
        if (mstr.length() < 2) {
            mstr = "0" + mstr;
        }
        return mstr + ":" + sstr;
    }

    private class Timer extends Thread {

        private long time = 0;
        private boolean count;

        public Timer() {
            count = true;
        }

        @Override
        public void run() {
            while (count) {
                try {
                    Thread.sleep(1000);
                    time++;
                    setTime("耗时：" + TimeFormat(time));
                    MainFrame.getPane().repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
