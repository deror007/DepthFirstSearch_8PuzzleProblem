package EightPuzzleSolver;
import java.util.Arrays;

//Every state is generated with this class.
public class State {
	
	//Fields:
	private char[] state ;			//actual state from tiles 1-9.
	private byte emptyPos;			//empty position of a state.
	
	public State(char[] tiles) {
		// Constructor method for generating state
		this.state = tiles;

		this.emptyPos = findEmptyTile();

	}
	
	//Find the empty position number in a given state.
	private byte findEmptyTile() {
		for (byte i = 0; i < this.state.length; i++) {
			if (this.state[i] == '_'){
				return i;
			}
		}
		return -1;		//returns -1 if no underscore exists.
			
	}


// override hashcode and equals methods.
// VERY IMPORTANT as they check if different char pointers store the same char arrays. 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

// These override functions help the DFS establish old from new arrays to help append Visited States array.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return Arrays.equals(state, other.state);
	}

	// Getter method for a states tile order. 
	public char[] getState() {
		return state;
	}
	
	// Getter method for a states empty location.
	public byte getEmptyPos() {
		return emptyPos;
	}
	
	// No need for setter methods.
}