package controller;

import model.board.BoardFactory;
import model.board.GameBoard;
import model.particles.BlackHole;
import model.particles.Particle;
import model.particles.PlayerParticle;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class Game {

    private GameBoard board;
    private Particle player;
    public static final Random RNG = new Random();
    private final int width;
    private final int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
//        board = BoardFactory.newSingleAttackParticleBoard(width, height);
//        board = BoardFactory.newMultipleAttackParticleBoard(width, height, 20);
        board = BoardFactory.newMultipleParticleBoard(width, height, 20);
        player = new PlayerParticle();
        player.setLocation(new Point(RNG.nextInt(board.getWidth()), RNG.nextInt(board.getHeight())));
        board.putParticle(player);
    }

    public int[][] getParticleTypeArray() {
        return board.toTypeArray();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean handleKeyCode(int keyCode) {
        Point loc = player.getLocation();
        board.deleteParticle(loc.x, loc.y);
        switch(keyCode) {
            case 32:
                attack(loc);
                board.putParticle(player);
                return true;
            case 104: //UP
                if (loc.y > 0) {
                    player.moveTowardsLocation(new Point(loc.x, loc.y - 1));
                }
                break;
            case 98: // DOWN
                if (loc.y < height - 1) {
                    player.moveTowardsLocation(new Point(loc.x, loc.y + 1));
                }
                break;
            case 100: //LEFT
                if (loc.x > 0) {
                    player.moveTowardsLocation(new Point(loc.x - 1, loc.y));
                }
                break;
            case 102: // RIGHT
                if (loc.x < width - 1) {
                    player.moveTowardsLocation(new Point(loc.x + 1, loc.y));
                }
                break;
            case 103: // UP-LEFT
                if (loc.y > 0 && loc.x > 0) {
                    player.moveTowardsLocation(new Point(loc.x - 1, loc.y - 1));
                }
                break;
            case 105: // UP-RIGHT
                if (loc.y > 0 && loc.x < width - 1) {
                    player.moveTowardsLocation(new Point(loc.x + 1, loc.y - 1));
                }
                break;
            case 97: // DOWN-LEFT
                if (loc.y < height - 1 && loc.x > 0) {
                    player.moveTowardsLocation(new Point(loc.x - 1, loc.y + 1));
                }
                break;
            case 99: // DOWN-RIGHT
                if (loc.y < height - 1 && loc.x < width - 1) {
                    player.moveTowardsLocation(new Point(loc.x + 1, loc.y + 1));
                }
                break;
            case 101: // Stay still
                board.putParticle(player);
                return true;
            default:
        }
        board.putParticle(player);
        return !player.getLocation().equals(loc);
    }

    public void attack(Point location) {
        for (int y = location.y - 1 ; y <= location.y + 1 ; y++) {
            for (int x = location.x - 1 ; x <= location.x + 1 ; x++) {
                if (!(x == location.x && y == location.y)) {
                    board.deleteParticle(x, y);
                }
            }
        }
    }

    public boolean playerClick(Point point) {
        Point loc = player.getLocation();
        if (loc.equals(point)) {
            return true;
        }
        board.deleteParticle(loc.x, loc.y);
        player.moveTowardsLocation(point);
        board.putParticle(player);
        return !player.getLocation().equals(loc);
    }

    public Point getPlayerLocation() {
        return player.getLocation();
    }

    public List<Particle> getAllParticles() {
        return board.getAllParticles();
    }

    public void moveParticle(Point start) {
        Particle p = board.deleteParticle(start.x, start.y);
        board.putParticle(p);
    }

    public void deleteParticle(Particle p) {
        board.deleteParticle(p.getLocation().x, p.getLocation().y);
    }

    public void putParticle(Particle p) {
        board.putParticle(p);
    }

    public Particle getParticle(int x, int y) {
        return board.getParticle(x, y);
    }
}
