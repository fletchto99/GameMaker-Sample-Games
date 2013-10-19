package me.matt.mousefx.effect.tail.particle;

import java.awt.Color;
import java.awt.Graphics;

import me.matt.mousefx.entity.Entity;

public class TailParticle extends Entity {

	private Color color;

	public TailParticle(int x, int y, Color color) {
		super(x, y);
		this.color = color;
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(color);
		graphics.drawLine(x, y, x, y);
	}

	@Override
	public void update() {
		if (++lifespan >= 120) {
			destroy();
			return;
		}

		color = new Color(color.getRed(), color.getGreen(), color.getBlue(),
				(int) (255 - (255 * (lifespan / 120D))));
	}

}
