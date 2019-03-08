import java.awt.event.*;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSlider;
import java.awt.FlowLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BubblePanel extends JPanel {
    Random rand = new Random();
    ArrayList<Bubble> bubbleList;
    int size = 35;
    Timer timer;
    int delay = 33;
    JSlider slider;

    public BubblePanel() {
        timer = new Timer(delay, new BubbleListener());
        bubbleList = new ArrayList<Bubble>();
        setBackground(Color.BLACK);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(12, 13, 496, 62);
        add(panel);

        JButton btnPause = new JButton("Pause");
        btnPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if (btn.getText().equals("Pause")) {
                    timer.stop();
                    btn.setText("Start");
                } else {
                    timer.start();
                    btn.setText("Pause");
                }
            }
        });
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblAnimationSpeed = new JLabel("Animation Speed");
        lblAnimationSpeed.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(lblAnimationSpeed);

        slider = new JSlider();
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int speed = slider.getValue() + 1;
                int delay = 1000 / speed;
                timer.setDelay(delay);
            }
        });
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(30);
        slider.setMinorTickSpacing(5);
        slider.setMaximum(120);
        panel.add(slider);
        panel.add(btnPause);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                bubbleList = new ArrayList<Bubble>();
                repaint();
            }
        });
        panel.add(btnClear);

        JLabel label = new JLabel("");
        label.setBounds(12, 13, 56, 16);
        add(label);
        //testBubbles();
        addMouseListener(new BubbleListener());
        addMouseMotionListener(new BubbleListener());
        addMouseWheelListener(new BubbleListener());
        timer.start();
    }

    public void paintComponent(Graphics canvas) {
        super.paintComponent(canvas);
        for (Bubble b : bubbleList) {
            b.draw(canvas);
        }
    }

    private class BubbleListener extends MouseAdapter implements ActionListener {
        public void mousePressed(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }

        public void mouseDragged(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            if (System.getProperty("os.name").startsWith("Mac"))
                size += e.getUnitsToScroll();
            else
                size -= e.getUnitsToScroll();
            if (size < 3)
                size = 3;
        }

        public void actionPerformed(ActionEvent e) {
            for (Bubble b : bubbleList)
                b.update();
            repaint();

        }
    }

    private class Bubble {
        private int x;
        private int y;
        private int size;
        private Color color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 5;

        public Bubble(int newX, int newY, int newSize) {
            x = newX;
            y = newY;
            size = newSize;
            color = new Color(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));
            xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
        }

        public void draw(Graphics canvas) {
            canvas.setColor(color);
            canvas.fillOval(x - size / 2, y - size / 2, size, size);
        }

        public void update() {
            x += xspeed;
            y += yspeed;
            if (x - size / 2 <= 0 || x + size / 2 >= getWidth())
                xspeed = -xspeed;
            if (y - size / 2 <= 0 || y + size / 2 >= getHeight())
                yspeed = -yspeed;

        }
    }
}
