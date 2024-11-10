import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private static int gameCount = 0;
    private final static ArrayList<Integer> gameHistory = new ArrayList<>();
    private final static ArrayList<Matchbox> matchboxes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("Games played: " + gameCount);
            System.out.println("MENACE win-rate: " + MENACEWinPercent());
            System.out.println("MENACE game history: " + printGameHistory());
            System.out.println();

            Game game = new Game();
            gameHistory.add(game.run(matchboxes));
            gameCount++;
            
        }

        
    }

    public static float MENACEWinPercent(){
        float wins = 0;
        for (Integer game : gameHistory) {
            if (game == 1){
                wins++;
            }
        }
        return wins/gameHistory.size();
    }

    public static String printGameHistory(){
        String toString = "";
        for (Integer game : gameHistory) {
            switch (game) {
                case -1 -> toString += "L";
                case 1 -> toString += "W";
                default -> toString += "D";
            }
            toString += " ";
        }
        
        return toString;
    }
}
