package me.matt.mousefx.effect.tail;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import me.matt.mousefx.effect.MouseEffect;
import me.matt.mousefx.effect.tail.particle.TailParticle;
import me.matt.mousefx.entity.Entity;

public class MouseTail extends MouseEffect {

	List<Entity> entities = new ArrayList<Entity>();

	@Override
	public void render(Graphics graphics) {
		update();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity != null) {
				entity.render(graphics);
			}
		}
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if ((entity == null) || (!entity.isAlive())) {
				entities.remove(entity);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity != null) {
				entity.update();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		entities.add(new TailParticle(event.getX(), event.getY(), Color.GREEN));
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		entities.add(new TailParticle(event.getX(), event.getY(), Color.RED));
	}

}
