package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {

    GamePanel gamePanel;

    public OBJ_Tent(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Tent";
        type = CONSUMABLE_TYPE;
        down1 = setup("/objects/tent", gamePanel.tileSize, gamePanel.tileSize);
        description = "[Tent]\nYou can sleep until\nnext morning.";
        itemPrice = 300;

    }

    @Override
    public boolean use(Entity entity) {
        if(gamePanel.environmentManager.lighting.dayState != gamePanel.environmentManager.lighting.DAY) {
            gamePanel.gameState = gamePanel.SLEEP_STATE;
            gamePanel.playSoundEfect(18);
            gamePanel.player.health = gamePanel.player.maxHealth;
            gamePanel.player.mana = gamePanel.player.maxMana;
            gamePanel.player.getSleepingImage(down1);
        } else {
            gamePanel.playSoundEfect(17);
            gamePanel.gameState = gamePanel.DIALOGUE_STATE;
            gamePanel.ui.currentDialogue = "You can't sleep during the day";
        }
        return false;
    }
}
