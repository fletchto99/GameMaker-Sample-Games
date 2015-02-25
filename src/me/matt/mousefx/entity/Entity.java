package me.matt.mousefx.entity;

import java.awt.Graphics;

public abstract class Entity {

    private boolean alive = true;

    protected int x;
    protected int y;

    protected int lifespan = 0;

    protected Entity(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    protected void destroy() {
        alive = false;
    }

    public int getLifespan() {
        return lifespan;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return alive;
    }

    public abstract void render(Graphics graphics);

    public abstract void update();

}