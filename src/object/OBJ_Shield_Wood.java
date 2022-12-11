package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

    public OBJ_Shield_Wood(GamePanel gamePanel) {
        super(gamePanel);
        
        name = "Wood Shield";
        type = SHIELD_TYPE;
        down1 = setup("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nDefense: " + defenseValue + "\nEffects: No special effects";
        itemPrice = 75;
    }
    
}
