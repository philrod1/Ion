package model.particles;

import java.awt.Point;
import java.util.List;

public interface Particle {
    Point getLocation();
    Point moveTowardsLocation(Point location);
    void setLocation(Point location);
    boolean isAlive();
    void setDead();
    int getTypeId();

    List<Point> getAttack();
}
