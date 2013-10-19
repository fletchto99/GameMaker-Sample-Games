package me.matt.mousefx.effect.pixolution.particle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import me.matt.mousefx.entity.Entity;

public class ColorParticle extends Entity {

	private static final Random random = new Random();

	private double xx;
	private double yy;
	private double zz;

	private double vx;
	private double vy;
	private double vz;

	private Color color;

	public ColorParticle(int x, int y) {
		super(x, y);
		color = new Color(random.nextInt(255), random.nextInt(255),
				random.nextInt(255), 255);
		xx = x;
		yy = y;
		zz = 2;

		vx = random.nextGaussian() * 0.6;
		vy = random.nextGaussian() * 0.4;
		vz = random.nextFloat() * 0.7 + 4;
	}

	@Override
	public void update() {
		if (++lifespan >= 120) {
			destroy();
			return;
		}

		color = new Color(color.getRed(), color.getGreen(), color.getBlue(),
				(int) (255 - (255 * (lifespan / 120D))));

		xx += vx;
		yy += vy;
		zz += vz;

		if (zz < 0) {
			zz = 0;
			vx *= 1.2;
			vy *= 1.2;
			vz *= -1.0;
		}

		vz -= 0.15;

		x = (int) xx;
		y = (int) yy;
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(color);
		graphics.fillRect(x - 1, y, 2, 2);
	}
}