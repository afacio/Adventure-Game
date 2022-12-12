package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Gold;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

public class MON_Orc extends Entity {
    
    GamePanel gamePanel;

    public MON_Orc(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = MONSTER_TYPE;
        name = "Orc";
        speed = 1;
        defaultSpeed = speed;
        maxHealth = 10;
        health = maxHealth;
        attack = 8;
        defense = 2;
        exp = 10;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;

        motion1_duration = 40;
        motion2_duration = 85;

        getImage();
        getAttackImage();
    }

    private void getImage() {
        up1 = setup("/monster/orc/orc_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/orc/orc_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/orc/orc_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/orc/orc_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/orc/orc_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/orc/orc_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/orc/orc_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/orc/orc_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    private void getAttackImage() {
        attackUp1 = setup("/monster/orc/orc_attack_up_1", gamePanel.tileSize, 2 * gamePanel.tileSize);
        attackUp2 = setup("/monster/orc/orc_attack_up_2", gamePanel.tileSize, 2 * gamePanel.tileSize);
        attackDown1 = setup("/monster/orc/orc_attack_down_1", gamePanel.tileSize, 2 * gamePanel.tileSize);
        attackDown2 = setup("/monster/orc/orc_attack_down_2", gamePanel.tileSize, 2 * gamePanel.tileSize);
        attackLeft1 = setup("/monster/orc/orc_attack_left_1", 2 * gamePanel.tileSize, gamePanel.tileSize);
        attackLeft2 = setup("/monster/orc/orc_attack_left_2", 2 * gamePanel.tileSize, gamePanel.tileSize);
        attackRight1 = setup("/monster/orc/orc_attack_right_1", 2 * gamePanel.tileSize, gamePanel.tileSize);
        attackRight2 = setup("/monster/orc/orc_attack_right_2", 2 * gamePanel.tileSize, gamePanel.tileSize);
    }

    @Override
    public void setAction() {

        if (onPath) {
            checkChasingOff(gamePanel.player, 10, 100);
            searchPath(getGoalCol(gamePanel.player), getGoalRow(gamePanel.player));
        } else {
            checkChasingOn(gamePanel.player, 5, 100);
            getRandomDirection();
        }

        if(!attacking) {
            checkAttack(50, gamePanel.tileSize*3, gamePanel.tileSize);
        }
    }

    public void demageReaction() {

        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop() {

        int i = new Random().nextInt(100) + 1;

        if (i <= 50) {
            dropItem(new OBJ_Coin_Gold(gamePanel));
        }
        if (i > 50 && i <= 75) {
            dropItem(new OBJ_Heart(gamePanel));
        }
        if (i > 75 && i <= 100) {
            dropItem(new OBJ_Mana_Crystal(gamePanel));
        }

    }
    
}
