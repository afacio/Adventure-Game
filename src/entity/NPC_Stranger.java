package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Stranger extends Entity {

    public NPC_Stranger(GamePanel gamePanel) {
        super(gamePanel);
        
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/stranger/stranger_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/stranger/stranger_up_2", gamePanel.tileSize, gamePanel.tileSize);            
        // up3 = setup("/npc/stranger/stranger_up_3");            
        down1 = setup("/npc/stranger/stranger_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/stranger/stranger_down_2", gamePanel.tileSize, gamePanel.tileSize);          
        // down3 = setup("/npc/stranger/stranger_down_3");          
        left1 = setup("/npc/stranger/stranger_left_1", gamePanel.tileSize, gamePanel.tileSize);          
        left2 = setup("/npc/stranger/stranger_left_2", gamePanel.tileSize, gamePanel.tileSize);            
        // left3 = setup("/npc/stranger/stranger_left_3");            
        right1 = setup("/npc/stranger/stranger_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/stranger/stranger_right_2", gamePanel.tileSize, gamePanel.tileSize);
        // right3 = setup("/npc/stranger/stranger_right_3");
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

    public void setDialogue(){
        dialogues[0] = "...";

    }

    @Override
    public void speak(){
        super.speak();
    }
}
