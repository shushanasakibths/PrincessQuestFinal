import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    private ArrayList<BufferedImage> frames;
    private int currentFrame;
    private long lastTime, timer;
    private int delay;

    public Animation(ArrayList<BufferedImage> frames, int delay) {
        this.frames = frames;
        this.delay = delay;
        currentFrame = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void update() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > delay) {
            currentFrame++;
            timer = 0;
            if (currentFrame >= frames.size()) {
                currentFrame = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames.get(currentFrame);
    }

    public void reset() {
        currentFrame = 0;
    }

    public boolean isComplete() {
        return currentFrame == frames.size() - 1;
    }
}