package model.particles;


import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class AttackParticle extends AbstractParticle {

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
        return 2;
    }

    @Override
    public List<Point> getAttack() {
        List<Point> moves = new LinkedList<>();
        for (int y = location.y - 1 ; y <= location.y + 1 ; y++) {
            for (int x = location.x - 1 ; x <= location.x + 1 ; x++) {
                if (x != location.x && y != location.y) {
                    moves.add(new Point(x, y));
                }
            }
        }
        return moves;
    }
}
