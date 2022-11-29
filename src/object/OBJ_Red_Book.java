package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Red_Book extends Entity {

    public OBJ_Red_Book(GamePanel gamePanel) {
        super(gamePanel);

        name = "Fireball spell";
        type = MAGIC_WEAPON_TYPE;
        attackValue = 2;
        mana = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        down1 = setup("/objects/red_book", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nAttack power" + attackValue + "\nMana cost" + mana;
    }
}
