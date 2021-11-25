package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.File;

public class OBJ_Trofeum extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Trofeum(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Trofeum";
        try {
            image = ImageIO.read(new File("src/res/objects/trofeum.png"));
            uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
