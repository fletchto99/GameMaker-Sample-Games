package reaction;

/**
 * A Timer
 */
public class Timer {

	private long end;
	private final long start;

	/**
	 * Instantiates a new Timer with a given time period in milliseconds.
	 * 
	 * @param period
	 *            Time period in milliseconds.
	 */
	public Timer(final long period) {
		start = System.currentTimeMillis();
		end = start + period;
	}

	/**
	 * Returns <tt>true</tt> if this timer's time period has not yet elapsed.
	 * 
	 * @return <tt>true</tt> if the time period has not yet passed.
	 */
	public boolean isRunning() {
		return System.currentTimeMillis() < end;
	}

}