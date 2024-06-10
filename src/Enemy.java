import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy {
    private int x, y, health;
    private Animation walkAnimation, attackAnimation;
    private boolean attacking;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        health = 2;
        attacking = false;

        ArrayList<BufferedImage> walkFrames = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            try {
                walkFrames.add(ImageIO.read(new File("src/Enemy/GTwalk/GTwalk" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        walkAnimation = new Animation(walkFrames, 100);

        ArrayList<BufferedImage> attackFrames = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            try {
                attackFrames.add(ImageIO.read(new File("src/Enemy/GTattack/GTattack" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        attackAnimation = new Animation(attackFrames, 100);
    }

    public void draw(Graphics g) {
        if (attacking) {
            g.drawImage(attackAnimation.getCurrentFrame(), x, y, null);
        } else {
            g.drawImage(walkAnimation.getCurrentFrame(), x, y, null);
        }
    }

    public void update(Player player) {
        walkAnimation.update();
        if (attacking) {
            attackAnimation.update();
            if (attackAnimation.isComplete()) {
                attacking = false;
                player.takeDamage();
            }
        } else {
            if (Math.abs(player.getxCoord() - x) < 20 && Math.abs(player.getyCoord() - y) < 20) {
                attacking = true;
                attackAnimation.reset();
            }
        }
    }

    public void takeDamage() {
        health--;
        if (health <= 0) {
            //doodowkfe
        }
    }

    public int getHealth() {
        return health;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, walkAnimation.getCurrentFrame().getWidth(), walkAnimation.getCurrentFrame().getHeight());
    }
}
