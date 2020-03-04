package model.board;

import model.particles.BlackHole;
import model.particles.Particle;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class ArrayBoard implements GameBoard {

    private final Particle[][] board;

    ArrayBoard(int width, int height) {
        board = new Particle[height][width];
    }

    @Override
    public int getWidth() {
        return board[0].length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public Particle getParticle(int x, int y) {
        return board[y][x];
    }

    @Override
    public Particle deleteParticle(int x, int y) {
        Particle p = board[y][x];
        board[y][x] = null;
        return p;
    }

    @Override
    public void putParticle(Particle p) {
        int x = p.getLocation().x;
        int y = p.getLocation().y;
        if (board[y][x] == null) {
            board[y][x] = p;
        } else {
            board[y][x] = new BlackHole();
            board[y][x].setLocation(new Point(x, y));
        }
    }

    @Override
    public int[][] toTypeArray() {
        int[][] types = new int[getHeight()][getWidth()];
        for (int y = 0 ; y < board.length ; y++) {
            Particle[] row = board[y];
            for (int x = 0 ; x < row.length ; x++) {
                types[y][x] = (row[x] == null) ? 0 : row[x].getTypeId();
            }
        }
        return types;
    }

    @Override
    public List<Particle> getAllParticles() {
        List<Particle> allParticles = new LinkedList<>();
        for (Particle[] row : board) {
            for (Particle particle : row) {
                if (particle != null && particle.getTypeId() != 3) {
                    allParticles.add(particle);
                }
            }
        }
        return allParticles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-".repeat(getWidth() + 2));
        sb.append('\n');
        for (Particle[] row : board) {
            sb.append("|");
            for (Particle p : row) {
                sb.append((p == null) ? ' ' : p.toString());
            }
            sb.append("|\n");
        }
        sb.append("-".repeat(getWidth() + 2));
        return sb.toString();
    }
}
