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

        int xDistance = (int) Math.abs(worldX - gamePanel.player.worldX);
        int yDistance = (int) Math.abs(worldY - gamePanel.player.worldY);
        int tileDistance = (int) (xDistance + yDistance) / gamePanel.tileSize;

        if (onPath) {
            if (tileDistance > 10) {
                onPath = false;
            }

            int goalCol = (int) (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (int) (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(100) + 1;
            if (i > 99 && !projectile.alive && shotAvelibleCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);

                for (int index = 0; index < gamePanel.projectileList[1].length; index++) {
                    if (gamePanel.projectileList[gamePanel.currentMap][index] == null) {
                        gamePanel.projectileList[gamePanel.currentMap][index] = projectile;
                        break;
                    }
                }

                projectile.playSoundEffect();
                shotAvelibleCounter = 0;
            }
        } else {
            if (tileDistance < 5) {
                int random = new Random().nextInt(100) + 1;
                if (random > 50) {
                    onPath = true;
                }
            }

            actionLockCounter++;

            if (actionLockCounter == 120 || collisionOn) {
                int i = new Random().nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                } else if (i > 25 && i <= 50) {
                    direction = "down";
                } else if (i > 50 && i <= 75) {
                    direction = "left";
                } else if (i > 55 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    // @Override
    // public void updateSprites() {
    //     super.updateSprites();
    //     if (spriteCounter > animationRefresh) {
    //         if (spriteNumber == 1) {
    //             spriteNumber = 2;
    //         } else if (spriteNumber == 2) {
    //             spriteNumber = 3;
    //         }
    //     } else if (spriteNumber == 3) {
    //         spriteNumber = 1;
    //     }
    //     spriteCounter = 0;
    // }

    // @Override
    // public BufferedImage drawSprites() {
    //     super.drawSprites();
    //     BufferedImage image = null;
    //     switch (direction) {
    //         case "up":
    //             if (spriteNumber == 1) {
    //                 image = up1;
    //             } else if (spriteNumber == 2) {
    //                 image = up2;
    //             }
    //             else if (spriteNumber == 3) {
    //                 image = up3;
    //             }
    //             break;
    //         case "down":
    //             if (spriteNumber == 1) {
    //                 image = down1;
    //             } else if (spriteNumber == 2) {
    //                 image = down2;
    //             } else if (spriteNumber == 3) {
    //                 image = down3;
    //             }
    //             break;
    //         case "left":
    //             if (spriteNumber == 1) {
    //                 image = left1;
    //             } else if (spriteNumber == 2) {
    //                 image = left2;
    //             } else if (spriteNumber == 3) {
    //                 image = left3;
    //             }
    //             break;
    //         case "right":
    //             if (spriteNumber == 1) {
    //                 image = right1;
    //             } else if (spriteNumber == 2) {
    //                 image = right2;
    //             } else if (spriteNumber == 3) {
    //                 image = right3;
    //             }
    //             break;
    //         default:
    //             break;
    //     }
    //     return image;
    // }
    public void demageReaction() {

        actionLockCounter = 0;
        // direction = gamePanel.player.direction;
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
