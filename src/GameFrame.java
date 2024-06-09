import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel panel;

    public GameFrame() {
        setTitle("Princess Quest");
        setSize(960, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new GamePanel();
        add(panel);

        setVisible(true);

        new Thread(panel).start();
    }
}
