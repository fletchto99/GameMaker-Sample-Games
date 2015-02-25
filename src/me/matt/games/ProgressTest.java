package me.matt.games;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import me.matt.gamemaker.GameSettings;
import me.matt.gamemaker.game.Game;

public class ProgressTest extends Game {

    public void drawProgressBar(final Graphics2D g, final int x, final int y,
            final int width, final int height, final Color main,
            final Color progress, final int alpha, final int percentage) {
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        final GradientPaint base = new GradientPaint(x, y, new Color(200, 200,
                200, alpha), x, y + height, main);
        final GradientPaint overlay = new GradientPaint(x, y, new Color(200,
                200, 200, alpha), x, y + height, progress);
        if (height > width) {
            g.setPaint(base);
            g.fillRect(x, y, width, height);
            g.setPaint(overlay);
            g.fillRect(x,
                    y + (height - (int) (height * (percentage / 100.0D))),
                    width, (int) (height * (percentage / 100.0D)));
        } else {
            g.setPaint(base);
            g.fillRect(x, y, width, height);
            g.setPaint(overlay);
            g.fillRect(x, y, (int) (width * (percentage / 100.0D)), height);
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onLoad(final GameSettings arg0) {
    }

    @Override
    public void onRepaint(final Graphics graphics) {
        this.drawProgressBar((Graphics2D) graphics, 0, 0, 200, 25, Color.RED,
                Color.GREEN, 255, 80);

        this.drawProgressBar((Graphics2D) graphics, 0, 30, 200, 25, Color.GRAY,
                Color.BLUE, 255, 60);

        this.drawProgressBar((Graphics2D) graphics, 0, 60, 200, 25,
                Color.ORANGE, Color.CYAN, 255, 20);

        this.drawProgressBar((Graphics2D) graphics, 210, 0, 25, 200, Color.RED,
                Color.GREEN, 255, 80);

        this.drawProgressBar((Graphics2D) graphics, 240, 0, 25, 200,
                Color.GRAY, Color.BLUE, 255, 40);

        this.drawProgressBar((Graphics2D) graphics, 270, 0, 25, 200,
                Color.ORANGE, Color.CYAN, 255, 50);
    }

}
