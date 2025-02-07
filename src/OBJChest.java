import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJChest extends SuperObject {
    public OBJChest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Objects/chest.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
