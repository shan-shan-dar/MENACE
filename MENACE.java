import java.util.ArrayList;
import java.util.HashMap;

public class MENACE implements Player {
    private final int sign; // either 1 or -1
    private int gameCount;
    private final ArrayList<Integer> gameHistory;
    private final HashMap<String, Matchbox> matchboxes;

    public MENACE(int sign){
        this.sign = sign;
        gameCount = 0;
        gameHistory = new ArrayList<>();
        matchboxes = initializeMatchboxes();
    }

    public MENACE(){
        this(1);
    }

    public int getSign(){
        return sign;
    }

    public int getGameCount(){
        return gameCount;
    }

    public void setGameCount(int gameCount){
        this.gameCount = gameCount;
    }

    public HashMap<String, Matchbox> getMatchboxes(){
        return matchboxes;
    }

    public ArrayList<Integer> getGameHistory(){
        return gameHistory;
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

    public void printMatchboxesContents() {
        System.out.println("Matchboxes Contents:");
        
        for (String key : matchboxes.keySet()) {
            Matchbox matchbox = matchboxes.get(key);
            System.out.println("GameState (Serialized): " + key);
            System.out.println("BeadBox: " + matchbox.getBeadBoxContents());
            System.out.println();
        }
    }

    public static String serializeState(int[][] gameState) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : gameState) {
            for (int cell : row) {
                sb.append(cell).append("/");
            }
        }
        return sb.toString();
    }

    public float MENACEWinPercent(){
        if (gameHistory.isEmpty()) return 0; 
        float wins = 0;
        for (Integer game : gameHistory) {
            if (game == sign){
                wins++;
            }
        }
        return wins/gameHistory.size();
    }

    public String printGameHistory(int mode, int n) {
        String characters = "";
    
        switch (mode) {
            case 1 -> characters += "210";
            default -> characters += "LWD";
        }
    
        StringBuilder toString = new StringBuilder();
        int start = Math.max(0, gameHistory.size() - n);  // Calculate the starting index for the latest n entries
    
        // Add ellipsis if there are more than n entries
        if (gameHistory.size() > n) {
            toString.append("... ");
        }
    
        // Loop through the latest n entries
        for (int i = start; i < gameHistory.size(); i++) {
            Integer game = gameHistory.get(i);
            if (game == -sign) {
                toString.append(characters.charAt(0));
            } else if (game == sign) {
                toString.append(characters.charAt(1));
            } else {
                toString.append(characters.charAt(2));
            }
            toString.append(" ");
        }
    
        return toString.toString().trim();  // Trim trailing space
    }

    public String printGameHistory(int mode) {
        return printGameHistory(1, 50);
    }

    public String printGameHistory(){
        return printGameHistory(0);
    }

    @Override
    public int makeMove(Game game) {
        return game.curGameState.drawRandomBead();
    }
}
