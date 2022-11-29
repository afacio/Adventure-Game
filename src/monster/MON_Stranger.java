package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import projectile.Rock;


public class MON_Stranger extends Entity {

    GamePanel gamePanel;

    public MON_Stranger(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = MONSTER_TYPE;
        name = "Stranger";
        speed = 1;
        maxHealth = 20;
        health = maxHealth;
        attack = 2;
        defense = 1;
        exp = 12;
        projectile = new Rock(gamePanel);

        getImage();
    }

    public void getImage() {
        up1 = setup("/npc/stranger/stranger_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/stranger/stranger_up_2", gamePanel.tileSize, gamePanel.tileSize);                      
        down1 = setup("/npc/stranger/stranger_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/stranger/stranger_down_2", gamePanel.tileSize, gamePanel.tileSize);               
        left1 = setup("/npc/stranger/stranger_left_1", gamePanel.tileSize, gamePanel.tileSize);          
        left2 = setup("/npc/stranger/stranger_left_2", gamePanel.tileSize, gamePanel.tileSize);                       
        right1 = setup("/npc/stranger/stranger_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/stranger/stranger_right_2", gamePanel.tileSize, gamePanel.tileSize);
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
        int i = new Random().nextInt(100)+1;
        if(i > 99 && !projectile.alive && shotAvelibleCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gamePanel.projectileList.add(projectile);
            projectile.playSoundEfect();
            shotAvelibleCounter = 0;
        }
    }

    public void demageReaction() {
        actionLockCounter = 0;
        direction = gamePanel.player.direction;
    }
}
