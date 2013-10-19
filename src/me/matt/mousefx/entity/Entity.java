package me.matt.mousefx.entity;

import java.awt.Graphics;

public abstract class Entity {

	private boolean alive = true;

	protected int x;
	protected int y;

	protected int lifespan = 0;

	protected Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	protected void destroy() {
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getLifespan() {
		return lifespan;
	}

	public abstract void render(Graphics graphics);

	public abstract void update();

}