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

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    //Sets FPS to 60.
    int FPS = 60;

    TileManager tileManager = new TileManager(this);

    //Creates new KeyHandler object for key mapping.
    KeyHandler keyHandler = new KeyHandler();
    //Helps represent concept of time - keeps game running in real time until closed.
    Sound soundEffects = new Sound();
    Sound music = new Sound();

    public UI ui = new UI(this);
    Thread gameThread;
    //Player object from Player class.
    Player player = new Player(this, keyHandler);

    Font arial20 = new Font("Arial", Font.PLAIN, 20);

    public SuperObject obj[] = new SuperObject[10];
    public AssetSetter assetSetter = new AssetSetter(this);

    CollisionChecker collisionChecker = new CollisionChecker(this);

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

    public void setUpGame() {
        assetSetter.setObject();
        playMusic(0);
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
                //System.out.println("FPS: " + drawCount);
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
        player.update();
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

        //DEBUG
        long drawStart = 0;
        if(keyHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }


        //TILE
        tileManager.draw(g2);

        //OBJECT
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        //PLAYER
        player.draw(g2);

        //UI
        ui.draw(g2);

        //DEBUG
        if(keyHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: "  + passed, 10, 400);
            //System.out.println("Draw Time: " + passed);
        }

        //CHANGE CAMERA
        if(keyHandler.changeCamera) {
            g2.setFont(arial20);
            g2.setColor(Color.white);
            g2.drawString("Camera Mode: Still", 10, 570);
        }
        else if(!keyHandler.changeCamera) {
            g2.setFont(arial20);
            g2.setColor(Color.white);
            g2.drawString("Camera Mode: Follow Player", 10, 570);
        }

        //Ensures that content is removed when program execution finishes. Saves memory.
        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffects.setFile(i);
        soundEffects.play();
    }
}
