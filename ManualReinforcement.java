public class ManualReinforcement {
    private static final MENACE bot = new MENACE();

    public static void main(String[] args) {
        while(true){
            System.out.println("Games played: " + bot.getGameCount());
            System.out.println("MENACE win-rate: " + bot.MENACEWinPercent());
            System.out.println("MENACE game history: " + bot.printGameHistory());
            System.out.println();
    
            Game game = new Game(bot, new HumanPlayer());
            int result = game.run();
    
            // code to exit
            if (result == -10){
                return;
            }
    
            // code to show matchbox content
            if (result == -20){
                bot.printMatchboxesContents();
                continue;
            }
    
            bot.getGameHistory().add(result);
            bot.setGameCount(bot.getGameCount() + 1);
    
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
        }
    }   
}
