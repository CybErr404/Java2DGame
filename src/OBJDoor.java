import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJDoor extends SuperObject {
    GamePanel gp;
    public OBJDoor(GamePanel gp) {
        this.gp = gp;
        name = "Door";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Objects/door.png"));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
