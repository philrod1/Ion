package controller;

import model.particles.Particle;
import view.GamePanel;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TempMain {


    public static void main(String[] args) {
        final int scale = 50;
        final int width = 15;
        final int height = 15;
        Game game = new Game(width, height);
        GamePanel gp = new GamePanel(game.getParticleTypeArray());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ion");
            frame.getContentPane().add(gp);
            frame.setBounds(1, 1, game.getWidth() * scale, game.getHeight() * scale + 50);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    Point prev = game.getPlayerLocation();
                    boolean result = game.handleKeyCode(e.getKeyCode());
                    Point next = game.getPlayerLocation();
                    if (result) {
                        gp.animateMove(prev, next);
                        List<Particle> particles = game.getAllParticles();
                        for (Particle p : particles) {
                            game.deleteParticle(p);
                        }
                        for (Particle p : particles) {
                            if (p.getTypeId() != 3) {
                                Point start = p.getLocation();
                                Point stop = p.moveTowardsLocation(next);
                                gp.animateMove(start, stop);
                                p.setLocation(stop);
                                game.putParticle(p);
                            }
                        }
                    }
                }
            });
            gp.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getButton() == 1) {
                        Point prev = game.getPlayerLocation();
                        Point next = gp.getGridCoords(e.getPoint());
                        boolean result = game.playerClick(next);
                        if (result) {
                            gp.animateMove(prev, next);
                            List<Particle> particles = game.getAllParticles();
                            for (Particle p : particles) {
                                game.deleteParticle(p);
                            }
                            for (Particle p : particles) {
                                if (p.getTypeId() != 3) {
                                    Point start = p.getLocation();
                                    Point stop = p.moveTowardsLocation(next);
                                    gp.animateMove(start, stop);
                                    p.setLocation(stop);
                                    game.putParticle(p);
                                }
                            }
                        }
                    }
                    if (e.getButton() == 3) {
                        game.attack(game.getPlayerLocation());
                    }
                }
            });
            frame.setResizable(true);
            frame.setVisible(true);
        });

        // Repaint the view at 60 FPS
        final Runnable task = () -> gp.updateBoard(game.getParticleTypeArray());
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(task, 0, (int)(1000 / 60.0), TimeUnit.MILLISECONDS);

    }
}
