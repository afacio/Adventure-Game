package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    int value = 2;
    GamePanel gamePanel;


    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Heart";
        type = PICKUP_ONLY_TYPE;
        down1 = setup("/objects//hearts/heart_1", gamePanel.tileSize, gamePanel.tileSize);
        image1 = setup("/objects//hearts/heart_1", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects//hearts/heart_2", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("/objects//hearts/heart_3", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEfect(2);
        gamePanel.ui.addMessage("Life +" + value);
        entity.health += value;
        return true;
    }
    
}
