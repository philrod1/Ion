package model.board;

import model.particles.Particle;

public interface GameBoard {
    int getWidth();
    int getHeight();
    Particle getParticle(int x , int y);
    Particle deleteParticle(int x, int y);
    void putParticle(Particle p);
    int[][] toTypeArray();
}
