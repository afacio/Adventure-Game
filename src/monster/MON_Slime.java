package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Slime extends Entity {

    public MON_Slime(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        name = "Slime";
        speed = 1;
        maxHealth = 3;
        health = maxHealth;

        solidArea.x = 2;
        solidArea.y = 16;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    private void getImage() {
        up1 = setup("/monster/slime/up_1");
        up2 = setup("/monster/slime/up_2");
        up3 = setup("/monster/slime/up_3");
        down1 = setup("/monster/slime/down_1");
        down2 = setup("/monster/slime/down_2");
        down3 = setup("/monster/slime/down_3");
        left1 = setup("/monster/slime/left_1");
        left2 = setup("/monster/slime/left_2");
        left3 = setup("/monster/slime/left_3");
        right1 = setup("/monster/slime/right_1");
        right2 = setup("/monster/slime/right_2");
        right3 = setup("/monster/slime/right_3");
    }

    public void setAction() {
        Random random = new Random();

        actionLockCounter++;

        if (actionLockCounter == 120) {
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 55 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

}
