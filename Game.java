import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    public Matchbox curGameState;
    public boolean turn;
    public ArrayList<Matchbox> stateHistory;
    public HashMap<String, Matchbox> matchboxes;

    public Game(HashMap<String, Matchbox> matchboxes){
        // empty board
        curGameState = matchboxes.get(Driver.serializeState(new int[3][3]));
        // true if MENACE starts first, false otherwise
        turn = true;
        stateHistory = new ArrayList<>();
        this.matchboxes = matchboxes;
    }

    public int run(){
        Scanner sc = new Scanner(System.in);

        while (curGameState.checkWon() == -2){

            int move = -1;

            if (!turn) {
        
                System.out.println(curGameState);

                // take user move
                do { 
                    System.out.print("Move: ");
                    move = sc.nextInt();
                    System.out.println();
                } while (!(curGameState.isValidMove(move) || move == -10 || move == -20));

                if (move == -10){
                    return -10;
                }

                if (move == -20){
                    return -20;
                }

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
        System.out.println(curGameState.checkWon() == 0 ? "Draw" : (((curGameState.checkWon() == -1) ? "You win!" : "MENACE wins!")) + "\n");

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

        String stateKey = Driver.serializeState(newState);
    
        // Check if this game state already exists in matchboxes
        if (matchboxes.containsKey(stateKey)) {
            curGameState.setMoveTracker(bead);
            curGameState.setMENACEMove(player == 1);
            return matchboxes.get(stateKey);
        }
    
        // If the state doesn’t exist, create a new Matchbox and add it to matchboxes
        Matchbox newMatchbox = new Matchbox(newState);
        matchboxes.put(stateKey, newMatchbox);
    
        // Update the move tracker and MENACE move indicator
        curGameState.setMoveTracker(bead);
        curGameState.setMENACEMove(player == 1);
    
        return newMatchbox;
    }
}
