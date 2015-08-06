package minesweeper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name ot the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		
		playerTimes.add(new PlayerTime(name, time));
		Collections.sort(playerTimes);
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<PlayerTime> it = iterator();
		while (it.hasNext()) {
			PlayerTime plt = it.next();
			sb.append(plt.toString()).append("\n");
		}
		return sb.toString();

	}
	
	private void reset(){
		playerTimes.clear();
	}
	

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		@Override
		public int compareTo(PlayerTime o) {
			// TODO Auto-generated method stub

			if (this.time < o.time) {
				return -1;
			} else if (this.time == o.time) {
				return 0;
			} else
				return 1;

		}

		@Override
		public String toString() {
			Formatter formatter = new Formatter();

			formatter.format("%20s %s", this.name, this.time); 
			return formatter.toString();
		}

	}
}
