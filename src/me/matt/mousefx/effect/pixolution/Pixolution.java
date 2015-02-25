package me.matt.mousefx.effect.pixolution;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import me.matt.mousefx.effect.MouseEffect;
import me.matt.mousefx.effect.pixolution.particle.ColorParticle;
import me.matt.mousefx.entity.Entity;

public class Pixolution extends MouseEffect {
    private final List<Entity> entities = new ArrayList<>();

    @Override
    public void mouseDragged(final MouseEvent event) {
        entities.add(new ColorParticle(event.getX(), event.getY()));
    }

    @Override
    public void mouseMoved(final MouseEvent event) {
        for (int i = 0; i < 20; i++) {
            entities.add(new ColorParticle(event.getX(), event.getY()));
        }
    }

    @Override
    public void render(final Graphics graphics) {
        this.update();
        for (int i = 0; i < entities.size(); i++) {
            final Entity entity = entities.get(i);
            if (entity != null) {
                entity.render(graphics);
            }
        }
    }

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            final Entity entity = entities.get(i);
            if ((entity == null) || (!entity.isAlive())) {
                entities.remove(entity);
            }
        }
        for (int i = 0; i < entities.size(); i++) {
            final Entity entity = entities.get(i);

            if (entity != null) {
                entity.update();
            }
        }
    }

}
