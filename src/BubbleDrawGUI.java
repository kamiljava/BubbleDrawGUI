import javax.swing.JFrame;
public class BubbleDrawGUI extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BubbleDraw App GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BubblePanel());
        frame.setSize(new java.awt.Dimension(1000,800));
        frame.setVisible(true);
    }
}

