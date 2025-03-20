//Import statement for swing.
import javax.swing.*;

/**
 * Main class designed to create and start game window with GamePanel as a parameter.
 */
public class Main {
    public static void main(String[] args) {
        //Creates new game window.
        JFrame window = new JFrame();
        //Determines what happens when window is closed.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Ensures that window cannot be resized.
        window.setResizable(false);
        //Sets the title of the game window.
        window.setTitle("2D Adventure");

        //Creates a new game panel.
        GamePanel gamePanel = new GamePanel();
        //Adds game panel to the window.
        window.add(gamePanel);

        //Causes window to be sized to the preferred size.
        window.pack();

        //Sets the location of the window to center.
        window.setLocationRelativeTo(null);
        //Ensures the window is visible when run.
        window.setVisible(true);

        //Sets up the game.
        gamePanel.setUpGame();

        //Starts a game thread using current game panel.
        gamePanel.startGameThread();
    }
}
