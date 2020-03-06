package model.particles;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class BlackHole extends AbstractParticle {

    @Override
    public Point moveTowardsLocation(Point target) {
        return location;
    }

    @Override
    public int getTypeId() {
        return 3;
    }

    @Override
    public List<Point> getAttack() {
        return new LinkedList<>();
    }
}
