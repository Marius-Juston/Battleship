import java.util.*;

public class ComputerPlayer extends Player {

    private static final String SUNK = "Sunk";
    private final Board board;

    ComputerPlayer(final String name) {
        super(name);
        board = new Board();
    }

    /**
     * Randomly chooses a Location that has not been
     * attacked (Location loc is ignored).  Marks
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
     * @param enemy The Player to attack.
     * @param loc   The Location to attack.
     * @return
     */
    @Override
    public boolean attack(final Player enemy, Location loc) {
        final Ship ship = enemy.getShip(loc = board.attack());

//        System.out.println(loc);

        if (ship != null) {
            getGuessBoard()[loc.getRow()][loc.getCol()] = 1;
            ship.takeHit(loc);

            board.getHits().add(loc);

            if (ship.isSunk()) {
                System.out.println(SUNK);

                board.getIntegers().remove(Integer.valueOf(ship.getLocations().size()));
                board.getMisses().addAll(board.getHits());
                board.getHits().clear();

                for (int[] i : board.board)
                    System.out.println(Arrays.toString(i));
                return true;
            }

        } else {
            getGuessBoard()[loc.getRow()][loc.getCol()] = -1;
            board.getMisses().add(loc);
        }

        for (int[] i : board.board)
            System.out.println(Arrays.toString(i));
        return false;
    }

    @Override
    public String toString() {
        return "ComputerPlayer{" +
                "board=" + board +
                '}';
    }

    static class Board {
        private final Collection<Location> misses = new HashSet<>(83);
        private int[][] board;
        private Set<Location> hits = new HashSet<>(5);
        private Collection<Integer> integers = new ArrayList<>(Arrays.asList(5, 4, 3, 3, 2));

        Board() {
            board = new int[10][10];
        }

        public Collection<Location> getMisses() {
            return misses;
        }

        public Collection<Integer> getIntegers() {
            return integers;
        }

        public Collection<Location> getHits() {
            return hits;
        }

        public void resetBoard() {
            board = new int[10][10];

            for (final Location location : misses)
                board[location.getRow()][location.getCol()] = -1;
        }

        //attacks the board using probability
        public Location attack() {
            resetBoard();

            if (getHits().isEmpty()) {
//                System.out.println("Hunting");

                for (final int[] r : board) {
                    for (int c = 0; c < r.length; c++) {
                        if (getIntegers().contains(5) && (c < (r.length - 4)))
                            if ((-1 != r[c]) && (-1 != r[c + 1]) && (-1 != r[c + 2]) && (-1 != r[c + 3]) && (-1 != r[c + 4])) {
                                ++r[c];
                                ++r[c + 1];
                                ++r[c + 2];
                                ++r[c + 3];
                                ++r[c + 4];
                            }

                        if (getIntegers().contains(4) && (c < (r.length - 3)))
                            if ((-1 != r[c]) && (-1 != r[c + 1]) && (-1 != r[c + 2]) && (-1 != r[c + 3])) {
                                ++r[c];
                                ++r[c + 1];
                                ++r[c + 2];
                                ++r[c + 3];
                            }
                        if (getIntegers().contains(3) && (c < (r.length - 2)))
                            if ((-1 != r[c]) && (-1 != r[c + 1]) && (-1 != r[c + 2])) {
                                ++r[c];
                                ++r[c + 1];
                                ++r[c + 2];

                            }
                        if (getIntegers().contains(3) && (2 == Collections.frequency(getIntegers(), 3)) && (c < (r.length - 2)))
                            if ((-1 != r[c]) && (-1 != r[c + 1]) && (-1 != r[c + 2])) {
                                ++r[c];
                                ++r[c + 1];
                                ++r[c + 2];

                            }
                        if (getIntegers().contains(2) && (c < (r.length - 1)))
                            if ((-1 != r[c]) && (-1 != r[c + 1])) {
                                ++r[c];
                                ++r[c + 1];
                            }
                    }
                }

                for (int c = 0; c < board[0].length; c++) {
                    for (int r = 0; r < board.length; r++) {
                        if (getIntegers().contains(5) && (r < (board.length - 4)))
                            if ((-1 != board[r][c]) && (-1 != board[r + 1][c]) && (-1 != board[r + 2][c]) && (-1 != board[r + 3][c]) && (-1 != board[r + 4][c])) {
                                ++board[r][c];
                                ++board[r + 1][c];
                                ++board[r + 2][c];
                                ++board[r + 3][c];
                                ++board[r + 4][c];
                            }

                        if (getIntegers().contains(4) && (r < (board.length - 3)))
                            if ((-1 != board[r][c]) && (-1 != board[r + 1][c]) && (-1 != board[r + 2][c]) && (-1 != board[r + 3][c])) {
                                ++board[r][c];
                                ++board[r + 1][c];
                                ++board[r + 2][c];
                                ++board[r + 3][c];
                            }
                        if (getIntegers().contains(3) && (r < (board.length - 2)))
                            if ((-1 != board[r][c]) && (-1 != board[r + 1][c]) && (-1 != board[r + 2][c])) {

                                ++board[r][c];
                                ++board[r + 1][c];
                                ++board[r + 2][c];


                            }
                        if (getIntegers().contains(3) && (2 == Collections.frequency(getIntegers(), 3)) && (r < (board.length - 2)))
                            if ((-1 != board[r][c]) && (-1 != board[r + 1][c]) && (-1 != board[r + 2][c])) {

                                ++board[r][c];
                                ++board[r + 1][c];
                                ++board[r + 2][c];


                            }
                        if (getIntegers().contains(2) && (r < (board.length - 1)))
                            if ((-1 != board[r][c]) && (-1 != board[r + 1][c])) {
                                ++board[r][c];
                                ++board[r + 1][c];
                            }
                    }
                }
            } else {
                System.out.println("Hitting");

                for (final Location hit : getHits()) {
                    final int r = hit.getRow();
                    final int c = hit.getCol();
                    
                    if (integers.contains(2))
                        compute2(r, c);
                    if (integers.contains(3))
                        compute3(r, c);
                    if (integers.contains(4))
                        compute4(r, c);
                    if (integers.contains(5))
                        compute5(r, c);
                }

                for (final Location hit : getHits()) {
                    board[hit.getRow()][hit.getCol()] = 0;
                }

            }

            return findLargest();
        }


