import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    int standCounter;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("girl_up_1");
        up2 = setup("girl_up_2");
        down1 = setup("girl_down_1");
        down2 = setup("girl_down_2");
        left1 = setup("girl_left_1");
        left2 = setup("girl_left_2");
        right1 = setup("girl_right_1");
        right2 = setup("girl_right_2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Player/" + imageName + ".png"));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {

        if(keyHandler.upPressed || keyHandler.downPressed
                || keyHandler.leftPressed || keyHandler.rightPressed) {
            //Moves player up.
            if(keyHandler.upPressed) {
                direction = "up";
            }
            //Moves player down.
            else if(keyHandler.downPressed) {
                direction = "down";
            }
            //Moves player to the left.
            else if(keyHandler.leftPressed) {
                direction = "left";
            }
            //Moves player to the right.
            else if(keyHandler.rightPressed) {
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //IF COLLISION IS FALSE, PLAYER CAN'T MOVE.
            if(!collisionOn) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 14) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            standCounter++;
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    public void pickUpObject(int index) {
        if(index != 999) {
            String objectName = gp.obj[index].name;
            switch(objectName) {
                case "Key":
                    gp.playSoundEffect(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    gp.playSoundEffect(3);
                    if (hasKey > 0) {
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    }
                    else {
                        gp.ui.showMessage("You need a key to get through here.");
                }
                    break;
                case "Boots":
                    gp.playSoundEffect(2);
                    speed += 2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("Speed up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSoundEffect(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
//        //Sets color of component to white.
//        g2.setColor(Color.white);
//        //Fills rectangle with dimensions of the player position and tile size.
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }
}
