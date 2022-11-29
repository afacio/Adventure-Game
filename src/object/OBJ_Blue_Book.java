package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Blue_Book extends Entity {

    public OBJ_Blue_Book(GamePanel gamePanel) {
        super(gamePanel);

        name = "Wather spell";
        type = MAGIC_WEAPON_TYPE;
        attackValue = 2;
        mana = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        down1 = setup("/objects/blue_book", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nAttack power" + attackValue + "\nMana cost" + mana;
    }
}
