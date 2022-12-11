package object;

import entity.Entity;
import main.GamePanel;
import java.awt.Rectangle;

public class OBJ_Coin_Gold extends Entity {

    GamePanel gamePanel;
    int value = 10;

    public OBJ_Coin_Gold(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
       
        name = "Gold Coin";
        type = PICKUP_ONLY_TYPE;
        down1 = setup("/objects/coin_gold", gamePanel.tileSize, gamePanel.tileSize);

        solidArea = new Rectangle(10,9, 22, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    @Override
    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(1);
        gamePanel.ui.addMessage("Coin +" + value);
        gamePanel.player.coin += value;
        return true;
    }
    
}
