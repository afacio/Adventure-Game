package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Entity {

    GamePanel gamePanel;

    public double worldX, worldY;
    public double speed;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int actionLockCounter = 0;

    public Rectangle solidArea = new Rectangle(14, 16, 22, 32);

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public String dialogues[] = new String[20];
    public int dialoguesIntex = 0;

    public int maxHealth;
    public int health;

    public BufferedImage image1, image2, image3;
    public String name;
    public boolean collision = false;

    public boolean invincible = false;
    public int invincibleCounter;

    public int type;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setDefaultSolidAreaValues();
    }

    private void setDefaultSolidAreaValues() {

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(new File("src/res" + imagePath + ".png"));
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
    }

    public void speak() {
        if (dialogues[dialoguesIntex] == null) {
            dialoguesIntex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialoguesIntex];
        dialoguesIntex++;

        switch (gamePanel.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monster);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayerCollision(this);

        if (this.type == 2 && contactPlayer) {
            if (gamePanel.player.invincible == false) {
                gamePanel.player.health--;
                gamePanel.player.invincible = true;
            }
        }

        int animationRefresh = 8;

        // IF COLLISION IS FALSE, ENTITY CAN MOVE
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
        if (spriteCounter > animationRefresh) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 3;
            } else if (spriteNumber == 3) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        double screenX = (worldX - gamePanel.player.worldX + gamePanel.player.screenX);
        double screenY = (worldY - gamePanel.player.worldY + gamePanel.player.screenY);

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

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

            g2.drawImage(image, (int) screenX, (int) screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.setColor(Color.red);
            g2.drawRect((int) (screenX + solidArea.x), (int) (screenY + solidArea.y), solidArea.width, solidArea.height);
        }
    }

}
