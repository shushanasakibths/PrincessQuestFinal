import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private final double MOVE_AMT = 5;
    private BufferedImage right, damagedHeart;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private int health;
    private boolean moving;
    private Animation walk, attack, idle;
    private boolean attacking;
    private ArrayList<Item> inventory;

    public Player(String name, String rightImg) {
        facingRight = true;
        xCoord = 480;
        yCoord = 240;
        health = 3;
        inventory = new ArrayList<>();
        attacking = false;
        moving = false;

        try {
            right = ImageIO.read(new File(rightImg));
            damagedHeart = ImageIO.read(new File("src/Assets/damagedheart.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<BufferedImage> walkFrames = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "src/Princess/Walk/walk" + i + ".png";
            try {
                walkFrames.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        walk = new Animation(walkFrames, 100);

        ArrayList<BufferedImage> attackFrames = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            String filename = "src/Princess/Attack/Attack" + i + ".png";
            try {
                attackFrames.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        attack = new Animation(attackFrames, 100);

        ArrayList<BufferedImage> idleFrames = new ArrayList<>();
        try {
            idleFrames.add(ImageIO.read(new File("src/Princess/Idle/IdleRight.png")));
            idleFrames.add(ImageIO.read(new File("src/Princess/Idle/IdleLeft.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        idle = new Animation(idleFrames, 500);
    }

    public void draw(Graphics g) {
        if (attacking) {
            g.drawImage(attack.getCurrentFrame(), (int)xCoord, (int)yCoord, null);
        } else if (moving) {
            g.drawImage(walk.getCurrentFrame(), (int)xCoord, (int)yCoord, null);
        } else {
            g.drawImage(idle.getCurrentFrame(), (int)xCoord, (int)yCoord, null);
        }
    }

    public void update() {
        if (attacking) {
            attack.update();
            if (attack.isComplete()) {
                attacking = false;
                attack.reset();
            }
        } else {
            if (moving) {
                walk.update();
            }
            idle.update();
        }
    }

    public void moveRight() {
        facingRight = true;
        xCoord += MOVE_AMT;
    }

    public void moveLeft() {
        facingRight = false;
        xCoord -= MOVE_AMT;
    }

    public void moveUp() {
        yCoord -= MOVE_AMT;
    }

    public void moveDown() {
        yCoord += MOVE_AMT;
    }

    public void attack() {
        attacking = true;
        attack.reset();
    }

    public void collectItem(Item item) {
        inventory.add(item);
    }

    public void takeDamage() {
        health--;
        if (health <= 0) {
        }
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public BufferedImage getHeartImage() {
        try {
            return ImageIO.read(new File("src/Assets/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getDamagedHeartImage() {
        return damagedHeart;
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }

    public int getHeight() {
        return walk.getCurrentFrame().getHeight();
    }

    public int getWidth() {
        if (facingRight) {
            return walk.getCurrentFrame().getWidth();
        } else {
            return walk.getCurrentFrame().getWidth() * -1;
        }
    }

    public Rectangle playerRect() {
        int imageHeight = walk.getCurrentFrame().getHeight();
        int imageWidth = walk.getCurrentFrame().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        moving = true;
        switch (key) {
            case KeyEvent.VK_W -> moveUp();
            case KeyEvent.VK_S -> moveDown();
            case KeyEvent.VK_A -> moveLeft();
            case KeyEvent.VK_D -> moveRight();
            case KeyEvent.VK_E -> attack();
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_S || key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            moving = false;
        }
    }

    public void setPosition(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void useKey() {
        for (Iterator<Item> it = inventory.iterator(); ((Iterator<?>) it).hasNext();) {
            Item item = it.next();
            if (item.getImagePath().contains("normalkey.png") || item.getImagePath().contains("darkkey.png")) {
                it.remove();
                break;
            }
        }
    }
}
