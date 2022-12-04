package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana_Crystal extends Entity {

    int value = 1;
    GamePanel gamePanel;


    public OBJ_Mana_Crystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Mana Crystal";
        type = PICKUP_ONLY_TYPE;
        down1 = setup("/objects/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEfect(2);
        gamePanel.ui.addMessage("Mana +" + value);
        entity.mana += value;
        return true;
    }
    
}
