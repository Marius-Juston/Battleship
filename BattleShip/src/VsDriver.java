import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

final class VsDriver extends Canvas {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = (WIDTH / 12) * 9;
    private static final long serialVersionUID = 7932485256572298382L;
    private static final String ARIAL = "Arial";
    private static final String WINS = "Wins!";
    private static final String VS = " -vs- ";
    private static final String BLUE = "Blue";
    private static final String RED = "Red";
    private final Battleship battleship;
    private final int x;
    private final int y;
    private final int squareSize;
    private final int len;
    private final Player p1;
    private final Player p2;
    private BufferedImage logo;
    private BufferedImage end;
    private BufferedImage vs;

    private VsDriver() {
        battleship = new Battleship();
        battleship.addPlayer(new ComputerPlayer(RED));        // construct player1's AI here
        battleship.addPlayer(new ExpertComputerPlayer(BLUE));       // construct player2's AI here

        x = 90;
        y = 200;
        squareSize = 36;
        len = (squareSize * 10) - 1;
        p1 = battleship.getPlayer(0);
        p2 = battleship.getPlayer(1);

        // Get Battleship Logo
        try {
            logo = ImageIO.read(new File("src/Logo.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // Get VS Screen
        try {
            vs = ImageIO.read(new File("src/VS.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // Get End Screen
        try {
            end = ImageIO.read(new File("src/End.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final String title = p1.getName() + VS + p2.getName();
        new Window(WIDTH, HEIGHT, title, this);

        try {
            Thread.sleep(100L);
        } catch (final InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        render();
        move();
    }

    public static void main(final String... args) {

        do {
            final VsDriver vsDriver = new VsDriver();
            vsDriver.setEnabled(false);
        } while (true);
    }

    private static void renderGuesses(final Graphics g, final Player player, final int x, final int y, final int s) {
        final int[][] guessBoard = player.getGuessBoard();
        for (int r = 0; r < guessBoard.length; r++)
            for (int c = 0; c < guessBoard[r].length; c++)
                if (0 < guessBoard[r][c])    // hit
                {
                    g.setColor(Color.RED);
                    g.fillOval((c * s) + x + (int) ((double) s * 0.35), (r * s) + y + (int) ((double) s * 0.35), (int) ((double) s * 0.33), (int) ((double) s * 0.33));
                } else if (0 > guessBoard[r][c])   // miss
                {
                    g.setColor(Color.WHITE);
                    g.fillOval((c * s) + x + (int) ((double) s * 0.35), (r * s) + y + (int) ((double) s * 0.35), (int) ((double) s * 0.33), (int) ((double) s * 0.33));
                }
    }

    private void move() {
        boolean p1Turn = true;
        while (!battleship.gameOver()) {
            if (p1Turn)
                p1.attack(p2, new Location(0, 0));
            else
                p2.attack(p1, new Location(0, 0));

            p1Turn = !p1Turn;

            battleship.upkeep();
            render();

            try {
                Thread.sleep(250L);                      // milliseconds; adjust to change speed
            } catch (final InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void render() {
        final Graphics g = getGraphics();

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (battleship.gameOver()) {
            // End Screen
            g.drawImage(end, 0, 0, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font(ARIAL, Font.BOLD, squareSize));
            final String winner = battleship.getWinner().getName();
            g.drawString(winner, (WIDTH / 2) - ((winner.length() * squareSize) / 4), HEIGHT / 4);
            g.drawString(WINS, (WIDTH / 2) - ((WINS.length() * squareSize) / 4), (HEIGHT / 4) + squareSize);
        } else {
            // Boards
            renderGrid(g, x, y, squareSize);
            renderGuesses(g, p1, x, y, squareSize);
            renderGrid(g, 570, y, squareSize);
            renderGuesses(g, p2, 570, y, squareSize);

            // Names
            g.setColor(Color.WHITE);
            g.drawString(p1.getName(), x, y + 25 + len);
            g.drawString(p2.getName(), 570, y + 25 + len);
        }

        // Battleship Logo
        g.drawImage(logo, (WIDTH / 2) - 246, 10, this);

        // VS
        g.drawImage(vs, (WIDTH / 2) + 180, 70, this);

        g.dispose();
    }

    private void renderGrid(final Graphics g, final int x, final int y, final int s) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(ARIAL, Font.BOLD, s / 2));

        // Row Lines
        for (int i = 0; 11 > i; i++)
            g.drawLine(x, y + (i * s), x + len, y + (i * s));

        // Column Lines
        for (int i = 0; 11 > i; i++)
            g.drawLine(x + (i * s), y, x + (i * s), y + len);

        // Row Markers
        for (int i = 0; 10 > i; i++)    //marks row coordinates on side
            g.drawString(String.valueOf(i), x - (int) ((double) s * 0.43), y + (int) ((double) s * 0.67) + (s * i));

        // Column Markers
        for (int i = 0; 10 > i; i++)    //marks column coordinates on top
            g.drawString(String.valueOf(i), x + (int) ((double) s * 0.4) + (s * i), y - (int) ((double) s * 0.2));
    }

    @Override
    public String toString() {
        return "VsDriver{" +
                "battleship=" + battleship +
                ", x=" + x +
                ", y=" + y +
                ", squareSize=" + squareSize +
                ", len=" + len +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", logo=" + logo +
                ", end=" + end +
                ", vs=" + vs +
                '}';
    }
}
