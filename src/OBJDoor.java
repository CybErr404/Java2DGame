import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJDoor extends SuperObject {
    public OBJDoor() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Objects/door.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
