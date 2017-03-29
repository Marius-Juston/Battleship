import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

final class BattleshipDriver extends Canvas implements MouseListener {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = (WIDTH / 12) * 9;
    private static final long serialVersionUID = 451895769629125349L;
    private static final String ARIAL = "Arial";
    private static final String WINS = "Wins!";
    private static final String ALPHA_BATTLESHIP = "AlphaBattleship";
    private static final String MR_HUBBARD = "Mr. Hubbard";
    private final Battleship battleship;
    private final int x;
    private final int y;
    private final int squareSize;
    private final int len;
    private final Player p1;
    private final Player p2;
    private BufferedImage logo;
    private BufferedImage end;

    private BattleshipDriver() {
        battleship = new Battleship();
        battleship.addPlayer(new HumanPlayer(MR_HUBBARD));
        battleship.addPlayer(new ComputerPlayer(ALPHA_BATTLESHIP));

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

        // Get End Screen
        try {
            end = ImageIO.read(new File("src/End.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        addMouseListener(this);

        new Window(WIDTH, HEIGHT, "Battleship", this);

        try {
            Thread.sleep(100L);
        } catch (final InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        render();
    }

    public static void main(final String... args) {
        new BattleshipDriver();
    }

    private static void renderGuesses(final Graphics g, final Player player, final int x, final int y, final int s) {
        final int[][] guessBoard = player.getGuessBoard();
        for (int r = 0; r < guessBoard.length; r++)
            for (int c = 0; c < guessBoard[r].length; c++)
                if (guessBoard[r][c] > 0)    // hit
                {
                    g.setColor(Color.RED);
                    g.fillOval((c * s) + x + (int) ((double) s * 0.35), (r * s) + y + (int) ((double) s * 0.35), (int) ((double) s * 0.33), (int) ((double) s * 0.33));
                } else if (guessBoard[r][c] < 0)   // miss
                {
                    g.setColor(Color.WHITE);
                    g.fillOval((c * s) + x + (int) ((double) s * 0.35), (r * s) + y + (int) ((double) s * 0.35), (int) ((double) s * 0.33), (int) ((double) s * 0.33));
                }
    }

    private void render() {
        final Graphics g = getGraphics();

        // Background
        g.setColor(Color.DARK_GRAY);
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

        g.dispose();
    }

    private void renderGrid(final Graphics g, final int x, final int y, final int s) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(ARIAL, Font.BOLD, s / 2));

        // Row Lines
        for (int i = 0; i < 11; i++)
            g.drawLine(x, y + (i * s), x + len, y + (i * s));

        // Column Lines
        for (int i = 0; i < 11; i++)
            g.drawLine(x + (i * s), y, x + (i * s), y + len);

        // Row Markers
        for (int i = 0; i < 10; i++)    //marks row coordinates on side
            g.drawString(String.valueOf(i), x - (int) ((double) s * 0.43), y + (int) ((double) s * 0.67) + (s * i));

        // Column Markers
        for (int i = 0; i < 10; i++)    //marks column coordinates on top
            g.drawString(String.valueOf(i), x + (int) ((double) s * 0.4) + (s * i), y - (int) ((double) s * 0.2));
    }

    @Override
    public final void mouseClicked(final MouseEvent e) {
        final int r = e.getY();
        final int c = e.getX();

        final int len = (squareSize * 10) - 1;
        if ((r > y) && (r < (y + len)) && (c > x) && (c < (x + len)))    // clicked on board
        {
            final int row = (r - y) / squareSize;
            final int col = (c - x) / squareSize;

            System.out.println(row + ", " + col);

            final Location loc = new Location(row, col);
            if (p1.getGuessBoard()[row][col] == 0) {
                p1.attack(p2, loc);
                p2.attack(p1, loc);
            }

            battleship.upkeep();
            render();
        }

        System.out.println(r + ", " + c);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public String toString() {
        return "BattleshipDriver{" +
                "battleship=" + battleship +
                ", x=" + x +
                ", y=" + y +
                ", squareSize=" + squareSize +
                ", len=" + len +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", logo=" + logo +
                ", end=" + end +
                '}';
    }
}
