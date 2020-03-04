package model.board;

import model.particles.Particle;

import java.util.List;

public interface GameBoard {
    int getWidth();
    int getHeight();
    Particle getParticle(int x , int y);
    Particle deleteParticle(int x, int y);
    void putParticle(Particle p);
    int[][] toTypeArray();
    List<Particle> getAllParticles();
}
