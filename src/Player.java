import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private final double MOVE_AMT = 0.6;
    private BufferedImage right;
    private boolean facingRight;
    private double xCoord;
    private double yCoord;
    private int score;
    private String name;

    private BufferedImage idleRight;
    private BufferedImage idleLeft;
    private BufferedImage idleDown;
    private BufferedImage idleUp;
    private Animation walk;
    private Animation walkUp;
    private Animation walkDown;
    private Animation attack;
    private Animation attackUp;
    private Animation attackDown;


    public Player(String rightImg, String name) {
        this.name = name;
        facingRight = true;
        xCoord = 50;
        yCoord = 435;
        score = 0;
        try {
            right = ImageIO.read(new File(rightImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        ArrayList<BufferedImage> walk_animation = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "src/Princess/Walk/walk" + i + ".png";
            try {
                walk_animation.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        walk = new Animation(walk_animation,66);

        ArrayList<BufferedImage> walkUp_animation = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "src/Princess/WalkUp/WalkUp" + i + ".png";
            try {
                walkUp_animation.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        walkUp = new Animation(walkUp_animation,66);

        ArrayList<BufferedImage> walkDown_animation = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String filename = "src/Princess/WalkDown/WalkDown" + i + ".png";
            try {
                walkDown_animation.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        walkDown = new Animation(walkDown_animation,66);

        ArrayList<BufferedImage> attack_animation = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            String filename = "src/Princess/Attack/Attack" + i + ".png";
            try {
                attack_animation.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        attack = new Animation(attack_animation,66);

        ArrayList<BufferedImage> attackUp_animation = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            String filename = "src/Princess/AttackUp/AttackUp" + i + ".png";
            try {
                attackUp_animation.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        attackUp = new Animation(attackUp_animation,66);

        ArrayList<BufferedImage> attackDown_animation = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            String filename = "src/Princess/AttackDown/AttackDown" + i + ".png";
            try {
                attackDown_animation.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        attackDown = new Animation(attackDown_animation,66);



    }

    //This function is changed from the previous version to let the player turn left and right
    //This version of the function, when combined with getWidth() and getHeight()
    //Allow the player to turn without needing separate images for left and right
    public int getxCoord() {
        if (facingRight) {
            return (int) xCoord;
        } else {
            return (int) (xCoord + (getPlayerImage().getWidth()));
        }
    }

    public int getyCoord() {
        return (int) yCoord;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void faceRight() {
        facingRight = true;
    }

    public void faceLeft() {
        facingRight = false;
    }

    public void moveRight() {
        if (xCoord + MOVE_AMT <= 920) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void moveUp() {
        if (yCoord - MOVE_AMT >= 0) {
            yCoord -= MOVE_AMT;
        }
    }

    public void moveDown() {
        if (yCoord + MOVE_AMT <= 435) {
            yCoord += MOVE_AMT;
        }
    }

    public void turn() {
        if (facingRight) {
            faceLeft();
        } else {
            faceRight();
        }
    }

    public void collectCoin() {
        score++;
    }

    public BufferedImage getPlayerImage() {
        return walk.getActiveFrame();
    }

    //These functions are newly added to let the player turn left and right
    //These functions when combined with the updated getxCoord()
    //Allow the player to turn without needing separate images for left and right
    public int getHeight() {
        return getPlayerImage().getHeight();
    }

    public int getWidth() {
        if (facingRight) {
            return getPlayerImage().getWidth();
        } else {
            return getPlayerImage().getWidth() * -1;
        }
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
