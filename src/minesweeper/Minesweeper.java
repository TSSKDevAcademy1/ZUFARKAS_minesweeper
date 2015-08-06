package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.UserInterface;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;
	private long startMillis = System.currentTimeMillis();

	BestTimes bt = new BestTimes();
	private BestTimes bestTimes = bt;

	private static Minesweeper instance;

	public static Minesweeper getInstance() {
		return instance;
	}

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		instance=this;
		userInterface = new ConsoleUI();

		Field field = new Field(2, 2, 1);
		// System.out.println(field);
		userInterface.newGameStarted(field);
	}

	public int getPlayingSeconds() {
		long endTime = System.currentTimeMillis();
		return (int) ((endTime - startMillis) / 1000);
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		new Minesweeper();
	}
}
