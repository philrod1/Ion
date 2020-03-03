package model.particles;

import java.awt.*;

public abstract class AbstractParticle implements Particle {

    protected Point location;
    private boolean alive;

    @Override
    public Point getLocation() {
        return new Point(location.x, location.y);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public void setDead() {
        alive = false;
    }
}
