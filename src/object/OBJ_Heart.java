package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);

        name = "Heart";
        image1 = setup("/objects//hearts/heart_1");
        image2 = setup("/objects//hearts/heart_2");
        image3 = setup("/objects//hearts/heart_3");
    }
    
}
