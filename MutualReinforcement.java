import java.util.Scanner;

public class MutualReinforcement {
    private static final int checkpoint = 25;
    private static final MENACE bot1 = new MENACE();
    private static final MENACE bot2 = new MENACE(-1);
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            if (bot1.getGameCount() % checkpoint == 0){
                System.out.println("Bot 1 win-rate: " + bot1.MENACEWinPercent());
                System.out.println("Bot 2 win-rate: " + bot2.MENACEWinPercent());
                System.out.println("Games played: " + bot2.getGameCount());
                System.out.println("Game history: " + bot1.printGameHistory(1));
                System.out.println();

                System.out.print("Play bot 1, 2, continue training (0), or exit (-1): ");
                int input;
                do {
                    input = sc.nextInt();
                } while (input < -1 || input > 2);
                
                if (input == -1){
                    break;
                }
                
                if (input != 0){
                    MENACE gameBot;
                    if (input == 1){
                        gameBot = bot1;
                    } else {
                        gameBot = bot2;
                    }
            
                    boolean exit = false;
                    do {
                        Game game = new Game(gameBot, new HumanPlayer());
                        int result = game.run();
                
                        // code to exit
                        if (result == -10){
                            exit = true;
                        }
                
                        // code to show matchbox content
                        if (result == -20){
                            gameBot.printMatchboxesContents();
                        }
                    } while (!exit);
                }
            }
    
            Game game = new Game(bot1, bot2);
            int result = game.run();
    
            bot1.getGameHistory().add(result);
            bot2.getGameHistory().add(result);

            bot1.setGameCount(bot1.getGameCount() + 1);
            bot2.setGameCount(bot2.getGameCount() + 1);
    
            // reinforcement
            for (Matchbox matchbox : game.stateHistory) {
                if (matchbox.isPlayerOneMove()){
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

            for (Matchbox matchbox : game.stateHistory) {
                if (!matchbox.isPlayerOneMove()){
                    switch (result) {
                        case -1 -> {
                            for (int i = 0; i < Config.WIN_REWARD_AMOUNT; i++) {
                                matchbox.addToBeadBox(matchbox.getMoveTracker());
                            }
                        }
                        case 1 -> {
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
}
