package object;

import entity.Entity;
import main.GamePanel;
import java.awt.Rectangle;
import java.util.ArrayList;

public class OBJ_Chest extends Entity {

    GamePanel gamePanel;
    boolean opened = false;

    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        // ArrayList<Entity> inv
        // if(inv.size() <= inventoryMaxSize) {
        //     inventory = inv;
        // }
        inventory.add(new OBJ_Key(gamePanel));
        inventory.add(new OBJ_Potion_Red(gamePanel));

        name = "Chest";
        type = OBSTACLE_TYPE;
        image1 = setup("/objects/chest", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/chest_opened", gamePanel.tileSize, gamePanel.tileSize);

        down1 = image1;
        collision = true;

        solidArea = new Rectangle(4,16, 40, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        gamePanel.gameState = gamePanel.STORAGE_STATE;
        gamePanel.ui.entity = this;
        gamePanel.ui.subState = 1;
        down1 = image2;
    }
}
