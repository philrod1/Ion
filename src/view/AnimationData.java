package view;

import java.awt.*;

public class AnimationData {

    private final int type;
    private final Point start, stop;

    protected AnimationData(int type, Point start, Point stop) {
        this.type = type;
        this.start = start;
        this.stop = stop;
    }

    public int getType() {
        return type;
    }

    protected int[] getData(double scaleX, double scaleY, double step, int pulseFrame, int animationFrame) {
        double distanceX = (stop.x - start.x) * scaleX;
        double distanceY = (stop.y - start.y) * scaleY;
        double pulse     = Math.sin(step * pulseFrame);
        double anim      = Math.sin(step * animationFrame * 4);
        double pulseX    = scaleX * 0.8 + pulse * (scaleX * 0.2);
        double positionX = distanceX * anim + start.x * scaleX;
        double pulseY    = scaleY * 0.8 + pulse * (scaleY * 0.2);
        double positionY = distanceY * anim + start.y * scaleY;

        int x = (int) (((positionX + 2) - (pulseX / 2)) + (scaleX / 2));
        int y = (int) (((positionY + 2) - (pulseY / 2)) + (scaleY / 2));
        int w = (int) pulseX - 2;
        int h = (int) pulseY - 2;

        return new int[] {x, y, w, h};
    }

    protected Point getStop() {
        return stop;
    }
}
