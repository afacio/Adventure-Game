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
            image1 = ImageIO.read(new File("src/res/objects/door.png"));
            image1 = uTool.scaleImage(image1, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
