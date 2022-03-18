package EightPuzzleSolver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Stack;

public class F027497 {
	private static Scanner Scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		boolean active = true;
		instructions(); 	//Display input example.
		
		//Get user inputs for each root state.
		System.out.println("<PUZZLE ONE>");
		char[] startState1 = getPuzzleValues();
		State root1 = new State(startState1);
		System.out.println("<PUZZLE TWO>");
		char[] startState2 = getPuzzleValues();
		State root2 = new State(startState2);
		
		//Traverse search space with Depth First Search to find reachable states and intersect.
		System.out.println("Loading Results...");
		LinkedHashSet<State> reachableStates1 = DFS(root1);
		LinkedHashSet<State> reachableStates2 = DFS(root2);
		LinkedHashSet<State> commonStates = getCommonStates(reachableStates1, reachableStates2);
		
		//Options menu
		while (active) {
			System.out.println("ITEMS SELECT:");
			char option = Scan.nextLine().charAt(0);
            switch (option) {
            case 'a' : 	//Write Reachable States 2 set to file.
            	printPath(reachableStates1);
            	break;
            case 'b' :	//Get Reachable States 1 cardinality.
            	System.out.print("Reachable States 1 Size:");
            	System.out.println(reachableStates1.size());
            	break;
            case 'c' :	//Write Reachable States 2 set to file.
            	printPath(reachableStates2);
            	break;
            case 'd' : 	//Get Reachable States 2 cardinality.
            	System.out.print("Reachable States 2 Size:");
            	System.out.println(reachableStates2.size());
            	break;
            case 'e' : 	//Write intersecting states to file.
            	printPath(commonStates);
            	break;
            case 'f' : 	//Get interesect cardinality.
            	System.out.print("Reachable Intersect Size:");
            	System.out.println(commonStates.size());
            	break;
            case 'q' :	//Quit program.
            	return;
        }
		}
		
		
		Scan.close();
	}    
	

	
	// Depth First Search Method returns all visited states.
	private static LinkedHashSet<State> DFS(State root) {
		Stack<char[]> stack = new Stack<>();
		LinkedHashSet<State> reachableStates = new LinkedHashSet<>();
		//pushes root onto stack.
		stack.push(root.getState());
        
        //finds current states children and add to stack if new, and old states are skipped.

        while(stack.isEmpty()==false) {
        	State currentState = new State(stack.pop());
        	if (reachableStates.add(currentState)) {  //check if currentState is new and gets added to visited if true.
        		
                ArrayList<char[]> newStates= findChildren(currentState);     //direct children are generated.
                
                for (char[] childState : newStates) {   //pushes each child state one by one to stack even if its old.
                		
                		stack.push(childState);
                    
                }
        	}
        } 								// While loop ends when there is no more states to explore.
        
        return reachableStates;			// Returns all reachable States.	
	}
	
	
	// Find intersect of reachable states 1 and 2. (Basically equivalent!)
	public static LinkedHashSet<State> getCommonStates (LinkedHashSet<State> reachableStates1, LinkedHashSet<State> reachableStates2){
      LinkedHashSet<State> commonStates = new LinkedHashSet<>(reachableStates1);
      commonStates.retainAll(reachableStates2);
      return commonStates;
  }
	

	//Display sets in "Reachable States.txt" file.
	public static void printPath(LinkedHashSet<State> foundStates) {
		
		for (State found : foundStates) {
			System.out.print(found.getState()[0]);
			System.out.print("  ");
			System.out.print(found.getState()[1]);
			System.out.print("  ");
			System.out.println(found.getState()[2]);
			System.out.println(" ");
			System.out.print(found.getState()[3]);
			System.out.print("  ");
			System.out.print(found.getState()[4]);
			System.out.print("  ");
			System.out.println(found.getState()[5]);
			System.out.println(" ");
			System.out.print(found.getState()[6]);
			System.out.print("  ");
			System.out.print(found.getState()[7]);
			System.out.print("  ");
			System.out.println(found.getState()[8]);
			
			System.out.println();
			System.out.println();
		}
		
        try {
        	//Try creating and writing to file.
        	
        	ArrayList<State> found = new ArrayList<>(foundStates);
        	File filename = new File("Reachable States.txt");
            FileWriter fileWrite = new FileWriter(filename.getAbsoluteFile());

            BufferedWriter bw = new BufferedWriter(fileWrite);
            
            // If file doesn't exist, then create it.
            if (!filename.exists()) {
                filename.createNewFile();
            }
            // Write each found reachable state to file
            for (State state : found) {
            	bw.write(state.getState());
            	bw.newLine();
            	
            }
            //Tell user that the file is written successfully.
            bw.write("Done!");
            bw.close();
            System.out.println("Successfully stored reachable states to: "+filename.getAbsolutePath() );

            //If something is wrong with any file operations, notify user.
        } catch (IOException e) {
    	  System.out.println("Text File Error:");
          e.printStackTrace();
        }
	    
	
	}
	
	// Example of how the inputs are ordered in a root state, to help user understand how to use the program.
	private static void instructions() {
		System.out.println("Tiles are ordered from top-left to bottom-right.");
		System.out.println(" ");
		System.out.println("The empty space is represented by '_'. Example: 5th tile is a space:");
		System.out.println(" ");
		System.out.println("1st 2nd 3rd");
		System.out.println("4th  _  6th");
		System.out.println("7th 8th 9th");
		System.out.println(" ");
	}

	


	//Get user input start state
	private static char[] getPuzzleValues() {
		char[] values = new char[9];
		System.out.println(" ");
		
		for (int i = 1; i < 10; i++) {
			System.out.println("Enter Tile "+ i + ":");
			char input = Scan.nextLine().charAt(0);
			values[i-1] = input;
		}
		
		return values;
		
	}
	

	//Returns a list of child nodes for a given parent node.
	private static ArrayList<char[]> findChildren(State state) {
		ArrayList<char[]> childStates = new ArrayList<>();
		byte emptyTile = state.getEmptyPos();
		char[] newState;
		
		//Finds which new moves are available by the empty position value.
		
		switch (emptyTile) {
			case 0://Right, Down

				newState=swapTile(state.getState(), emptyTile, 1);
				childStates.add(newState);

				newState=swapTile(state.getState(), emptyTile, 3);
				childStates.add(newState);
			    break;
			case 1://Left, Right, Down

			    newState=swapTile(state.getState(), emptyTile, -1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 3);
			    childStates.add(newState);
			    break;
			case 2://Left, Down

			    newState=swapTile(state.getState(), emptyTile, -1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 3);
			    childStates.add(newState);
			    break;
			case 3://Right,Up,Down

			    newState=swapTile(state.getState(), emptyTile, 1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, -3);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 3);
			    childStates.add(newState);
			    break;
			case 4://Left,Right,Up, Down

			    newState=swapTile(state.getState(), emptyTile, -1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, -3);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 3);
			    childStates.add(newState);
			    break;
			case 5://Left, Up, Down

			    newState=swapTile(state.getState(), emptyTile, -1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, -3);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 3);
			    childStates.add(newState);
			    break;
			case 6: //Right, Up

			    newState=swapTile(state.getState(), emptyTile, 1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, -3);
			    childStates.add(newState);
			    break;
			case 7://Left,Right,Up

			    newState=swapTile(state.getState(), emptyTile, -1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, 1);
			    childStates.add(newState);
			    newState=swapTile(state.getState(), emptyTile, -3);
			    childStates.add(newState);
			    break;
			case 8://Left, Up

				newState=swapTile(state.getState(), emptyTile, -1);
				childStates.add(newState);

				newState=swapTile(state.getState(), emptyTile, -3);
				childStates.add(newState);
				break;
			}
		
		//Stores all child nodes even the current state's parent-state. The parent is removed after.
		return childStates;
		
		
	}
	
	//Moves correct tiles to get a child state.
	private static char[] swapTile(char[] state, byte emptyPos, int swapPos) {
		//Create a deep copy of an array and swap the correct tile positions with empty position.
		char[] newState = new char[9];
		System.arraycopy(state, 0, newState, 0, 9);	 
		newState[emptyPos + swapPos]=state[emptyPos];     //New empty position
		newState[emptyPos]= state[emptyPos + swapPos];    //New tile position found at old empty position.
		return newState;
		
	}
	


}
