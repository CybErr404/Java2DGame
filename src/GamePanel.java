//Import statements for awt and swing.
import javax.swing.JPanel;
import java.awt.*;

/**
 * Class designed to run the game itself.
 */
public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    //Original tile size of a typical sprite.
    final int originalTileSize = 16; //16x16 tile
    //Scale to enlarge sprite to a seemingly larger state.
    final int scale = 3;
    //Calculates tile size to scale 16x16 sprite to larger size.
    final int tileSize = originalTileSize * scale; //48x48 tile
    //Max screen columns.
    final int maxScreenCol = 16;
    //Max screen rows.
    final int maxScreenRow = 12;
    //Calculates screen width by multiplying tile size by max columns.
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    //Calculates screen height by multiplying tile size by max rows.
    final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //FPS
    //Sets FPS to 60.
    int FPS = 60;

    //Creates new KeyHandler object for key mapping.
    KeyHandler keyHandler = new KeyHandler();
    //Helps represent concept of time - keeps game running in real time until closed.
    Thread gameThread;

    //PLAYER POSITION
    //Set player's default position.
    int playerX = 100;
    int playerY = 100;
    //Sets player's default speed.
    int playerSpeed = 4;

    //Constructor that sets the main attributes of the game panel.
    public GamePanel() {
        //Sets size to the calculated width and height.
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        //Sets background color of game window to black.
        this.setBackground(Color.black);
        //Improves rendering performance.
        this.setDoubleBuffered(true);

        //Adds a key listener to the game panel so keyboard input is accepted.
        this.addKeyListener(keyHandler);
        //Sets focusability to true. Allows program to focus on keyboard input.
        this.setFocusable(true);
    }

    //Method that starts a specific game thread.
    public void startGameThread() {
        //Initializes a new game thread.
        gameThread = new Thread(this);
        //Starts game thread.
        gameThread.start();
    }

    //Game loop method. SLEEP VERSION.
//    @Override
//    public void run() {
//
//        double drawInterval = 1000000000/FPS; //0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while(gameThread != null) {
//            //Example code commented out that shows in console game is running.
//            //System.out.println("The game loop is running.");
//
//
//            //1 UPDATE: Update information such as character positions.
//            update();
//
//            //2 DRAW: Draw the screen with the updated information.
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime / 1000000;
//
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    /**
     * Game loop run method using delta/accumulator game loop setup.
     * This method is used over the sleep method due to the possibility of inaccuracies with timing.
     */
    public void run() {
        //0.0166 seconds.
        double drawInterval = 1000000000/FPS;
        //Creates variable named delta and initializes it to 0.
        double delta = 0;
        //Calculates last time.
        long lastTime = System.nanoTime();
        //Will store current time during game loop.
        long currentTime;
        //Sets timer to 0.
        long timer = 0;
        //Sets draw count to 0.
        int drawCount = 0;

        //Loop that checks to see if the game thread is null - if not, run everything inside.
        while(gameThread != null) {
            //Check current time.
            currentTime = System.nanoTime();

            //Add past time divided by drawInterval to delta.
            delta += (currentTime - lastTime) / drawInterval;
            //Add past time to timer.
            timer += (currentTime - lastTime);
            //Sets last time to the current time.
            lastTime = currentTime;

            //Checks to see if delta is greater than 1.
            if(delta >= 1) {
                //Update and repaint.
                update();
                repaint();
                //Subtract 1 from delta.
                delta--;
                //Increase draw count.
                drawCount++;
            }

            //Calculates the current FPS and prints it into the console.
            if(timer >= 1000000000) {
                //Prints current FPS.
                System.out.println("FPS: " + drawCount);
                //Sets draw count to 0.
                drawCount = 0;
                //Sets timer to 0.
                timer = 0;
            }
        }
    }

    /**
     * Update method that updates the contents on the screen 60 times per second (or however
     * many frames per second the game is set to when running).
     */
    public void update() {
        //Moves player up.
        if(keyHandler.upPressed) {
            playerY -= playerSpeed;
        }
        //Moves player down.
        else if(keyHandler.downPressed) {
            playerY += playerSpeed;
        }
        //Moves player to the left.
        else if(keyHandler.leftPressed) {
            playerX -= playerSpeed;
        }
        //Moves player to the right.
        else if(keyHandler.rightPressed) {
            playerX += playerSpeed;
        }
    }

    /**
     * Method that assists in drawing components on a screen.
     * @param g - type Graphics which helps with displaying things on the screen.
     */
    public void paintComponent(Graphics g) {
        //Calls parent class method and passes g as a parameter.
        super.paintComponent(g);

        //Graphics2D extends Graphics - creates a Graphics2D object by casting g to a Graphics2D.
        Graphics2D g2 = (Graphics2D) g;
        //Sets color of component to white.
        g2.setColor(Color.white);
        //Fills rectangle with dimensions of the player position and tile size.
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        //Ensures that content is removed when program execution finishes. Saves memory.
        g2.dispose();
    }
}
