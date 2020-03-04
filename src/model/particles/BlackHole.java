package model.particles;

import java.awt.Point;

public class BlackHole extends AbstractParticle {

    @Override
    public Point moveTowardsLocation(Point target) {
        return location;
    }

    @Override
    public int getTypeId() {
        return 3;
    }
}
