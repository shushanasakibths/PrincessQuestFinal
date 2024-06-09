import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePanel extends JPanel implements KeyListener {
    private JFrame frame;
    private BufferedImage titleScreen;

    public WelcomePanel(JFrame frame) {
        this.frame = frame;
        try {
            titleScreen = ImageIO.read(new File("src/Assets/TitleScreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleScreen, 0, 0, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            frame.setVisible(false);
            new GameFrame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
