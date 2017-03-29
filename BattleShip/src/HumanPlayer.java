public class HumanPlayer extends Player {
    public HumanPlayer(final String name) {
        super(name);
    }

    /**
     * Attack the specified Location loc.  Marks
     * the attacked Location on the guess board
     * with a positive number if the enemy Player
     * controls a ship at the Location attacked;
     * otherwise, if the enemy Player does not
     * control a ship at the attacked Location,
     * guess board is marked with a negative number.
     * <p>
     * If the enemy Player controls a ship at the attacked
     * Location, the ship must add the Location to its
     * hits taken.  Then, if the ship has been sunk, it
     * is removed from the enemy Player's list of ships.
     * <p>
     * Return true if the attack resulted in a ship sinking;
     * false otherwise.
     *
     * @param enemy
     * @param loc
     * @return
     */
    @Override
    public final boolean attack(final Player enemy, final Location loc) {
        final Ship ship = enemy.getShip(loc);

        if (ship != null) {
            getGuessBoard()[loc.getRow()][loc.getCol()] = 1;
            ship.takeHit(loc);

            if (ship.isSunk())
                return true;

        } else
            getGuessBoard()[loc.getRow()][loc.getCol()] = -1;

        return false;
    }
}
