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
        itemPrice = 25;
    }

    public void use(Entity entity) {
        gamePanel.player.health += healingValue;
        gamePanel.playSoundEfect(2);
    }
}
