package view;

import javax.swing.JPanel;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GamePanel extends JPanel {

    private static final int FRAMES = 128;

    private static Color[] COLOURS = new Color[] {
            new Color(0,0,0,0),
            Color.RED,
            Color.BLUE,
            Color.BLACK,
            Color.GREEN,
            Color.PINK,
            Color.YELLOW
    };

    private final static double step = Math.PI / FRAMES;

    private int pulseFrame = 0;
    private int animationFrame = 0;

    private int[][] board;
    private final int w, h;

    private final List<AnimationData> animations;
    private final List<AttackAnimation> attacks;
    private Point mouse = new Point(0,0);

    public GamePanel (int[][] board) {
        animations = new LinkedList<>();
        attacks = new LinkedList<>();
        setLayout(null);
        this.board = board;
        w = board[0].length;
        h = board.length;
        System.out.println(("Width = " + w + ", height = " + h));
        setBackground(new Color(20,20,40));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        drawGrid(g2);
        if (!attacks.isEmpty()) {
            drawAttacks(g2);
        }
        drawParticles(g2);
        if (!animations.isEmpty()) {
            drawAnimation(g2);
        }
        drawMouse(g2);
        g.dispose();
        pulseFrame++;
        pulseFrame %= FRAMES;
    }

    private void drawMouse(Graphics2D g) {
        g.setColor(new Color(180,180,100));
        g.setStroke(new BasicStroke(3.0f, 0,0));
        g.drawRect(mouse.x, mouse.y,getWidth()/w,getHeight()/h );
    }

    private void drawAttacks(Graphics2D g) {
        g.setStroke(new BasicStroke(3.0f, 0,0));
        double sx = getWidth() / (double) w;
        double sy = getHeight() / (double) h;
        List<AttackAnimation> finished = new LinkedList<>();
        for (AttackAnimation attack : attacks) {
            if (attack.paintAttack(g, sx, sy)) {
                finished.add(attack);
            }
        }
        for (AttackAnimation attack : finished) {
            attacks.remove(attack);
        }
    }

    private void drawAnimation(Graphics2D g) {
        double sx = getWidth() / (double) w;
        double sy = getHeight() / (double) h;
        for (AnimationData animation : animations) {
            int[] data = animation.getData(sx, sy, step, pulseFrame, animationFrame);
            g.setColor(COLOURS[animation.getType()]);
            g.fillOval(data[0], data[1], data[2], data[3]);
        }
        animationFrame++;
        if (animationFrame == FRAMES/8) {
            animationFrame = 0;
            for (AnimationData animation : animations) {
                board[animation.getStop().y][animation.getStop().x] = animation.getType();
            }
            animations.clear();
        }
    }

    private void drawParticles(Graphics2D g) {
        double sx = getWidth() / (double) w;
        double sy = getHeight() / (double) h;
        for (int y = 0 ; y < h ; y++) {
            for (int x = 0 ; x < w ; x++) {
                int type = board[y][x];
                if (type > 0) {
                    int pf = (pulseFrame + 21 * type) % FRAMES;
                    double px = sx * 0.8 + Math.sin(step * pf) * (sx * 0.2);
                    double py = sy * 0.8 + Math.sin(step * pf) * (sy * 0.2);
                    g.setColor(COLOURS[type]);
                    g.fillOval((int)(((x * sx + 2) - px/2) + sx/2), (int)(((y * sy + 1)-py/2) + sy/2), (int)px-2, (int)py-1);
                }
            }
        }
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(new Color(130,130,50));
        double width = getWidth();
        double height = getHeight();
        double sx = width / w;
        double sy = height / h;
        for (int x = 1 ; x <= w ; x++) {
            g.drawLine((int)(x * sx), 0, (int)(x * sx), getHeight());
        }
        for (int y = 1 ; y <= h ; y++) {
            g.drawLine(0, (int)(sy * y), getWidth(), (int)(sy * y));
        }
    }

    public void updateBoard(int[][] board) {
        repaint();
        if (!animations.isEmpty()) {
            return;
        }
        this.board = board;
    }

    public Point getGridCoords(Point point) {
        double width = getWidth();
        double height = getHeight();
        double sx = width / w;
        double sy = height / h;
        return new Point((int)(point.x / sx), (int)(point.y / sy));
    }

    public void animateMove(Point prev, Point next) {
        int type = board[prev.y][prev.x];
        board[prev.y][prev.x] = 0;
        animations.add(new AnimationData(type, prev, next));
    }

    public void showAttack(Point location, List<Point> attack) {
        attacks.add(new AttackAnimation(location, attack));
    }

    public void setMouse(Point point) {
        double width = getWidth();
        double height = getHeight();
        double sx = width / w;
        double sy = height / h;
        Point coords = getGridCoords(point);
        mouse = new Point((int)(coords.x * sx), (int)(coords.y * sy));
    }
}
