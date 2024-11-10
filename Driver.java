import java.util.ArrayList;

public class Driver {
    private static int gameCount = 0;
    private final static ArrayList<Integer> gameHistory = new ArrayList<>();
    private final static ArrayList<Matchbox> matchboxes = new ArrayList<>();

    public static void main(String[] args) {
        while(true){
            System.out.println("Games played: " + gameCount);
            System.out.println("MENACE win-rate: " + MENACEWinPercent());
            System.out.println("MENACE game history: " + printGameHistory());
            System.out.println();

            Game game = new Game(matchboxes);
            gameHistory.add(game.run());
            gameCount++;

            for (Matchbox state : game.stateHistory) {
                System.out.println(state);
                System.out.println(state.getMoveTracker());
                System.out.println(state.isMENACEMove());
                System.out.println();
            }
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
