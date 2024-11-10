import java.util.ArrayList;
import java.util.Random;

public class Matchbox {
    private final Random random;

    private final int[][] gameState;
    private final ArrayList<Integer> beadBox;

    // tracks the latest move made to this state
    private int moveTracker;
    private boolean isMENACEMove;

    public Matchbox(int[][] gameState){
        random = new Random();
        this.gameState = gameState;
        beadBox = new ArrayList<>(9 * Config.INITIAL_BEAD_AMOUNT);
        
        for (int bead = 0; bead < 9; bead++) {
            if (isValidMove(bead)) {
                for (int i = 0; i < Config.INITIAL_BEAD_AMOUNT; i++) {
                    beadBox.add(bead);
                }
            }
        }
    }

    public void addToBeadBox(int bead){
        beadBox.add(bead);
    }

    public boolean removeFromBeadBox(int bead){
        return beadBox.remove((Integer)bead);
    }

    public int drawRandomBead() {
        int index = random.nextInt(beadBox.size());
        int move = beadBox.get(index);

        return move;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------

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

    public boolean isMENACEMove() {
        return isMENACEMove;
    }

    public void setMoveTracker(int move){
        moveTracker = move;
    }

    public void setMENACEMove(boolean isMENACEMove) {
        this.isMENACEMove = isMENACEMove;
    }

    public ArrayList<Integer> getBeadBoxContents() {
        return beadBox;
    }

    /*
     * 1    MENACE wins
     * -1   the player wins
     * -2    the game is still ongoing
     * 0   it’s a draw
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
                    return -2;
                }
            }
        }

        // draw
        return 0;
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

    public int compareTo(int[][] other) {
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (this.gameState[i][j] != other[i][j]) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
