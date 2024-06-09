import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Item {
    private int x, y;
    private BufferedImage image;
    private String imagePath;

    public Item(String imagePath, int x, int y) {
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public boolean contains(Point point) {
        Rectangle rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
        return rect.contains(point);
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getImagePath() {
        return imagePath;
    }
}
