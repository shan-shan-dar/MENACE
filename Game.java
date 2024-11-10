import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public GameState curGameState;
    public boolean turn;
    public ArrayList<GameState> stateHistory;

    public Game(){
       curGameState = new GameState(new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        });
        turn = false;
        stateHistory = new ArrayList<>();
    }

    public int run(ArrayList<Matchbox> matchboxes){
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
                boolean matchboxExists = false;

                for (Matchbox matchbox : matchboxes) {
                    if (curGameState.compareTo(matchbox.getGameStateCopy()) == 0){
                        move = matchbox.drawRandomBead();
                        matchboxExists = true;
                        break;
                    }
                }

                if (!matchboxExists){
                    Matchbox newMatchbox = new Matchbox(curGameState);
                    matchboxes.add(newMatchbox);

                    move = newMatchbox.drawRandomBead();
                }
            }

            GameState oldGameState = curGameState;
            curGameState = curGameState.makeMove((turn ? 1 : -1), move);
            stateHistory.add(oldGameState);

            turn = !turn;
        }

        System.out.println(curGameState);
        System.out.println(((curGameState.checkWon() == -1) ? "You win!" : "MENACE wins!") + "\n");

        return curGameState.checkWon();
    }
}
