package reaction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;

import me.matt.gamemaker.GameSettings;
import me.matt.gamemaker.game.Game;

public class ReactionTimer extends Game {

	private int test = 0;
	private int state = 0;
	private long start = -1;
	private long end = -1;
	private long[] times = new long[3];
	private long[] avrages = new long[2];
	Rectangle button = new Rectangle(100, 300, 200, 50);
	String buttonText = "Start visual test 1";
	Color color = Color.BLACK;

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoad(GameSettings s) {
		s.setTickTime(1);// refresh every milisecond to get accurate to the
							// milisecond
		s.setSize(400, 400);
		s.setResizable(false);
	}

	@Override
	public void onRepaint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setColor(Color.WHITE);
		g.draw(button);
		g.drawString(buttonText, (int) (200 - (g.getFontMetrics()
				.getStringBounds(buttonText, g).getWidth() / 2)), 335);
		g.setColor(color);
		g.fillRect(50, 50, 300, 200);
		g.setColor(Color.WHITE);
		g.drawRect(50, 50, 300, 200);
		if (state == 0) {
			drawString(
					g,
					"This is a reaction tester. The first 3 tests will be \nused to calculate your visual reaction time. The \nlast 3 tests will be used to calculate your audio \nreaction time. When finished click calculate \nresults to see your reaction times. The visual test \nwill change this box's color to Red. The audio \ntest will make a beep. They will occur randomly \nbetween 1.5 to 3.5 seconds after clicking start test. \nClick the button below to begin.",
					70, 70);
		}
	}

	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (button.contains(event.getPoint())) {
			actionPerformed();
		}
	}

	public void actionPerformed() {
		switch (state) {
		case -1:
			break;
		case 0:
			state = 1;
		case 1:
			new Thread(new Runnable() {
				@Override
				public void run() {
					buttonText = "React";
					state = -1;
					Timer t = new Timer(random(1500, 3500));
					while (t.isRunning()) {
					}
					state = 2;
					color = Color.RED;
					start = System.currentTimeMillis();
				}

			}).start();
			break;
		case 2:
			end = System.currentTimeMillis();
			color = Color.BLACK;
			times[test] = end - start;
			test++;
			buttonText = "Start visual test " + (test + 1);
			state = 1;
			if (test == 3) {
				avrages[0] = ((times[0] + times[1] + times[2]) / 3);
				state = 3;
				test = 0;
				times = new long[3];
				buttonText = "Start audio test " + (test + 1);
			}
			break;
		case 3:
			new Thread(new Runnable() {

				@Override
				public void run() {
					state = -1;
					Timer t = new Timer(random(1500, 3500));
					buttonText = "React";
					while (t.isRunning()) {
					}
					Toolkit.getDefaultToolkit().beep();
					state = 4;
					start = System.currentTimeMillis();
				}

			}).start();

			break;
		case 4:
			end = System.currentTimeMillis();
			times[test] = end - start;
			test++;
			buttonText = "Start audio test " + (test + 1);
			state = 3;
			if (test == 3) {
				state = 5;
				buttonText = "Calculate Results.";
			}
			break;
		case 5:
			avrages[1] = ((times[0] + times[1] + times[2]) / 3);
			JOptionPane.showMessageDialog(null,
					"Your avrage reaction time for visual is " + avrages[0]
							+ "ms and Your avrage reaction time for audio is "
							+ avrages[1] + "ms");
			break;
		}

	}

	public static int random(final int min, final int max) {
		final int n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : new Random().nextInt(n));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getVersion() {
		return 1.0;
	}

}
