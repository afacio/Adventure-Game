package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Green_Book extends Entity {

    public OBJ_Green_Book(GamePanel gamePanel) {
        super(gamePanel);

        name = "Wild spell";
        type = MAGIC_WEAPON_TYPE;
        attackValue = 2;
        mana = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        down1 = setup("/objects/green_book", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nAttack power" + attackValue + "\nMana cost" + mana;
    }
}
