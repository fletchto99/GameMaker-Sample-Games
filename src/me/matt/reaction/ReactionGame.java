package me.matt.reaction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;

import me.matt.gamemaker.GameSettings;
import me.matt.gamemaker.game.Game;

public class ReactionGame extends Game {

    public static int random(final int min, final int max) {
        final int n = Math.abs(max - min);
        return Math.min(min, max) + (n == 0 ? 0 : new Random().nextInt(n));
    }

    private int test = 0;
    private int state = 0;
    private long start = -1;
    private long end = -1;
    private long[] times = new long[3];
    private final long[] averages = new long[2];
    Rectangle button = new Rectangle(100, 300, 200, 50);
    String buttonText = "Start visual test 1";

    Color color = Color.BLACK;

    public void actionPerformed() {
        switch (state) {
            case -1:
                break;
            case 0:
                state = 1;
            case 1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        buttonText = "React";
                        state = -1;
                        final Timer t = new Timer(ReactionGame.random(1500,
                                3500));
                        while (t.isRunning()) {
                        }
                        state = 2;
                        color = Color.RED;
                        start = System.currentTimeMillis();
                    }

                }).start();
                break;
            case 2:
                end = System.currentTimeMillis();
                color = Color.BLACK;
                times[test] = end - start;
                test++;
                buttonText = "Start visual test " + (test + 1);
                state = 1;
                if (test == 3) {
                    averages[0] = ((times[0] + times[1] + times[2]) / 3);
                    state = 3;
                    test = 0;
                    times = new long[3];
                    buttonText = "Start audio test " + (test + 1);
                }
                break;
            case 3:
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        state = -1;
                        final Timer t = new Timer(ReactionGame.random(1500,
                                3500));
                        buttonText = "React";
                        while (t.isRunning()) {
                        }
                        Toolkit.getDefaultToolkit().beep();
                        state = 4;
                        start = System.currentTimeMillis();
                    }

                }).start();

                break;
            case 4:
                end = System.currentTimeMillis();
                times[test] = end - start;
                test++;
                buttonText = "Start audio test " + (test + 1);
                state = 3;
                if (test == 3) {
                    state = 5;
                    buttonText = "Calculate Results.";
                }
                break;
            case 5:
                averages[1] = ((times[0] + times[1] + times[2]) / 3);
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Your average reaction time for visual is "
                                        + averages[0]
                                        + "ms and Your average reaction time for audio is "
                                        + averages[1] + "ms");
                break;
        }

    }

    private void drawString(final Graphics g, final String text, final int x,
            int y) {
        for (final String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public String getName() {
        return "Reaction Game";
    }

    @Override
    public double getVersion() {
        return 1.0;
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        if (button.contains(event.getPoint())) {
            this.actionPerformed();
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onLoad(final GameSettings s) {
        s.setTickTime(1);// refresh every milisecond to get accurate to the
                         // milisecond
        s.setSize(400, 400);
        s.setResizable(false);
    }

    @Override
    public void onRepaint(final Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.draw(button);
        g.drawString(buttonText, (int) (200 - (g.getFontMetrics()
                .getStringBounds(buttonText, g).getWidth() / 2)), 335);
        g.setColor(color);
        g.fillRect(50, 50, 300, 200);
        g.setColor(Color.WHITE);
        g.drawRect(50, 50, 300, 200);
        if (state == 0) {
            this.drawString(
                    g,
                    "This is a reaction tester. The first 3 tests will be \nused to calculate your visual reaction time. The \nlast 3 tests will be used to calculate your audio \nreaction time. When finished click calculate \nresults to see your reaction times. The visual test \nwill change this box's color to Red. The audio \ntest will make a beep. They will occur randomly \nbetween 1.5 to 3.5 seconds after clicking start test. \nClick the button below to begin.",
                    70, 70);
        }
    }

}
