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
        //Sets focusability to true.
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
     */
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {
        if(keyHandler.upPressed) {
            playerY -= playerSpeed;
        }
        else if(keyHandler.downPressed) {
            playerY += playerSpeed;
        }
        else if(keyHandler.leftPressed) {
            playerX -= playerSpeed;
        }
        else if(keyHandler.rightPressed) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
