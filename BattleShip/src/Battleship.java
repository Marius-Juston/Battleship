import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

class Battleship {
    private final List<Player> players;

    public Battleship() {
//        Initialise the array of players
        players = new ArrayList<>(2);
    }

    /**
     * Returns the player at the specific index
     *
     * @param i index of the player
     * @return Returns the player
     */
    public final Player getPlayer(final int i) {
        return players.get(i);
    }

    /**
     * Adds the player to the arrayList of players
     *
     * @param player player to add to the arrayList
     */
    public final void addPlayer(final Player player) {
        players.add(player);
    }

    /**
     * Remove any Players from the players List
     * if all of their ships have been sunk.
     */
    public final void upkeep() {
        for (int i = players.size() - 1; i >= 0; i--) {
            final Collection<Ship> temp = new HashSet<>(5);

            final int[][] board = players.get(i).getGuessBoard();

//            Adds to the set the ships that are not null and that have sunk
            for (int r = 0; r < board.length; r++)
                for (int c = 0; c < board[r].length; c++) {
//                    sees if there is a ship at the current board location
                    final Ship ship = players.get(i).getShip(new Location(r, c));

                    if ((ship != null) && ship.isSunk())
                        temp.add(ship);
                }

//            if the same number of ships are the same number of sunken ship removes player
            if (temp.size() == players.get(i).getNumberOfShips())
                players.remove(i);
        }
    }

    /**
     * Returns true if only one Player remains in
     * the players List; false otherwise.
     *
     * @return true if only one player remaining false otherwise
     */
    public final boolean gameOver() {
        return players.size() == 1;
    }

    /**
     * Returns the only Player remaining in the
     * players List if the game is over; returns
     * null otherwise.
     *
     * @return winning player
     */
    public final Player getWinner() {
        return gameOver() ? players.get(0) : null;
    }

    @Override
    public String toString() {
        return "Battleship{" +
                "players=" + players +
                '}';
    }
}
