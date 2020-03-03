package controller;

import model.board.BoardFactory;
import model.board.GameBoard;
import model.particles.Particle;
import model.particles.PlayerParticle;

import java.awt.Point;
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
        board = BoardFactory.newEmptyBoard(width, height);
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
            case 38: //UP
                if (loc.y > 0) {
                    player.moveTowardsLocation(new Point(loc.x, loc.y - 1));
                }
                break;
            case 40: // DOWN
                if (loc.y < height - 1) {
                    player.moveTowardsLocation(new Point(loc.x, loc.y + 1));
                }
                break;
            case 37: //LEFT
                if (loc.x > 0) {
                    player.moveTowardsLocation(new Point(loc.x - 1, loc.y));
                }
                break;
            case 39: // RIGHT
                if (loc.x < width - 1) {
                    player.moveTowardsLocation(new Point(loc.x + 1, loc.y));
                }
                break;
            default:
        }
        board.putParticle(player);
        return !player.getLocation().equals(loc);
    }

    public boolean playerClick(Point point) {
        Point loc = player.getLocation();
        board.deleteParticle(loc.x, loc.y);
        player.moveTowardsLocation(point);
        board.putParticle(player);
        return !player.getLocation().equals(loc);
    }

    public Point getPlayerLocation() {
        return player.getLocation();
    }
}
