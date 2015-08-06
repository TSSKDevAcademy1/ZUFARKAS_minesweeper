package minesweeper.core;

/**
 * Tile of a field.
 */
public abstract class Tile {

	/** Tile states. */
	public enum State {
		/** Open tile. */
		OPEN, /** Closed tile. */
		CLOSED, /** Marked tile. */
		MARKED
	}

	/** Tile state. */
	
	private State state = State.CLOSED; 	//musia byt policka uzavrete CLOSED ak chcem zacat hrat

	/**
	 * Returns current state of this tile.
	 * 
	 * @return current state of this tile
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets current current state of this tile.
	 * 
	 * @param state
	 *            current state of this tile
	 */
	void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (getState() == State.MARKED) {
			return "M";
		} else {
			return "-";
		}
	}

}
