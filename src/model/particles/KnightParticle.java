package model.particles;


import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class KnightParticle extends AbstractParticle {

    @Override
    public Point moveTowardsLocation(Point player) {
        List<Point> possibleMoves = getAttack();
        double distance = 10000.0;
        Point bestMove = null;
        for (Point move : possibleMoves) {
            double moveDistance = move.distance(player);
            if (moveDistance < distance) {
                distance = moveDistance;
                bestMove = move;
            }
        }
        return bestMove;
    }

    @Override
    public int getTypeId() {
        return 4;
    }

    @Override
    public List<Point> getAttack() {
        List<Point> moves = new LinkedList<>();
        moves.add(new Point(location.x-1, location.y-2));
        moves.add(new Point(location.x+1, location.y-2));

        moves.add(new Point(location.x-2, location.y-1));
        moves.add(new Point(location.x+2, location.y-1));

        moves.add(new Point(location.x-2, location.y+1));
        moves.add(new Point(location.x+2, location.y+1));

        moves.add(new Point(location.x-1, location.y+2));
        moves.add(new Point(location.x+1, location.y+2));
        return moves;
    }
}
