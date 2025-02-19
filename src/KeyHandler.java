//Import statements for KeyEvent and KeyListener which allow for keyboard interaction.
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyHandler class that implements the KeyListener interface. Consists of methods related to
 * keyboard interaction, including methods that determine whether W, A, S, or D were pressed
 * or released while game is running.
 */
public class KeyHandler implements KeyListener {

    //Variable declarations for when W, A, S, and D are pressed.
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    //DEBUG
    boolean checkDrawTime = false;

    //CAMERA CHANGE - EXTRA ASSIGNMENT
    boolean changeCamera = false;

    /**
     * Method that determines which key has been typed.
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Method that determines which key has been pressed by mapping the key code to an integer.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //Gets key code from KeyEvent and saves it in an int variable.
        int code = e.getKeyCode();

        //Checks to see if the key pressed was W.
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        //Checks to see if the key pressed was A.
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        //Checks to see if the key pressed was S.
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        //Checks to see if the key pressed was D.
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if(code == KeyEvent.VK_T) {
            if(!checkDrawTime) {
                checkDrawTime = true;
            }
            else if(checkDrawTime) {
                checkDrawTime = false;
            }
        }

        if(code == KeyEvent.VK_Q) {
            if(!changeCamera) {
                changeCamera = true;
            }
            else if(changeCamera) {
                changeCamera = false;
            }
        }
    }

    /**
     * Method that determines which key has been released by mapping the key code to an integer.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //Gets key code from KeyEvent and saves it in an int variable.
        int code = e.getKeyCode();

        //Checks to see if the key released was W.
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        //Checks to see if the key released was A.
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        //Checks to see if the key released was S.
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        //Checks to see if the key released was D.
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
