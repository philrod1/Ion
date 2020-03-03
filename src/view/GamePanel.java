package view;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int FRAMES = 128;

    private static Color[] COLOURS = new Color[] {
            new Color(0,0,0,0),
            Color.RED,
            Color.BLUE,
            Color.GREEN
    };

    private final static double step = Math.PI / FRAMES;

    private int pulseFrame = 0;
    private int animationFrame = 0;

    private int[][] board;
    private final int w, h;
    private int animating;
    private Point animationStart, animationStop;

    public GamePanel (int[][] board) {
        setLayout(null);
        this.board = board;
        w = board[0].length;
        h = board.length;
        System.out.println(("Width = " + w + ", height = " + h));
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawParticles(g);
        if (animating > 0) {
            drawAnimation(g);
        }
        g.dispose();
        pulseFrame++;
        pulseFrame %= FRAMES;
    }

    private void drawAnimation(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        double sx = getWidth() / (double) w;
        double sy = getHeight() / (double) h;
        double distX = (animationStop.x - animationStart.x) * sx;
        double distY = (animationStop.y - animationStart.y) * sy;
        double px = sx * 0.8 + Math.sin(step * pulseFrame) * (sx * 0.2);
        double py = sy * 0.8 + Math.sin(step * pulseFrame) * (sx * 0.2);
        double posX = distX * Math.sin(step * animationFrame * 4) + animationStart.x * sx;
        double posY = distY * Math.sin(step * animationFrame * 4) + animationStart.y * sy;
        g2.setColor(COLOURS[animating]);
        g2.fillOval((int)(((posX + 2) - px/2) + sx/2), (int)(((posY + 1)-py/2) + sy/2), (int)px-2, (int)py-1);
        animationFrame++;
        if (animationFrame == FRAMES/8) {
            animationFrame = 0;
            board[animationStop.y][animationStop.x] = animating;
            animating = 0;
        }
    }

    private void drawParticles(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        double sx = getWidth() / (double) w;
        double sy = getHeight() / (double) h;
        double px = sx * 0.8 + Math.sin(step * pulseFrame) * (sx * 0.2);
        double py = sy * 0.8 + Math.sin(step * pulseFrame) * (sy * 0.2);
        for (int y = 0 ; y < h ; y++) {
            for (int x = 0 ; x < w ; x++) {
                int type = board[y][x];
                if (type > 0) {
                    g2.setColor(COLOURS[type]);
                    g2.fillOval((int)(((x * sx + 2) - px/2) + sx/2), (int)(((y * sy + 1)-py/2) + sy/2), (int)px-2, (int)py-1);
                }
            }
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.ORANGE);
        double width = getWidth();
        double height = getHeight();
        double sx = width / w;
        double sy = height / h;
        for (int x = 0 ; x <= w ; x++) {
            g.drawLine((int)(x * sx), 0, (int)(x * sx), getHeight());
        }
        for (int y = 0 ; y <= h ; y++) {
            g.drawLine(0, (int)(sy * y), getWidth(), (int)(sy * y));
        }
    }

    public void updateBoard(int[][] board) {
        repaint();
        if (animating > 0) {
            return;
        }
        this.board = board;
    }

    public Point getGridCoords(Point point) {
        int sx = getWidth() / w;
        int sy = getHeight() / h;
        return new Point(point.x / sx, point.y / sy);
    }

    public void animateMove(Point prev, Point next) {
        animating = board[prev.y][prev.x];
        if (animating > 0) {
            board[prev.y][prev.x] = 0;
            animationStart = prev;
            animationStop = next;
        }
    }
}
