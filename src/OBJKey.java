import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJKey extends SuperObject {
    public OBJKey() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Objects/key.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
