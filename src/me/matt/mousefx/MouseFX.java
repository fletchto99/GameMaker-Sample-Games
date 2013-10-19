package me.matt.mousefx;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import me.matt.gamemaker.GameSettings;
import me.matt.gamemaker.game.Game;
import me.matt.mousefx.effect.MouseEffect;
import me.matt.mousefx.effect.crosshair.CrossHair;
import me.matt.mousefx.effect.pixolution.Pixolution;
import me.matt.mousefx.effect.tail.MouseTail;

public class MouseFX extends Game {

	private MouseEffect effect = null;

	public void onDisable() {
	}

	public void onLoad(GameSettings settings) {
		settings.setTickTime(10);
		settings.setResizable(false);
		settings.setSize(800, 600);
	}

	@Override
	public void onRepaint(Graphics graphics) {
		if (effect != null) {
			effect.render(graphics);
		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if (effect != null) {
			effect.mouseDragged(event);
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (effect != null) {
			effect.mouseMoved(event);
		}
	}

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_R) {
			effect = null;
		} else if (event.getKeyCode() == KeyEvent.VK_1) {
			effect = new Pixolution();
		} else if (event.getKeyCode() == KeyEvent.VK_2) {
			effect = new MouseTail();
		} else if (event.getKeyCode() == KeyEvent.VK_3) {
			effect = new CrossHair();
		}
	}

	@Override
	public String getName() {
		return "MouseFX";
	}

	@Override
	public double getVersion() {
		return 1.0;
	}

}
