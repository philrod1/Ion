package model.particles;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class PlayerParticle extends AbstractParticle {

    @Override
    public Point moveTowardsLocation(Point location) {
        int dx = Math.abs(location.x - this.location.x);
        int dy = Math.abs(location.y - this.location.y);
        if ((dx == 1 && dy == 0)
         || (dx == 1 && dy == 1)
         || (dx == 0 && dy == 1)) {
            this.location = location;
        }
        return this.location;
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public String toString() {
        return "@";
    }

    @Override
    public List<Point> getAttack() {
        return new LinkedList<>();
    }
}
