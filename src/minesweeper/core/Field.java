package minesweeper.core;

import java.util.Random;

import minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();

	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);

			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}

			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}

			if (tile instanceof Clue) { // zisti, ci je tile instanciou Clue.
										// Ale tu je to zbytocne zistit lebo je.
				Clue clue = (Clue) tile; // pretypovanie premennej tile na typ
											// Clue
				if (clue.getValue() == 0) {
					this.openAdjacentTiles(row, column);
				}
			}
		}
	}

	public void openAdjacentTiles(int row, int column) {

		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < getRowCount()) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < getColumnCount()) {
						Tile tile = getTile(actRow, actColumn);
						if (tile.getState() == Tile.State.CLOSED) {
							this.openTile(actRow, actColumn);
						}
					}
				}
			}
		}
	}

	/**
	 * return state of game
	 * 
	 * @return state state of Game
	 */

	public GameState getState() {
		return state;
	}

	/**
	 * 
	 * @return rowCount of
	 */

	public int getRowCount() {
		return rowCount;
	}

	/**
	 * 
	 * @return
	 */
	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];

	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		// throw new UnsupportedOperationException("Method markTile not yet
		// implemented");
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
			return;
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
			return;
		}
	}

	/**
	 * Generates playing field. Random generate of mines (náhodne rozlozenie mín
	 * a doplnenie hernych pol dlaždicami typu Clue)
	 */

	private void generate() {
		// throw new UnsupportedOperationException("Method generate not yet
		// implemented");
		for (int i = 0; i < mineCount; i++) {
			int r = new Random().nextInt(rowCount);
			int c = new Random().nextInt(columnCount);
			if (tiles[r][c] == null) {
				tiles[r][c] = new Mine();
			} else {
				i--;
			}
		}

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (tiles[i][j] == null) {

					tiles[i][j] = new Clue(countAdjacentMines(i, j));
				}
			}
		}

	}

	private int getNumberOf(Tile.State state) {
		int counter = 0;
		for (int r = 0; r < tiles.length; r++) {
			for (int c = 0; c < tiles.length; c++) {
				if (tiles[r][c].getState() == state) {
					++counter;

				}
			}
		}
		return counter;
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		int pom = rowCount * columnCount - getNumberOf(State.OPEN);
		if (pom == mineCount) {
			return true;
		} else {
			return false;
		}
		// throw new UnsupportedOperationException("Method isSolved not yet
		// implemented");
		/*
		 * Hra je úspešne ukončená ak platí počet všetkých dlaždíc - počet
		 * odokrytých dlaždíc = počet mín. Z uvedeného vyplýva, že hra bude
		 * ukončená vtedy, ak budú odokryté všetky dlaždice bez mín. Pre určenie
		 * počtu odkrytých dlaždíc definujte súkromnú metódu int
		 * getNumberOf(Tile.State state), ktorá vráti počet dlaždíc v danom
		 * stave.
		 */

	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		String pom = "";
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				pom = pom + tiles[i][j].toString() + " ";

			}
			pom += "\n";
		}
		return pom;
	}

	public int getRemainingMineCount() {
		/*použite metódu int getNumberOf(Tile.State state) triedy Field, ktorá vráti počet dlaždíc v požadovanom stave. */
		int remainingMineCount= mineCount - getNumberOf(State.MARKED);
		return remainingMineCount;
	}
}