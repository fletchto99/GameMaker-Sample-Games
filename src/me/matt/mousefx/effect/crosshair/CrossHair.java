package me.matt.mousefx.effect.crosshair;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import me.matt.mousefx.effect.MouseEffect;

public class CrossHair extends MouseEffect {

    private Point point;

    @Override
    public void mouseDragged(final MouseEvent event) {
        point = event.getPoint();

    }

    @Override
    public void mouseMoved(final MouseEvent event) {
        point = event.getPoint();
    }

    @Override
    public void render(final Graphics graphics) {
        final Rectangle bounds = graphics.getClipBounds();
        graphics.setColor(Color.WHITE);
        if (point != null) {
            graphics.drawLine((int) point.getX(), 0, (int) point.getX(),
                    bounds.height);
            graphics.drawLine(0, (int) point.getY(), bounds.width,
                    (int) point.getY());
        }
    }

}
