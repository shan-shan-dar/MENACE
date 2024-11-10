
public class GameState implements Comparable<GameState>{
    /*
     * 0    empty
     * 1    move by MENACE (X)
     * -1   move by player (O)
     */
    private final int[][] gameState;

    // tracks the latest move made to this state
    private int moveTracker;

    public GameState(int[][] gameState){
        this.gameState = gameState;
        moveTracker = -1;
    }

    public boolean isValidMove(int bead){
        int row = bead/3;
        int col = bead%3;

        if (row > 8 || row < 0 || col > 8 || col < 0){
            return false;
        }

        return gameState[row][col] == 0;
    }

    public int[][] getGameStateCopy(){
        int[][] gameStateCopy = new int[3][3];

        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[0].length; j++) {
                gameStateCopy[i][j] = gameState[i][j];
            }
        }
        return gameStateCopy;
    }

    public int getMoveTracker(){
        return moveTracker;
    }

    /*
     * 1    MENACE wins
     * -1   the player wins
     * 0    the game is still ongoing
     * -2   it’s a draw
     */
    public int checkWon(){
        // check rows
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i][0] != 0 && gameState[i][0] == gameState[i][1] && gameState[i][0] == gameState[i][2]) {
                return gameState[i][0];
            }
        }

        // check columns
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[0][i] != 0 && gameState[0][i] == gameState[1][i] && gameState[0][i] == gameState[2][i]) {
                return gameState[0][i];
            }
        }

        // check diagonals
        if (gameState[0][0] != 0 && gameState[0][0] == gameState[1][1] && gameState[0][0] == gameState[2][2]) {
            return gameState[0][0];
        }
        if (gameState[0][2] != 0 && gameState[0][2] == gameState[1][1] && gameState[0][2] == gameState[2][0]) {
            return gameState[0][2];
        }

        // game isn't over
        for (int[] row : gameState) {
            for (int cell : row) {
                if (cell == 0){
                    return 0;
                }
            }
        }

        // draw
        return -2;
    }

    // assumes move is valid
    public GameState makeMove(int player, int bead){
        GameState newMatchbox = new GameState(getGameStateCopy());

        int row = bead/3;
        int col = bead%3;

        newMatchbox.gameState[row][col] = player;

        moveTracker = bead;
        return newMatchbox;
    }

    @Override
    public String toString() {
        String result = "";
        String positions = "⁰¹²³⁴⁵⁶⁷⁸";
        int position = 0; // Initialize position counter for empty spaces
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                // Convert the values based on conditions
                switch (gameState[i][j]) {
                    case -1 -> result += "🅇";
                    case 1 -> result += "🄾";
                    default -> result += positions.charAt(position); // Represent 0 as its position number
                }
                
                position++; // Increment position

                // Add separator
                if (j < gameState[i].length - 1) {
                    result += " | ";
                }
            }
            // Add newline after each row, except the last row
            if (i < gameState.length - 1) {
                result += "\n";
            }
        }
        return result;
    }


    // 0 is equal, 1 otherwise
    @Override
    public int compareTo(GameState other) {
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (this.gameState[i][j] != other.gameState[i][j]) {
                    return 1;
                }
            }
        }
        return 0;
    }
}