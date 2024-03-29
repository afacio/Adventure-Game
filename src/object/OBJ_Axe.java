package object;

import entity.Entity;
import main.GamePanel;
import java.awt.Rectangle;

public class OBJ_Axe extends Entity {


    public OBJ_Axe(GamePanel gamePanel) {
        super(gamePanel);

        name = "Axe";
        type = AXE_TYPE;
        down1 = setup("/objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        knockBackPower = 10;
        description = "[" + name + "]\nAttack: " + attackValue + "\nEffects: No special effects";

        itemPrice = 105;

        solidArea = new Rectangle(17,4, 22, 42);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        motion1_duration = 20;
        motion2_duration = 40;
    }
}
