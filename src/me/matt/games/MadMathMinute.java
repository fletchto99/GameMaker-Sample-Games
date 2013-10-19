package me.matt.games;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import me.matt.gamemaker.GameSettings;
import me.matt.gamemaker.game.Game;

public class MadMathMinute extends Game {

	/*
	 * A state which pertains to where the game is currently at
	 * 
	 * 0: The beggining screen asking for a name
	 * 
	 * 1: The main menu
	 * 
	 * 2: Ask the user the questions
	 * 
	 * 3: Game over
	 * 
	 * 4: Top 10 scores for you
	 */
	int state = 0;

	String currentUser = "";
	int lives = 3;
	String answer = "";
	int score = 0;
	String[] question = new String[3];
	Timer t = new Timer(60000);
	ArrayList<Integer> totals = new ArrayList<Integer>();

	@Override
	public void onLoad(GameSettings settings) {
		settings.setFullscreenAllowed(false);
		settings.setSize(500, 500);
		settings.setResizable(false);
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onRepaint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 500, 500);
		g.setFont(new Font("Tahoma", 0, 50));
		g.setColor(Color.RED);
		g.drawString("Mad Math Minute", 75, 50);
		g.setFont(new Font("Tahoma", 0, 14));
		g.setColor(Color.BLACK);
		switch (state) {
		case 0:
			g.setColor(Color.BLACK);
			g.drawString("Name:", 175, 250);
			if (currentUser != null && currentUser.length() == 0) {
				g.setColor(Color.GRAY);
				g.drawString("Username", 220, 250);
				g.setColor(Color.BLACK);
			}
			g.drawString(currentUser, 220, 250);
			g.drawRect(215, 237, 73, 15);
			g.setColor(Color.CYAN.darker());
			g.fillRect(190, 300, 100, 50);
			g.setColor(Color.BLACK);
			g.drawRect(190, 300, 100, 50);
			g.drawString("Start", 227, 330);
			break;
		case 1:
			g.setColor(Color.RED);
			drawString(
					g,
					"Welcome "
							+ currentUser
							+ "! Mad Math Minute is a fun math game that will test\nyour knowledge of arithmetic questions using addition, subtraction,\nmultiplication and division. Press Play to start a game. Press Top 10\nto see your past scores.",
					20, 300);

			// play
			g.setColor(Color.PINK.darker());
			g.fillRect(125, 225, 100, 50);
			g.setColor(Color.BLACK);
			g.drawRect(125, 225, 100, 50);
			g.drawString("Play", 165, 255);

			// exit
			g.setColor(Color.PINK.darker());
			g.fillRect(275, 225, 100, 50);
			g.setColor(Color.BLACK);
			g.drawRect(275, 225, 100, 50);
			g.drawString("Exit", 315, 255);

			// Top 10
			g.setColor(Color.PINK.darker());
			g.fillRect(200, 150, 100, 50);
			g.setColor(Color.BLACK);
			g.drawRect(200, 150, 100, 50);
			g.drawString("Top 10", 230, 180);
			break;
		case 2:
			if (lives == 0) {
				state++;
			}
			if (!t.isRunning()) {
				generateQuestion();
				lives--;
			}
			g.setColor(Color.BLACK);
			g.drawString("Lives Remaining:", 300, 100);
			g.drawString("Time Remaining:", 300, 85);
			g.drawString("Username: " + currentUser, 100, 85);
			g.drawString("Score: " + String.valueOf(score), 100, 100);
			g.setColor(Color.RED);
			g.drawString(String.valueOf(t.getRemaining() / 1000), 410, 85);
			g.setColor(Color.YELLOW.darker());
			int x = 410;
			for (int i = 0; i < lives; i++) {
				drawStar(g, x, 90, 10, 10);
				x += 20;
			}
			g.setColor(Color.BLACK);
			g.drawString("Question:" + question[0] + question[1] + question[2],
					160, 220);
			g.drawString("Answer:", 160, 250);
			if (answer != null && answer.length() == 0) {
				g.setColor(Color.GRAY);
				g.drawString("Answer", 220, 250);
				g.setColor(Color.BLACK);
			}
			g.drawString(answer, 220, 250);
			g.drawRect(215, 237, 73, 15);

			g.setColor(Color.CYAN.darker());
			g.fillRect(190, 300, 100, 50);
			g.setColor(Color.BLACK);
			g.drawRect(190, 300, 100, 50);
			g.drawString("Next", 227, 330);
			break;
		case 3:
			g.setColor(Color.RED);
			g.drawString("Game Over", 100, 120);
			g.setColor(Color.GREEN.darker());
			g.drawString(
					"Your score: " + String.valueOf(score) + "/"
							+ String.valueOf(score + 3), 100, 150);
			g.setColor(Color.CYAN.darker());
			g.fillRect(190, 300, 100, 50);
			g.setColor(Color.BLACK);
			g.drawRect(190, 300, 100, 50);
			g.drawString("Menu", 227, 330);
			break;
		case 4:
			Collections.sort(totals);
			String score = "";
			for (int i = 0; i < (totals.size() > 10 ? 10 : totals.size()); i++) {
				score += (i + 1) + ". " + String.valueOf(totals.get(i)) + "\n";
			}
			g.drawString("Your top scores " + currentUser, 150, 100);
			drawString(g, score, 150, 125);
			g.setColor(Color.CYAN.darker());
			g.fillRect(190, 300, 100, 50);
			g.setColor(Color.BLACK);
			g.drawRect(190, 300, 100, 50);
			g.drawString("Menu", 227, 330);
			break;
		}

	}

	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public void drawStar(Graphics g, int x, int y, int w, int h) {
		int ax = (w / 2) + x;
		int ay = y;
		int bx = w + x;
		int by = (int) (.374 * (double) (h) + (double) (y));
		int cx = (int) (.825 * (double) (w) + (double) (x));
		int cy = h + y;
		int dx = (int) (.175 * (double) (h) + (double) (x));
		int dy = h + y;
		int ex = x;
		int ey = by;
		g.drawLine(ax, ay, cx, cy);
		g.drawLine(bx, by, dx, dy);
		g.drawLine(cx, cy, ex, ey);
		g.drawLine(dx, dy, ax, ay);
		g.drawLine(ex, ey, bx, by);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		case 0:
			Rectangle start = new Rectangle(190, 300, 100, 50);
			if (start.contains(e.getPoint()) && currentUser != null
					&& currentUser.length() > 0) {
				state = 1;// new state
			}
			break;
		case 1:
			Rectangle exit = new Rectangle(275, 225, 100, 50);
			Rectangle play = new Rectangle(125, 225, 100, 50);
			if (exit.contains(e.getPoint())) {
				System.exit(0);
			}
			Rectangle t10 = new Rectangle(200, 150, 100, 50);
			if (t10.contains(e.getPoint())) {
				state = 4;
			}
			if (play.contains(e.getPoint())) {
				state = 2;
				t.reset();
				generateQuestion();
			}
			break;
		case 2:
			Rectangle next = new Rectangle(200, 300, 100, 50);
			if (next.contains(e.getPoint()) && answer != null
					&& answer.length() > 0) {
				checkAnswer();
				generateQuestion();
			}
			break;
		case 3:
			Rectangle menu = new Rectangle(190, 300, 100, 50);
			if (menu.contains(e.getPoint())) {
				totals.add(score);
				reset();
				state = 1;// new state
			}
			break;
		case 4:
			Rectangle main = new Rectangle(190, 300, 100, 50);
			if (main.contains(e.getPoint())) {
				reset();
				state = 1;// new state
			}
			break;
		}
	}

	private void reset() {
		lives = 3;
		score = 0;
		answer = "";
	}

	public static int random(final int min, final int max) {
		Random random = new Random();
		final int n = Math.abs((max + 1) - min);
		return Math.min(min, max) + (n == 0 ? 0 : random.nextInt(n));
	}

	private void checkAnswer() {
		int temp = 0;
		if (question[1].equalsIgnoreCase("*")) {
			temp = Integer.parseInt(question[0])
					* Integer.parseInt(question[2]);
		} else if (question[1].equalsIgnoreCase("/")) {
			temp = Integer.parseInt(question[0])
					/ Integer.parseInt(question[2]);
		} else if (question[1].equalsIgnoreCase("-")) {
			temp = Integer.parseInt(question[0])
					- Integer.parseInt(question[2]);
		} else if (question[1].equalsIgnoreCase("+")) {
			temp = Integer.parseInt(question[0])
					+ Integer.parseInt(question[2]);
		}
		if (answer.equalsIgnoreCase(String.valueOf(temp))) {
			score++;
		} else {
			lives--;
		}
		answer = "";
	}

	String[] primes = new String[] { "-11", "-7", "-5", "-3", "-1", "0", "1",
			"3", "5", "7", "9", "11" };

	private void generateQuestion() {
		t.reset();
		question[0] = String.valueOf(random(-12, 12));
		switch (random(1, 4)) {
		case 1:
			question[1] = "*";
			break;
		case 2:
			question[1] = "/";
			break;
		case 3:
			question[1] = "-";
			break;
		case 4:
			question[1] = "+";
			break;
		}
		if (!question[1].equalsIgnoreCase("/")) {
			question[2] = String.valueOf(random(-12, 12));
		} else {
			while (contains(primes, question[0])) {
				question[0] = String.valueOf(random(-12, 12));
			}
			question[2] = String.valueOf(random(-12, 12));
			while (Integer.parseInt(question[2]) == 0) {
				question[2] = String.valueOf(random(-12, 12));
			}
			while (Integer.parseInt(question[0])
					% Integer.parseInt(question[2]) != 0) {
				question[2] = String.valueOf(random(-12, 12));
				while (Integer.parseInt(question[2]) == 0) {
					question[2] = String.valueOf(random(-12, 12));
				}
			}
		}

	}

	public boolean contains(String[] array, String number) {
		for (String i : array) {
			if (i.equalsIgnoreCase(number)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (state) {// check our current running state
		case 0:
			// here i check if the person pressed backspace
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (currentUser != null && currentUser.length() > 0) {
					// if they press backspace remove the last character of the
					// name
					currentUser = currentUser.substring(0,
							currentUser.length() - 1);
				}
				// else we can type our key
			} else if (!e.isActionKey() && e.getKeyCode() != KeyEvent.VK_SHIFT
					&& e.getKeyCode() != KeyEvent.VK_CONTROL
					&& e.getKeyCode() != KeyEvent.VK_ENTER) {
				// append the current key to the end of the username
				currentUser += e.getKeyChar();
			}
			break;// end our case
		case 2:
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (answer != null && answer.length() > 0) {
					answer = answer.substring(0, answer.length() - 1);
				}
			} else if (!e.isActionKey() && e.getKeyCode() != KeyEvent.VK_SHIFT
					&& e.getKeyCode() != KeyEvent.VK_CONTROL
					&& e.getKeyCode() != KeyEvent.VK_ENTER) {
				answer += e.getKeyChar();
			}
			break;
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}
}

class Timer {

	private long end;
	private final long start;
	private final long period;

	public Timer(final long period) {
		this.period = period;// the duration of the timer
		start = System.currentTimeMillis(); // take the start time (when the
											// timer is made)
		end = start + period; // Find when the timer should end
	}

	public long getRemaining() {
		if (isRunning()) {
			return end - System.currentTimeMillis();// take the ending time and
													// subtract our current time
													// for the time left
		}
		return 0;// no time left, the timer is up
	}

	public boolean isRunning() {
		return System.currentTimeMillis() < end; // were still running if our
													// computers current time is
													// less than the end time of
													// the timer
	}

	public void reset() {// reset by updating when the timer should end
		end = System.currentTimeMillis() + period;
	}
}
