package me.matt.games;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;

import me.matt.gamemaker.GameSettings;
import me.matt.gamemaker.game.Game;

public class FlamingPong extends Game {

    private boolean multiplayer = false;
    private Rectangle multiplayer_button = new Rectangle(350, 175, 100, 50);

    static class Ball {
        public static Float ball = new Ellipse2D.Float(400, 200, 16, 16);
        public static boolean down = false;
        public static boolean right = false;
        public static boolean rightWin = false;
        public static boolean leftWin = false;
    }

    static class Player1 {
        public static int increment = 0;
        public static Rectangle paddle = new Rectangle(20, 180, 5, 40);
        public static boolean down = false;
        public static boolean up = false;
    }

    static class Player2 {
        public static int increment = 0;
        public static Rectangle paddle = new Rectangle(780, 180, 5, 40);
        public static boolean down = false;
        public static boolean up = false;
    }

    private final static class Tail {
        private final int SIZE = 100;
        private final double ALPHA_STEP = (255.0 / SIZE);
        private final Point[] points;
        private int index;

        public Tail() {
            points = new Point[SIZE];
            index = 0;
        }

        public void add(final Point p) {
            points[index++] = p;
            index %= SIZE;
        }

        public void draw(final Graphics g) {
            double alpha = 0;
            for (int i = index; i != (index == 0 ? SIZE - 1 : index - 1); i = (i + 1)
                    % SIZE) {
                if (points[i] != null && points[(i + 1) % SIZE] != null) {
                    g.setColor(new Color(255, 69, 0, (int) alpha));

                    g.fillOval(points[i].x - ((int) alpha / 20) / 2,
                            points[i].y - ((int) alpha / 20) / 2,
                            (int) alpha / 20, (int) alpha / 20);

                    alpha += ALPHA_STEP;
                }
            }
        }
    }

    Tail t = new Tail();

    @Override
    public void onRepaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        if (!multiplayer) {
            g.draw(multiplayer_button);
            g.drawString("Multiplayer", 375, 205);
        } else if (g.getClip().contains(Ball.ball.getBounds2D())) {
            {// player 1
                int temp = Player1.paddle.y + Player1.increment;
                if (temp > 0 && temp < 361) {
                    Player1.paddle.y += Player1.increment;
                }
                g.fill(Player1.paddle);
            }
            {// player 2
                int temp = Player2.paddle.y + Player2.increment;
                if (temp > 0 && temp < 361) {
                    Player2.paddle.y += Player2.increment;
                }
                g.fill(Player2.paddle);
            }
            {// ball
                if (Ball.ball.getY() < 385 && Ball.down) {
                    Ball.ball.y += 1;
                } else if (Ball.ball.y > 0 && !Ball.down) {
                    Ball.ball.y -= 1;
                } else {
                    Ball.down = !Ball.down;
                }

                if ((Ball.ball.x < 764 && Ball.right) || Ball.leftWin) {
                    Ball.ball.x += 2;
                } else if ((Ball.ball.x > 20 && !Ball.right) || Ball.rightWin) {
                    Ball.ball.x -= 2;
                } else {
                    if (Ball.ball.x >= 764
                            && (Ball.ball.y + 8 < Player2.paddle.y || Ball.ball.y + 8 > Player2.paddle.y
                            + Player2.paddle.height)) {
                        Ball.leftWin = true;
                    }
                    if (Ball.ball.x <= 20
                            && (Ball.ball.y + 8 < Player1.paddle.y || Ball.ball.y + 8 > Player1.paddle.y
                            + Player1.paddle.height)) {
                        Ball.rightWin = true;
                    }
                    if (!Ball.leftWin && !Ball.rightWin) {
                        Ball.right = !Ball.right;
                    }
                }
                t.add(new Point((int) Ball.ball.getCenterX(), (int) Ball.ball
                        .getCenterY()));
                t.draw(g);
                g.setColor(Color.WHITE);
                g.fill(Ball.ball);
            }
        } else {
            g.setFont(new Font("Tahoma", 1, 25));
            g.drawString(Ball.leftWin ? "Player 1 wins!" : "Player 2 wins!",
                    325, 190);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && !Player1.up) {
            Player1.increment -= 2;
            Player1.up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S && !Player1.down) {
            Player1.increment += 2;
            Player1.down = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && !Player2.up) {
            Player2.increment -= 2;
            Player2.up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !Player2.down) {
            Player2.increment += 2;
            Player2.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && Player1.up) {
            Player1.increment += 2;
            Player1.up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S && Player1.down) {
            Player1.increment -= 2;
            Player1.down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && Player2.up) {
            Player2.increment += 2;
            Player2.up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && Player2.down) {
            Player2.increment -= 2;
            Player2.down = false;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (multiplayer_button.contains(e.getPoint())) {
            multiplayer = true;
        }
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public double getVersion() {
        return 0;
    }

    @Override
    public void onLoad(GameSettings settings) {
        settings.setFullscreenAllowed(true);
        settings.setResizable(false);
        settings.setSize(800, 400);
        settings.setInformation("Player 1 use W and S keys to control paddel, player 2 use Up and Down arrow keys to control paddel.");
        settings.setTickTime(10);
    }

    @Override
    public void onDisable() {
    }
}
