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

public class Entity { // klasa abstrakcyjna nie mająca żądnej instancji

    GamePanel gamePanel;

    public Long id;

    public double worldX, worldY;
    public double speed;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int actionLockCounter = 0;

    public Rectangle solidArea = new Rectangle(14, 16, 22, 32);

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gamePanel, Long id) {
        this.gamePanel = gamePanel;
        this.id = id;

        setDefaultSolidAreaValues();
    }

    private void setDefaultSolidAreaValues(){

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

    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayerCollision(this);
        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npc);

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
            g2.drawRect((int)(screenX + solidArea.x), (int)(screenY + solidArea.y), solidArea.width, solidArea.height);
        }
    }

}
