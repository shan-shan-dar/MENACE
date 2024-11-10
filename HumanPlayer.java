
import java.util.Scanner;

public class HumanPlayer implements Player {

    @Override
    public int makeMove(Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.println(game.curGameState);

        int move;

        // take player 2 move
        do { 
            System.out.print("Move: ");
            move = sc.nextInt();
            System.out.println();
        } while (!(game.curGameState.isValidMove(move) || move == -10 || move == -20));

        return move;
    }
    
}
