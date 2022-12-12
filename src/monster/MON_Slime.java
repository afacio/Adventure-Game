package monster;

import java.util.Random;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;
import projectile.Rock;

public class MON_Slime extends Entity {

    GamePanel gamePanel;

    public MON_Slime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = MONSTER_TYPE;
        name = "Slime";
        speed = 1;
        defaultSpeed = speed;
        maxHealth = 5;
        health = maxHealth;
        attack = 2;
        defense = 0;
        exp = 6;
        projectile = new Rock(gamePanel);

        solidArea.x = 2;
        solidArea.y = 16;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    private void getImage() {
        up1 = setup("/monster/slime/up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/slime/up_2", gamePanel.tileSize, gamePanel.tileSize);
        up3 = setup("/monster/slime/up_3", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/slime/down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/slime/down_2", gamePanel.tileSize, gamePanel.tileSize);
        down3 = setup("/monster/slime/down_3", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/slime/left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/slime/left_2", gamePanel.tileSize, gamePanel.tileSize);
        left3 = setup("/monster/slime/left_3", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/slime/right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/slime/right_2", gamePanel.tileSize, gamePanel.tileSize);
        right3 = setup("/monster/slime/right_3", gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void setAction() {

        if (onPath) {
            checkChasingOff(gamePanel.player, 10, 100);

            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));

            checkShoot(100, 30);
        } else {
            checkChasingOn(gamePanel.player, 5, 100);
           
            getRandomDirection();
        }
    }

    public void demageReaction() {

        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop() {

        int i = new Random().nextInt(100) + 1;

        if (i <= 50) {
            dropItem(new OBJ_Coin_Bronze(gamePanel));
        }
        if (i > 50 && i <= 75) {
            dropItem(new OBJ_Heart(gamePanel));
        }
        if (i > 75 && i <= 100) {
            dropItem(new OBJ_Mana_Crystal(gamePanel));
        }

    }
}
