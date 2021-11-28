package entity;

import main.KeyHandler;
import main.UtilityTool;
import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;


    int standCounter = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle();

        solidArea.x = 11;
        solidArea.y = 16;
        solidArea.width = 25;
        solidArea.height = 25;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");            
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");          
        left1 = setup("boy_left_1");          
        left2 = setup("boy_left_2");            
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    }

    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(new File("src/res/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
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

            // CHECK TILE COLLISION
            collisionOn = false;
            // gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECTS COLLISION
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

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
            if (spriteCounter > 11) {
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
                spriteCounter = 0;
            }

        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
        case "up":
            if (spriteNumber == 1) {
                image = up1;
            } else {
                image = up2;
            }
            break;

        case "down":
            if (spriteNumber == 1) {
                image = down1;
            } else {
                image = down2;
            }
            break;
        case "left":
            if (spriteNumber == 1) {
                image = left1;
            } else {
                image = left2;
            }
            break;
        case "right":
            if (spriteNumber == 1) {
                image = right1;
            } else {
                image = right2;
            }
            break;
        }

        int x = screenX;
        int y = screenY;

        if(screenX > worldX){
            x = (int)worldX;
        }
        if(screenY > worldY){
            y = (int)worldY;
        }
        int rightOffset = gamePanel.screenWidth - screenX;
        if(rightOffset > gamePanel.worldWidth - worldX){
            x = (int)(gamePanel.screenWidth - (gamePanel.worldWidth - worldX));
        }
        int bottomOffset = gamePanel.screenHeight - screenY;
        if(bottomOffset > gamePanel.worldHeight- worldY){
            y = (int)(gamePanel.screenHeight - (gamePanel.worldHeight - worldY));
        }


        g2.drawImage(image, x, y, null);
        g2.setColor(Color.red);
        g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);

    }
}
