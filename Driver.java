import java.util.ArrayList;
import java.util.HashMap;

public class Driver {
    private static int gameCount = 0;
    private final static ArrayList<Integer> gameHistory = new ArrayList<>();
    private final static HashMap<String, Matchbox> matchboxes = initializeMatchboxes();

    public static void main(String[] args) {
        while(true){
            System.out.println("Games played: " + gameCount);
            System.out.println("MENACE win-rate: " + MENACEWinPercent());
            System.out.println("MENACE game history: " + printGameHistory());
            System.out.println();

            Game game = new Game(matchboxes);
            int result = game.run();

            // code to exit
            if (result == -10){
                return;
            }

            // code to show matchbox content
            if (result == -20){
                printMatchboxesContents();
                continue;
            }

            gameHistory.add(result);
            gameCount++;

            // reinforcement
            for (Matchbox matchbox : game.stateHistory) {
                if (matchbox.isMENACEMove()){
                    switch (result) {
                        case 1 -> {
                            for (int i = 0; i < Config.WIN_REWARD_AMOUNT; i++) {
                                matchbox.addToBeadBox(matchbox.getMoveTracker());
                            }
                        }
                        case -1 -> {
                            for (int i = 0; i < Config.PUNISHMENT_AMOUNT; i++) {
                                matchbox.removeFromBeadBox(matchbox.getMoveTracker());
                            }
                        }
                        default -> {
                            for (int i = 0; i < Config.DRAW_REWARD_AMOUNT; i++) {
                                matchbox.addToBeadBox(matchbox.getMoveTracker());
                            }
                        }
                    }
                }
            }


        }
    }

    public static void printMatchboxesContents() {
    System.out.println("Matchboxes Contents:");
    
    for (String key : matchboxes.keySet()) {
        Matchbox matchbox = matchboxes.get(key);
        System.out.println("GameState (Serialized): " + key);
        System.out.println("BeadBox: " + matchbox.getBeadBoxContents());
        System.out.println();
    }
}

    private static HashMap<String, Matchbox> initializeMatchboxes() {
        HashMap<String, Matchbox> map = new HashMap<>();

        // Create an empty board (3x3 with all cells set to 0)
        int[][] emptyBoard = new int[3][3]; // Automatically initializes with all zeros

        // Serialize the empty board state to create a unique key
        String emptyBoardKey = serializeState(emptyBoard);

        // Create a Matchbox for the empty board and add it to the map
        Matchbox emptyMatchbox = new Matchbox(emptyBoard);
        map.put(emptyBoardKey, emptyMatchbox);

        return map;
    }

    public static String serializeState(int[][] gameState) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : gameState) {
            for (int cell : row) {
                sb.append(cell).append("-");
            }
        }
        return sb.toString();
    }

    public static float MENACEWinPercent(){
        if (gameHistory.isEmpty()) return 0; 
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
