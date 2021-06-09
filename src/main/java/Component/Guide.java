package main.java.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/25 11:40
 * @Version 1.0
 * @Description 游戏向导、玩法说明
 */
public class Guide extends JDialog {

    JPanel main = new JPanel();

    JTabbedPane tab =new JTabbedPane();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JPanel jPanel5 = new JPanel();

    private JTextArea jt1 = new JTextArea("下方堆叠牌堆按照从上到下从大到小依次递减，且红黑花色交替的方式进行堆叠。");
    private JTextArea jt2 = new JTextArea("空位上只能安放四种花色的 K。");
    private JTextArea jt3 = new JTextArea("右上方的已完成牌堆分别摆放四种花色的牌，其中 A 可以直接放上去，而其他的牌只有比其牌面数值小 1 且花色完全相同的牌在上面时才能放上去。");
    private JTextArea jt4 = new JTextArea("下方有些牌背面朝上放在其他亮出的牌的下方，称为隐藏牌堆。它们只有在其上方的牌移走后才能露出。");
    private JTextArea jt5 = new JTextArea("左上方的随机牌堆中摆放着可供玩家直接操作的纸牌");

    public Guide() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle("Solitaire");
        setSize((int) (dimension.getWidth() * 0.25), (int) (dimension.getHeight() * 0.2));
        setResizable(false);
        setLocationRelativeTo(null);

        Container c = this.getContentPane();

        jt1.setSize((int) (dimension.getWidth() * 0.2), (int) (dimension.getHeight() * 0.2));
        jt2.setSize((int) (dimension.getWidth() * 0.2), (int) (dimension.getHeight() * 0.2));
        jt3.setSize((int) (dimension.getWidth() * 0.2), (int) (dimension.getHeight() * 0.2));
        jt4.setSize((int) (dimension.getWidth() * 0.2), (int) (dimension.getHeight() * 0.2));
        jt5.setSize((int) (dimension.getWidth() * 0.2), (int) (dimension.getHeight() * 0.2));

        jt1.setEditable(false);
        jt2.setEditable(false);
        jt3.setEditable(false);
        jt4.setEditable(false);
        jt5.setEditable(false);

        jt1.setLineWrap(true);
        jt2.setLineWrap(true);
        jt3.setLineWrap(true);
        jt4.setLineWrap(true);
        jt5.setLineWrap(true);

        jt1.setFont(new Font("楷体_GB2312", Font.BOLD, 13));
        jt1.setForeground(Color.black);

        jt2.setFont(new Font("楷体_GB2312", Font.BOLD, 13));
        jt2.setForeground(Color.black);

        jt3.setFont(new Font("楷体_GB2312", Font.BOLD, 13));
        jt3.setForeground(Color.black);

        jt4.setFont(new Font("楷体_GB2312", Font.BOLD, 13));
        jt4.setForeground(Color.black);

        jt5.setFont(new Font("楷体_GB2312", Font.BOLD, 13));
        jt5.setForeground(Color.black);

        jPanel1.add(jt1);
        jPanel2.add(jt2);
        jPanel3.add(jt3);
        jPanel4.add(jt4);
        jPanel5.add(jt5);

        tab.setSize((int) (dimension.getWidth() * 0.25), (int) (dimension.getHeight() * 0.2));
        tab.addTab("玩法1", null, jPanel1, null);
        tab.addTab("玩法2", null, jPanel2, null);
        tab.addTab("玩法3", null, jPanel3, null);
        tab.addTab("玩法4", null, jPanel4, null);
        tab.addTab("玩法5", null, jPanel5, null);

        main.add(tab);
        c.add(main);

        pack();
        this.setVisible(true);
    }

}
