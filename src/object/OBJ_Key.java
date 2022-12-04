package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity {

    GamePanel gamePanel;

    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Key";
        type = CONSUMABLE_TYPE;
        down1 = setup("/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nIt open the door";
        itemPrice = 50;
        stackable = true;
    }

    @Override
    public boolean use(Entity entity) {
        gamePanel.gameState = gamePanel.DIALOGUE_STATE;

        int objIndex = getDetected(entity, gamePanel.obj, "Door");

        if(objIndex != 999) {
            gamePanel.ui.currentDialogue = "Key was use to open the lock.";
            gamePanel.playSoundEfect(3);
            gamePanel.obj[gamePanel.currentMap][objIndex] = null;
            return true;
        } else {
            gamePanel.ui.currentDialogue = "No lock detected";
            return false;
        }
    }
}
