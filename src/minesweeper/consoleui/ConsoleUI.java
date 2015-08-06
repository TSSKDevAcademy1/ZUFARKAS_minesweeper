package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.BestTimes;
import minesweeper.BestTimes.PlayerTime;
import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.
	 * Field)
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		System.out.println(System.getProperty("user.name") + "Hello\n");

		do {
			update();
			processInput();
			if (field.getState() == GameState.SOLVED) {
				update();
				System.out.println("Congratulation! You won!");
				System.exit(0);

			}
			if (field.getState() == GameState.FAILED) {
				update();
				System.out.println("You lost!");
				System.exit(0);
			}
			
		} while (true); 
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);

		formatter.format("%3s", " ");
		for (int k = 0; k < field.getColumnCount(); k++) {
			formatter.format("%3s", k);
		}
		formatter.format("%3s", "\n");
		for (int i = 0; i < field.getRowCount(); i++) {
			formatter.format("%3c", i + 65);
			for (int j = 0; j < field.getColumnCount(); j++) {
				formatter.format("%3s", field.getTile(i, j));
			}
			formatter.format("%4s", "\n");// System.out.printf("%n");
		}
		System.out.println(sb);
		int i = field.getRemainingMineCount();
		System.out.println("The count of remaining mine is: " + i);
		System.out.println("The best time is: " + Minesweeper.getInstance().getPlayingSeconds());
		

		/*
		 * System.out.printf("%3s", " "); for (int k = 0; k <
		 * field.getColumnCount(); k++) { System.out.printf("%3s", k); }
		 * System.out.println(); for (int i = 0; i < field.getRowCount(); i++) {
		 * System.out.printf("%3c", i + 65); for (int j = 0; j <
		 * field.getColumnCount(); j++) { System.out.printf("%3s",
		 * field.getTile(i, j)); } System.out.printf("%n"); } int i =
		 * field.getRemainingMineCount(); System.out.println(
		 * "The count of remaining mine is: "+i);
		 */
	}

	
	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {

		String input = readLine();
		input = input.toUpperCase();
		try {
			handleInput(input);
		} catch (WrongFormatException ex) {
			System.out.println(ex.getMessage());
		}

	}

	
	public void handleInput(String input) throws WrongFormatException {
		if (input.length() == 1) {
			if (input.charAt(0) == 'X') {
				System.out.println("you end game");
				System.exit(0);
			}
		} else {
			Pattern pattern = Pattern.compile("([MO])([A-J])([0-9])");
			Matcher matcher = pattern.matcher(input);
			if (matcher.matches()) {
				int column = Integer.parseInt(matcher.group(3));
				int row = matcher.group(2).charAt(0) - 'A';

				if (column >= field.getColumnCount() || row >= field.getRowCount()) {
					throw new WrongFormatException("Zadaj mensie cislo alebo pismeno");
				}
				

				if (matcher.group(1).charAt(0) == 'M') {
					field.markTile(row, column);
				} else {
					field.openTile(row, column);
				}
			} else {
				throw new WrongFormatException("Zly vstup");
			}
		}
	}
}
