import javax.swing.*;

public class WelcomeFrame extends JFrame {
    public WelcomeFrame() {
        setTitle("Princess Quest");
        setSize(960, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        WelcomePanel panel = new WelcomePanel(this);
        add(panel);

        setVisible(true);
    }
}
