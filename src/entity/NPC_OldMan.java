package entity;

import java.util.Random;
import java.awt.Rectangle;

import main.GamePanel;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);
        
        direction = "down";
        speed = 0.5;
        type = NPC_TYPE;

        solidArea = new Rectangle(6,6, 32, 42);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        animationRefresh = 25;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/oldMan/oldman_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/oldMan/oldman_up_2", gamePanel.tileSize, gamePanel.tileSize);                   
        down1 = setup("/npc/oldMan/oldman_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/oldMan/oldman_down_2", gamePanel.tileSize, gamePanel.tileSize);                   
        left1 = setup("/npc/oldMan/oldman_left_1", gamePanel.tileSize, gamePanel.tileSize);          
        left2 = setup("/npc/oldMan/oldman_left_2", gamePanel.tileSize, gamePanel.tileSize);                    
        right1 = setup("/npc/oldMan/oldman_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/oldMan/oldman_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void setAction() {
        Random random = new Random();
        if(onPath) {
            // int goalCol = (int)(gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            // int goalRow = (int)(gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;

            // searchPath(goalCol, goalRow);
        } else {
            actionLockCounter ++;
    
            if(actionLockCounter == 120 || collisionOn){
                int i = random.nextInt(100) + 1;
        
                if (i <= 25) {
                    direction = "up";
                }
                else if (i > 25 && i <= 50) {
                    direction = "down";
                }
                else if (i > 50 && i <= 75) {
                    direction = "left";
                }
                else if (i > 55 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void setDialogue(){
        dialogues[0] = "Hello stranger.";
        dialogues[1] = "So you've come to this island to find the treasure ?";
        dialogues[2] = "I used to be a great wizard but now... \nI'm a bit too old for taking an adventure.";
        dialogues[3] = "Well, good luck on you!";

    }

    @Override
    public void speak(){
        super.speak();
        // onPath = true;
    }
    
}
