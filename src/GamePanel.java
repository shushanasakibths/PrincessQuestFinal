import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements KeyListener, MouseListener, ActionListener, Runnable {
    private BufferedImage background;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Item> items;
    private Timer timer;
    private int time;
    private boolean running;
    private int level;
    private boolean keyGiven;

    public GamePanel() {
        level = 0;
        initLevel();

        time = 0;
        running = true;
        keyGiven = false;

        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();

        timer = new Timer(1000, this);
        timer.start();
    }

    private void initLevel() {
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        keyGiven = false;

        switch (level) {
            case 0:
                loadBackground("src/Assets/start.png");
                items.add(new Item("src/Assets/chest.png", 300, 200));
                player = new Player("Princess", "src/Princess/Idle/IdleRight.png");
                player.setPosition(480, 240);
                break;
            case 1:
                loadBackground("src/Assets/level1.png");
                enemies.add(new Enemy(200, 200));
                enemies.add(new Enemy(400, 400));
                //items.add(new Item("src/Assets/heart.png", 100, 100));
                player.setPosition(50, 240);
                break;
            case 2:
                loadBackground("src/Assets/level2.png");
                for (int i = 0; i < 6; i++) {
                    enemies.add(new Enemy(200 + (i * 50), 200));
                }
                //items.add(new Item("src/Assets/heart.png", 150, 150));
                player.setPosition(50, 240);
                break;
            case 3:
                loadBackground("src/Assets/level3.png");
                items.add(new Item("src/Assets/chest.png", 300, 200));
                player.setPosition(50, 240);
                break;
            case 4:
                loadBackground("src/Assets/win.png");
                running = false;
                break;
            case 5:
                loadBackground("src/Assets/death.png");
                running = false;
                break;
            default:
                break;
        }
    }

    private void loadBackground(String path) {
        try {
            background = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        player.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        for (Item item : items) {
            item.draw(g);
        }
        drawHUD(g);
    }

    private void drawHUD(Graphics g) {
        // Draw player health
        for (int i = 0; i < player.getHealth(); i++) {
            g.drawImage(player.getHeartImage(), 20 + i * 60, 20, null);
        }
        for (int i = player.getHealth(); i < 3; i++) {
            g.drawImage(player.getDamagedHeartImage(), 20 + i * 60, 20, null);
        }
        // Draw inventory
        for (int i = 0; i < player.getInventory().size(); i++) {
            g.drawImage(player.getInventory().get(i).getImage(), 900 - i * 40, 20, null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        checkLevelTransition();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        for (Item item : items) {
            if (item.contains(click) && item.getImagePath().contains("chest.png")) {
                if (level == 3) {
                    player.collectItem(new Item("src/Assets/darkkey.png", 0, 0));
                } else {
                    player.collectItem(new Item("src/Assets/normalkey.png", 0, 0));
                }
                items.remove(item);
                break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time++;
            if (time >= 60) {
                running = false;
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            player.update();
            for (Enemy enemy : enemies) {
                enemy.update(player);
            }
            checkEnemyCollisions();
            checkPlayerHealth();
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkLevelTransition() {
        boolean hasKey = false;
        for (Item item : player.getInventory()) {
            if (item.getImagePath().contains("normalkey.png") || item.getImagePath().contains("darkkey.png")) {
                hasKey = true;
                break;
            }
        }

        switch (level) {
            case 0:
            case 1:
                if (hasKey && player.getxCoord() > 520) {
                    player.useKey();
                    level++;
                    initLevel();
                }
                break;
            case 2:
                if (hasKey && player.getxCoord() > 906) {
                    player.useKey();
                    level++;
                    initLevel();
                }
                break;
            case 3:
                if (hasKey && player.getxCoord() > 700) {
                    player.useKey();
                    level++;
                    initLevel();
                }
                break;
            default:
                break;
        }
    }

    private void checkEnemyCollisions() {
        if (player.isAttacking()) {
            Rectangle playerRect = player.playerRect();
            for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
                Enemy enemy = it.next();
                if (playerRect.intersects(enemy.getRect())) {
                    enemy.takeDamage();
                    if (enemy.getHealth() <= 0) {
                        it.remove();
                    }
                }
            }
            if (!keyGiven && enemies.isEmpty() && (level == 1 || level == 2)) {
                player.collectItem(new Item("src/Assets/normalkey.png", 0, 0));
                keyGiven = true;
            }
        }
    }

    private void checkPlayerHealth() {
        if (player.getHealth() <= 0) {
            level = 5;
            initLevel();
        }
    }
}
