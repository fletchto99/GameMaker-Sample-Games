package me.matt.mousefx.effect;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public abstract class MouseEffect {

    public abstract void mouseDragged(MouseEvent event);

    public abstract void mouseMoved(MouseEvent event);

    public abstract void render(Graphics graphics);

}
