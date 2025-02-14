import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJChest extends SuperObject {
    GamePanel gp;
    public OBJChest(GamePanel gp) {
        this.gp = gp;
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Objects/chest.png"));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
