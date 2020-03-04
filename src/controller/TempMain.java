package controller;

import model.particles.Particle;
import view.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
                        for (Particle p : game.getAllParticles()) {
                            Point start = p.getLocation();
                            Point stop = p.moveTowardsLocation(next);
                            gp.animateMove(start, stop);
                            p.setLocation(stop);
                            game.moveParticle(start);
                        }
                    }
                }
            });
            gp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Point prev = game.getPlayerLocation();
                    Point next = gp.getGridCoords(e.getPoint());
                    boolean result = game.playerClick(next);
                    if (result) {
                        gp.animateMove(prev, next);
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
