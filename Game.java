import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public Matchbox curGameState;
    public boolean playerOneTurn;
    public ArrayList<Matchbox> stateHistory;
    public final MENACE player1;
    public final Player player2;

    public Game(MENACE player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        // empty board
        curGameState = player1.getMatchboxes().get(MENACE.serializeState(new int[3][3]));
        // true if MENACE starts first, false otherwise
        playerOneTurn = true;
        stateHistory = new ArrayList<>();
    }

    public int run(){
        Scanner sc = new Scanner(System.in);

        while (curGameState.checkWon() == -2){

            int move = -10;

            if (!playerOneTurn) {
                move = player2.makeMove(this);

                // code for exit
                if (move == -10){
                    return -10;
                }

                // code to show matchbox content
                if (move == -20){
                    return -20;
                }

            } else {
                // determine MENACE move
                move = player1.makeMove(this);
            }

            Matchbox oldGameState = curGameState;
            curGameState = makeMove((playerOneTurn ? player1.getSign() : -player1.getSign()), move);
            stateHistory.add(oldGameState);

            playerOneTurn = !playerOneTurn;
        }

        System.out.println(curGameState);
        System.out.println((curGameState.checkWon() == 0 ? "Draw" : (((curGameState.checkWon() == -1) ? "Player 2 wins!" : "Player 1 wins!"))) + "\n");

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

        String stateKey = MENACE.serializeState(newState);
    
        // Check if this game state already exists in matchboxes
        if (player1.getMatchboxes().containsKey(stateKey)) {
            curGameState.setMoveTracker(bead);
            curGameState.setIsPlayerOneMove(player == 1);
            return player1.getMatchboxes().get(stateKey);
        }
    
        // If the state doesn’t exist, create a new Matchbox and add it to matchboxes
        Matchbox newMatchbox = new Matchbox(newState);
        player1.getMatchboxes().put(stateKey, newMatchbox);
    
        // Update the move tracker and MENACE move indicator
        curGameState.setMoveTracker(bead);
        curGameState.setIsPlayerOneMove(player == 1);
    
        return newMatchbox;
    }
}
