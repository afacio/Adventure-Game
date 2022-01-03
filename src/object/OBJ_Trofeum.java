package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Trofeum extends Entity {

    public OBJ_Trofeum(GamePanel gamePanel) {
        super(gamePanel);

        name = "Trofeum";
        down1 = setup("/objects/trofeum");
    }
}
