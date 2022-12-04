package object;

import entity.Entity;
import main.GamePanel;
import java.awt.Rectangle;;

public class OBJ_Door extends Entity {

    GamePanel gamePanel;

    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Door";
        type = OBSTACLE_TYPE;
        down1 = setup("/objects/door", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        solidArea = new Rectangle(0,5, gamePanel.tileSize, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact() {
        gamePanel.gameState = gamePanel.DIALOGUE_STATE;
        gamePanel.ui.currentDialogue = "You need a key to open this door.";
    }
}
