package model.board;

import model.particles.Particle;

public class ArrayBoard implements GameBoard {

    private final Particle[][] board;

    protected ArrayBoard(int width, int height) {
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
        board[p.getLocation().y][p.getLocation().x] = p;
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