        void compute5(int r, int c) {

            for (int i = r - 4; i <= r && i + 4 < board.length; i++)
                if (i >= 0) {
                    if ((-1 != board[i][c]) && (-1 != board[i + 1][c]) && (-1 != board[i + 2][c]) && (-1 != board[i + 3][c]) && (-1 != board[i + 4][c])) {
                        ++board[i][c];
                        ++board[i + 1][c];
                        ++board[i + 2][c];
                        ++board[i + 3][c];
                        ++board[i + 4][c];
                    }
                }
            for (int i = c - 4; i <= c && i + 4 < board[0].length; i++)
                if (i >= 0) {
                    if ((-1 != board[r][i]) && (-1 != board[r][i + 1]) && (-1 != board[r][i + 2]) && (-1 != board[r][i + 3]) && (-1 != board[r][i + 4])) {
                        ++board[r][i];
                        ++board[r][i + 1];
                        ++board[r][i + 2];
                        ++board[r][i + 3];
                        ++board[r][i + 4];
                    }
                }
        }


        void compute4(int r, int c) {
            for (int i = r - 3; i <= r && i + 3 < board.length; i++)
                if (i >= 0) {
                    if ((-1 != board[i][c]) && (-1 != board[i + 1][c]) && (-1 != board[i + 2][c]) && (-1 != board[i + 3][c])) {
                        ++board[i][c];
                        ++board[i + 1][c];
                        ++board[i + 2][c];
                        ++board[i + 3][c];
                    }
                }
            for (int i = c - 3; i <= c && i + 3 < board[0].length; i++)
                if (i >= 0) {
                    if ((-1 != board[r][i]) && (-1 != board[r][i + 1]) && (-1 != board[r][i + 2]) && (-1 != board[r][i + 3])) {
                        ++board[r][i];
                        ++board[r][i + 1];
                        ++board[r][i + 2];
                        ++board[r][i + 3];
                    }
                }
        }

        void compute3(int r, int c) {
            final int inc = Collections.frequency(getIntegers(), 3);

            for (int i = r - 2; i <= r && i + 2 < board.length; i++)
                if (i >= 0) {
                    if ((-1 != board[i][c]) && (-1 != board[i + 1][c]) && (-1 != board[i + 2][c])) {
                        board[i][c] += inc;
                        board[i + 1][c] += inc;
                        board[i + 2][c] += inc;
                    }
                }
            for (int i = c - 2; i <= c && i + 2 < board[0].length; i++)
                if (i >= 0) {
                    if ((-1 != board[r][i]) && (-1 != board[r][i + 1]) && (-1 != board[r][i + 2])) {
                        board[r][i] += inc;
                        board[r][i + 1] += inc;
                        board[r][i + 2] += inc;
                    }
                }
        }

        void compute2(int r, int c) {
            for (int i = r - 1; i <= r && i + 1 < board.length; i++)
                if (i >= 0) {
                    if ((-1 != board[i][c]) && (-1 != board[i + 1][c])) {
                        ++board[i][c];
                        ++board[i + 1][c];
                    }
                }
            for (int i = c - 1; i <= c && i + 1 < board[0].length; i++)
                if (i >= 0) {
                    if ((-1 != board[r][i]) && (-1 != board[r][i + 1])) {
                        ++board[r][i];
                        ++board[r][i + 1];
                    }
                }
        }

        //chooses the larges probability
        public Location findLargest() {
            int max = -1;

            final LinkedList<Location> loc = new LinkedList<>();

            for (int r = 0; r < board.length; r++)
                for (int c = 0; c < board[0].length; c++)
                    if (board[r][c] > max) {
                        loc.clear();
                        max = board[r][c];
                        loc.add(new Location(r, c));
                    } else if (board[r][c] == max)
                        loc.add(new Location(r, c));

            Collections.shuffle(loc);

            return loc.pop();
        }

        @Override
        public String toString() {
            return "Board{" +
                    "board=" + Arrays.toString(board) +
                    ", hits=" + getHits() +
                    ", misses=" + misses +
                    ", integers=" + getIntegers() +
                    '}';
        }

    }
}

