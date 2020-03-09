package view;

import java.awt.*;
import java.util.List;

public class AttackAnimation {
    private int value = 255;
    private final Point origin;
    private final List<Point> attack;

    public AttackAnimation(Point origin, List<Point> attack) {
        this.origin = origin;
        this.attack = attack;
    }

    public boolean paintAttack(Graphics2D g, double scaleX, double scaleY) {
        g.setColor(new Color(255, 0, 0, value));
        for (Point a : attack) {
            g.drawLine(
                    (int)(origin.x * scaleX + scaleX/2),
                    (int)(origin.y * scaleY + scaleY/2),
                    (int)(a.x * scaleX + scaleX/2),
                    (int)(a.y * scaleY + scaleY/2)
            );
        }
        value -= 5;
        return value < 0;
    }
}
