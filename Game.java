import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public Matchbox curGameState;
    public boolean turn;
    public ArrayList<Matchbox> stateHistory;
    public ArrayList<Matchbox> matchboxes;

    public Game(ArrayList<Matchbox> matchboxes){
       curGameState = new Matchbox(new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        });
        turn = false;
        stateHistory = new ArrayList<>();
        this.matchboxes = matchboxes;
    }

    public int run(){
        Scanner sc = new Scanner(System.in);

        while (curGameState.checkWon() == 0){

            int move = -1;

            if (!turn) {
        
                System.out.println(curGameState);

                // take user move
                do { 
                    System.out.print("Move: ");
                    move = sc.nextInt();
                    System.out.println();
                } while (!curGameState.isValidMove(move));
            } else {
                // determine MENACE move
                move = curGameState.drawRandomBead();
            }

            Matchbox oldGameState = curGameState;
            curGameState = makeMove((turn ? 1 : -1), move);
            stateHistory.add(oldGameState);

            turn = !turn;
        }

        System.out.println(curGameState);
        System.out.println(((curGameState.checkWon() == -1) ? "You win!" : "MENACE wins!") + "\n");

        return curGameState.checkWon();
    }

    // assumes move is valid
    public Matchbox makeMove(int player, int bead){
        // Create a copy of the current game state
        int[][] newState = curGameState.getGameStateCopy();

        // Apply the move to the new game state
        int row = bead / 3;
        int col = bead % 3;
        newState[row][col] = player;
    
        // Check if this game state already exists in matchboxes
        for (Matchbox matchbox : matchboxes) {
            if (matchbox.compareTo(newState) == 0) {
                curGameState.setMoveTracker(bead);
                curGameState.setMENACEMove(player == 1);
                return matchbox;  // Return the existing matchbox with the learned bead counts
            }
        }
    
        // If the state doesn’t exist, create a new Matchbox and add it to matchboxes
        Matchbox newMatchbox = new Matchbox(newState);
        matchboxes.add(newMatchbox);
    
        // Update the move tracker and MENACE move indicator
        curGameState.setMoveTracker(bead);
        curGameState.setMENACEMove(player == 1);
    
        return newMatchbox;
    }
}
