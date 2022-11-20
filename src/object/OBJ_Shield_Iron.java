package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Iron extends Entity{

    public OBJ_Shield_Iron(GamePanel gamePanel) {
        super(gamePanel);
        
        name = "Iron Shield";
        type = SHIELD_TYPE;
        down1 = setup("/objects/shield_blue", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nDefense: " + defenseValue + "\nEfects: No special efects";
    }
    
}
