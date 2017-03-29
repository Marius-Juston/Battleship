import javax.swing.*;
import java.awt.*;

class Window extends Canvas {
    private static final long serialVersionUID = -116024577971630125L;

    Window(final int width, final int height, final String title, final Canvas canvas) {
        final JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(canvas);
        frame.setVisible(true);
    }
}
