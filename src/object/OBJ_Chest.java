package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.File;

public class OBJ_Chest extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Chest(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Chest";
        try {
            image1 = ImageIO.read(new File("src/res/objects/chest.png"));
            image1 = uTool.scaleImage(image1, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
