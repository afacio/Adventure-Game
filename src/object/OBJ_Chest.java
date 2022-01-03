package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);

        name = "Chest";
        down1 = setup("/objects/chest");
    }
}
