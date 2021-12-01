package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Stranger extends Entity {

    public NPC_Stranger(GamePanel gamePanel, Long id) {
        super(gamePanel, id);
        
        direction = "down";
        speed = 1;

        getImage();
    }

    public void getImage() {
        up1 = setup("/npc/stranger/stranger_up_1");
        up2 = setup("/npc/stranger/stranger_up_2");            
        up3 = setup("/npc/stranger/stranger_up_3");            
        down1 = setup("/npc/stranger/stranger_down_1");
        down2 = setup("/npc/stranger/stranger_down_2");          
        down3 = setup("/npc/stranger/stranger_down_3");          
        left1 = setup("/npc/stranger/stranger_left_1");          
        left2 = setup("/npc/stranger/stranger_left_2");            
        left3 = setup("/npc/stranger/stranger_left_3");            
        right1 = setup("/npc/stranger/stranger_right_1");
        right2 = setup("/npc/stranger/stranger_right_2");
        right3 = setup("/npc/stranger/stranger_right_3");
    }

    @Override
    public void setAction() {
        Random random = new Random();

        actionLockCounter ++;

        if(actionLockCounter == 120 || collisionOn){
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
