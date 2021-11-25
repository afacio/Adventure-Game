package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.File;

public class OBJ_Door extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Door(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Door";
        try {
            image = ImageIO.read(new File("src/res/objects/door.png"));
            uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
