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

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";

        maxHealth = 6;
        health = maxHealth;
    }

    public void getPlayerImage() {
        up1 = setup("/player/player_up (1)");
        up2 = setup("/player/player_up (2)");
        up3 = setup("/player/player_up (3)");
        down1 = setup("/player/player_down (1)");
        down2 = setup("/player/player_down (2)");
        down3 = setup("/player/player_down (3)");
        left1 = setup("/player/player_left (1)");
        left2 = setup("/player/player_left (2)");
        left3 = setup("/player/player_left (3)");
        right1 = setup("/player/player_right (1)");
        right2 = setup("/player/player_right (2)");
        right3 = setup("/player/player_right (3)");
    }

    public void update() {

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
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

            gamePanel.keyHandler.enterPressed = false;

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {

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
                }
            }

            spriteCounter++;
            if (spriteCounter > 8) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 3;
                } else if (spriteNumber == 3) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNumber = 2;
                standCounter = 0;
            }
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    private void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    private void interactNPC(int index) {
        if (index != 999) {
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[index].speak();
            }
        }
    }

    private void monsterContact(int index) {
        if (index != 999) {
            if (invincible == false) {
                health--;
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                } else if (spriteNumber == 2) {
                    image = up2;
                } else {
                    image = up3;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                } else if (spriteNumber == 2) {
                    image = down2;
                } else {
                    image = down3;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                } else if (spriteNumber == 2) {
                    image = left2;
                } else {
                    image = left3;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                } else if (spriteNumber == 2) {
                    image = right2;
                } else {
                    image = right3;
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
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, x, y, null);
        g2.setColor(Color.green);
        g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
