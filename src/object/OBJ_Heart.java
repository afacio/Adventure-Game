package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Heart(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Heart";
        
        try {
            image1 = ImageIO.read(new File("src/res/objects/hearts/heart_1.png"));
            image1 = uTool.scaleImage(image1, gamePanel.tileSize, gamePanel.tileSize);
            image2 = ImageIO.read(new File("src/res/objects/hearts/heart_2.png"));
            image2 = uTool.scaleImage(image2, gamePanel.tileSize, gamePanel.tileSize);
            image3 = ImageIO.read(new File("src/res/objects/hearts/heart_3.png"));
            image3 = uTool.scaleImage(image3, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
