package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {


    public OBJ_Axe(GamePanel gamePanel) {
        super(gamePanel);

        name = "Axe";
        type = AXE_TYPE;
        down1 = setup("/objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nAttack: " + attackValue + "\nEfects: No special efects";
    }
}
