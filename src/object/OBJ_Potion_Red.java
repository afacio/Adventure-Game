package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Potion_Red extends Entity {

    GamePanel gamePanel;
    int healingValue = 5;

    public OBJ_Potion_Red(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Red potion";
        type = CONSUMABLE_TYPE;
        down1 = setup("/objects/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nHeals your life by " + healingValue;
    }

    public void use(Entity entity) {
        gamePanel.player.health += healingValue;
        if(gamePanel.player.health > gamePanel.player.maxHealth) {
            gamePanel.player.health = gamePanel.player.maxHealth;
        }
        gamePanel.playSoundEfect(2);
    }
}
