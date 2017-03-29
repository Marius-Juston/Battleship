import java.awt.geom.Line2D;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Player {
    private final String name;
    private final List<Ship> ships;
    private final int[][] guessBoard;
    private final SecureRandom random;

    Player(final String name) {
        this.name = name;
        ships = new ArrayList<>(5);
        guessBoard = new int[10][10];
        random = new SecureRandom();

        populateShips();
    }

    public final String getName() {
        return name;
    }

    /**
     * Returns how many ships are currently in this
     * Player's ships List.
     *
     * @return
     */
    public final int getNumberOfShips() {
        return ships.size();
    }

    /**
     * Returns the ship that occupies the specified
     * Location loc.  Returns null if this Player
     * does not control a ship at Location loc.
     *
     * @param loc
     * @return
     */
    public final Ship getShip(final Location loc) {
        for (final Ship ship : ships)
            for (final Location location : ship.getLocations())
                if (location.equals(loc))
                    return ship;

        return null;
    }

    /**
     * Returns true if this Player controls a ship
     * at the specified Location loc; false
     * otherwise.
     *
     * @param loc
     * @return
     */
    public final boolean hasShipAtLocation(final Location loc) {
        return getShip(loc) != null;
    }

    public final int[][] getGuessBoard() {
        return guessBoard;
    }

    /**
     * Returns true if obj is an instanceof Player and
     * instance variables are equivalent.
     *
     * @param obj
     * @return
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) return true;
        if ((obj == null) || (getClass() != obj.getClass())) return false;

        final Player player = (Player) obj;

        return ((null != name) ? name.equals(player.name) : (null == player.name)) && ((null != ships) ? ships.equals(player.ships) : (null == player.ships)) && Arrays.deepEquals(guessBoard, player.guessBoard);

    }

    @Override
    public int hashCode() {
        int result = (name != null) ? name.hashCode() : 0;
        result = (31 * result) + ((ships != null) ? ships.hashCode() : 0);
        result = (31 * result) + Arrays.deepHashCode(guessBoard);
        result = (31 * result) + ((random != null) ? random.hashCode() : 0);
        return result;
    }

    /**
     * Attack the specified Player at the specified Location.
     * <p>
     * Return true if the attack resulted in a ship sinking;
     * false otherwise.
     *
     * @param enemy
     * @param loc
     * @return
     */
    public abstract boolean attack(Player enemy, Location loc);

    /**
     * Construct an instance of
     * <p>
     * AircraftCarrier,
     * Destroyer,
     * Submarine,
     * Cruiser, and
     * PatrolBoat
     * <p>
     * and add them to this Player's list of ships.
     */
    private void populateShips() {
//        ships.add(new AircraftCarrier(new Location(1, 1), new Location(1, 2), new Location(1, 3), new Location(1, 4), new Location(1, 5)));

        ships.add(new AircraftCarrier(getRandomLocation(5)));
        ships.add(new Destroyer(getRandomLocation(4)));
        ships.add(new Submarine(getRandomLocation(3)));
        ships.add(new Cruiser(getRandomLocation(3)));
        ships.add(new PatrolBoat(getRandomLocation(2)));
    }

    private Location[] getRandomLocation(final int length) {
        while (true) {
            final Location[] locations = new Location[length];

            if (length > 0) {
                final boolean up = random.nextBoolean();
                final boolean direction = random.nextBoolean();

                int row = random.nextInt(guessBoard.length);
                int col = random.nextInt(guessBoard[0].length);

//
                for (int t = 0; t < length; t++) {
                    locations[t] = new Location((int) (Math.signum(row) * row), (int) (Math.signum(col) * col));

                    if (up)
                        row += direction ? -1 : 1;
                    else
                        col += direction ? -1 : 1;
                }

                boolean restart = (locations[locations.length - 1].getCol() >= guessBoard[0].length) || (locations[locations.length - 1].getRow() >= guessBoard.length);

                for (final Ship ship : ships) {
                    final List<Location> locations1 = ship.getLocations();
                    Collections.sort(locations1);

                    if (!locations1.isEmpty())
                        if (Line2D.linesIntersect(locations[0].getCol(), locations[0].getRow(), locations[locations.length - 1].getCol(), locations[locations.length - 1].getRow(), locations1.get(0).getCol(), locations1.get(0).getRow(), locations1.get(locations1.size() - 1).getCol(), locations1.get(locations1.size() - 1).getRow())) {
                            restart = true;
                            break;
                        }
                }

                if (restart) {
                    continue;
                }

            }

            return locations;

        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", ships=" + ships +
                ", guessBoard=" + Arrays.toString(guessBoard) +
                ", random=" + random +
                '}';
    }
}
