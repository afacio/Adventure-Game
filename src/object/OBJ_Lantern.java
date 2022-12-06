package object;

import entity.Entity;
import main.GamePanel;
import java.awt.Rectangle;

public class OBJ_Lantern extends Entity {
    
    GamePanel gamePanel;

    public OBJ_Lantern(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = LIGHT_TYPE;
        name="Lantern";
        down1 = setup("/objects/lantern", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nIlluminates your\nsurroundings.";
        itemPrice = 200;
        lightRadius = 350;

        solidArea = new Rectangle(7,5, 31, 42);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    
}
