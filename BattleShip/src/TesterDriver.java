/**
 * Created by mariu on 13/03/2017.
 */
final class TesterDriver {
    private static final String HUMAN = "human";
    private static final String COMP = "comp";

    public static void main(final String... args) {
        final Battleship battleship = new Battleship();

        battleship.addPlayer(new ComputerPlayer(COMP));
        battleship.addPlayer(new HumanPlayer(HUMAN));

        System.out.println((new Submarine(new Location(1, 1)).equals(new AircraftCarrier(new Location(1, 1)))));
    }
}
