package entity;

import main.KeyHandler;
import main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyHandler;

    public int health = 100;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;
    public boolean attackCanceled = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";

        maxHealth = 6;
        health = maxHealth;

        attackArea.width = 36;
        attackArea.height = 36;

    }

    public void getPlayerImage() {
        up1 = setup("/player/old_player/Walking sprites/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/old_player/Walking sprites/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
        // up3 = setup("/player/player_up (3)");
        down1 = setup("/player/old_player/Walking sprites/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/old_player/Walking sprites/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
        // down3 = setup("/player/player_down (3)");
        left1 = setup("/player/old_player/Walking sprites/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/old_player/Walking sprites/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
        // left3 = setup("/player/player_left (3)");
        right1 = setup("/player/old_player/Walking sprites/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/old_player/Walking sprites/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);
        // right3 = setup("/player/player_right (3)");
    }

    private void getPlayerAttackImage() {
        attackUp1 = setup("/player/old_player/Attacking sprites/boy_attack_up_1", gamePanel.tileSize,
                2 * gamePanel.tileSize);
        attackUp2 = setup("/player/old_player/Attacking sprites/boy_attack_up_2", gamePanel.tileSize,
                2 * gamePanel.tileSize);
        attackDown1 = setup("/player/old_player/Attacking sprites/boy_attack_down_1", gamePanel.tileSize,
                2 * gamePanel.tileSize);
        attackDown2 = setup("/player/old_player/Attacking sprites/boy_attack_down_2", gamePanel.tileSize,
                2 * gamePanel.tileSize);
        attackLeft1 = setup("/player/old_player/Attacking sprites/boy_attack_left_1", 2 * gamePanel.tileSize,
                gamePanel.tileSize);
        attackLeft2 = setup("/player/old_player/Attacking sprites/boy_attack_left_2", 2 * gamePanel.tileSize,
                gamePanel.tileSize);
        attackRight1 = setup("/player/old_player/Attacking sprites/boy_attack_right_1", 2 * gamePanel.tileSize,
                gamePanel.tileSize);
        attackRight2 = setup("/player/old_player/Attacking sprites/boy_attack_right_2", 2 * gamePanel.tileSize,
                gamePanel.tileSize);
    }

    public void update() {

        if (attacking) {
            attacking();
        } else if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed
                || keyHandler.enterPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            }
            if (keyHandler.downPressed) {
                direction = "down";
            }
            if (keyHandler.leftPressed) {
                direction = "left";
            }
            if (keyHandler.rightPressed) {
                direction = "right";
            }

            collisionOn = false;

            // CHECK TILE COLLISION
            gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECTS COLLISION
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // // CHECK NPC COLLISION
            int npcIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npc);
            interactNPC(npcIndex);

            // // CHECK MONSTER COLLISION
            int monsterIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monster);
            monsterContact(monsterIndex);

            // CHECK EVENT
            gamePanel.eventHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyHandler.enterPressed) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    default:
                        break;
                }
            }

            if (keyHandler.enterPressed && !attackCanceled) {
                gamePanel.playSoundEfect(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gamePanel.keyHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNumber = 1;
                standCounter = 0;
            }
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    private void attacking() {

        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 15) {
            spriteNumber = 2;

            int currentWorldX = (int) worldX;
            int currentWorldY = (int) worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monster);
            demageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 15) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    private void interactNPC(int index) {
        if (gamePanel.keyHandler.enterPressed) {
            if (index != 999) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[index].speak();
            }
        }
    }

    private void monsterContact(int index) {
        if (index != 999) {
            if (!invincible) {
                gamePanel.playSoundEfect(9);
                health--;
                invincible = true;
            }
        }
    }

    private void demageMonster(int index) {
        if (index != 999) {
            if (!gamePanel.monster[index].invincible) {
                gamePanel.playSoundEfect(5);
                gamePanel.monster[index].health--;
                gamePanel.monster[index].invincible = true;
                gamePanel.monster[index].demageReaction();
                if (gamePanel.monster[index].health <= 0) {
                    gamePanel.playSoundEfect(8);
                    gamePanel.monster[index].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = up1;
                    } else if (spriteNumber == 2) {
                        image = up2;
                    }
                    // else { image = up3; }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackUp1;
                    } else if (spriteNumber == 2) {
                        image = attackUp2;
                    }
                    tempScreenY -= gamePanel.tileSize;
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = down1;
                    } else if (spriteNumber == 2) {
                        image = down2;
                    }
                    // else { image = down3; }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackDown1;
                    } else if (spriteNumber == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = left1;
                    } else if (spriteNumber == 2) {
                        image = left2;
                    }
                    // else { image = left3; }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackLeft1;
                    } else if (spriteNumber == 2) {
                        image = attackLeft2;
                    }
                    tempScreenX -= gamePanel.tileSize;
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = right1;
                    } else if (spriteNumber == 2) {
                        image = right2;
                    }
                    // else { image = right3; }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackRight1;
                    } else if (spriteNumber == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        int x = screenX;
        int y = screenY;

        if (screenX > worldX) {
            x = (int) worldX;
        }
        if (screenY > worldY) {
            y = (int) worldY;
        }
        int rightOffset = gamePanel.screenWidth - screenX;
        if (rightOffset > gamePanel.worldWidth - worldX) {
            x = (int) (gamePanel.screenWidth - (gamePanel.worldWidth - worldX));
        }
        int bottomOffset = gamePanel.screenHeight - screenY;
        if (bottomOffset > gamePanel.worldHeight - worldY) {
            y = (int) (gamePanel.screenHeight - (gamePanel.worldHeight - worldY));
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setColor(Color.green);
        g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
