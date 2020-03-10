package model.board;

import model.particles.AttackParticle;
import model.particles.KnightParticle;
import model.particles.Particle;

import java.awt.Point;
import java.util.Random;

public class BoardFactory {

    private final static Random RNG = new Random();

    public static GameBoard newEmptyBoard(int width, int height) {
        return new ArrayBoard(width, height);
    }

    public static GameBoard newSingleAttackParticleBoard(int width, int height) {
        GameBoard board = new ArrayBoard(width, height);
        Particle ap = new AttackParticle();
        ap.setLocation(new Point(RNG.nextInt(width), RNG.nextInt(height)));
        board.putParticle(ap);
        return board;
    }

    public static GameBoard newMultipleAttackParticleBoard(int width, int height, int nParticles) {
        GameBoard board = new ArrayBoard(width, height);
        for (int i = 0 ; i < nParticles ; i++) {
            Particle ap = new AttackParticle();
            ap.setLocation(new Point(RNG.nextInt(width), RNG.nextInt(height)));
            board.putParticle(ap);
        }
        return board;
    }

    public static GameBoard newMultipleParticleBoard(int width, int height, int nParticles) {
        GameBoard board = new ArrayBoard(width, height);
        for (int i = 0 ; i < nParticles ; i++) {
            Particle ap = (Math.random() > 0.5) ? new AttackParticle() : new KnightParticle();
            ap.setLocation(new Point(RNG.nextInt(width), RNG.nextInt(height)));
            board.putParticle(ap);
        }
        return board;
    }
}
