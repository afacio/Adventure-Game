package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.File;

public class OBJ_Boots extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Boots(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Boots";
        try {
            image1 = ImageIO.read(new File("src/res/objects/boots.png"));
            image1 = uTool.scaleImage(image1, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
