package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gamePanel) {
        super(gamePanel);
       
        name = "Basic Sword";
        type = MELEE_WEAPON_TYPE;
        down1 = setup("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAttack: " + attackValue + "\nEffects: No special effects";
        itemPrice = 50;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 20;
    }
    
}
