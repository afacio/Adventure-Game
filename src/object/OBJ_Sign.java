package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sign extends Entity {

    GamePanel gamePanel;
    String information = "";

    public OBJ_Sign(GamePanel gamePanel, String information) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        this.information = information;

        name = "Sign";
        type = OBSTACLE_TYPE;
        down1 = setup("/objects/sign", gamePanel.tileSize, gamePanel.tileSize);
        collision = false;
    }

    @Override
    public void interact() {
        gamePanel.gameState = gamePanel.DIALOGUE_STATE;
        gamePanel.ui.currentDialogue = information;
    }
}
