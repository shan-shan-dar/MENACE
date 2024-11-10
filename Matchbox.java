import java.util.ArrayList;
import java.util.Random;

public class Matchbox {
    private static final int INITIAL_BEAD_COUNT = 10;
    private final Random random;

    private final GameState gameState;
    /*
     * 0 | 1 | 2
     * 3 | 4 | 5
     * 6 | 7 | 8
     */
    private final ArrayList<Integer> beadBox;

    public Matchbox(GameState gameState){
        random = new Random();
        this.gameState = gameState;
        beadBox = new ArrayList<>(9 * INITIAL_BEAD_COUNT);
        
        for (int bead = 0; bead < 9; bead++) {
            if (gameState.isValidMove(bead)) {
                for (int i = 0; i < INITIAL_BEAD_COUNT; i++) {
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

    public GameState getGameStateCopy(){
        return new GameState(gameState.getGameStateCopy());
    }
}
