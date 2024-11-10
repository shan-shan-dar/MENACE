public class Config {
    // Initial bead count for each legal move
    public static final int INITIAL_BEAD_AMOUNT = 5;

    // Don't punish if number of beads is at this amount
    public static final int MINIMUM_BEAD_AMOUNT = 0;
    
    // Reward/punishment values
    public static final int WIN_REWARD_AMOUNT = 2;
    public static final int DRAW_REWARD_AMOUNT = 1;
    public static final int PUNISHMENT_AMOUNT = 1;

    // Optional: Scaling factors for experimenting with doubling/halving
    public static final double WIN_MULTIPLIER = 2.0;
    public static final double LOSS_DIVISOR = 2.0;
}
