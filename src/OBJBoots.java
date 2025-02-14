import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJBoots extends SuperObject {
    GamePanel gp;
    public OBJBoots(GamePanel gp) {
        this.gp = gp;
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Objects/boots.png"));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
