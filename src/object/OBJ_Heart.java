package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);

        name = "Heart";
        image1 = setup("/objects//hearts/heart_1", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects//hearts/heart_2", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("/objects//hearts/heart_3", gamePanel.tileSize, gamePanel.tileSize);
    }
    
}
