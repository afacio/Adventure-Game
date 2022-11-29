package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

    GamePanel gamePanel;
    int value = 1;

    public OBJ_Coin_Bronze(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
       
        name = "Bronze Coin";
        type = PICKUP_ONLY_TYPE;
        down1 = setup("/objects/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);

    }

    @Override
    public void use(Entity entity) {
        gamePanel.playSoundEfect(1);
        gamePanel.ui.addMessage("Coin +" + value);
        gamePanel.player.coin += value;
    }
    
}
