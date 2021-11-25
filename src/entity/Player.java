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
import javax.swing.text.Utilities;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

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
            gamePanel.collisionChecker.checkTile(this);

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

            String objectName = gamePanel.obj[index].name;

            switch (objectName) {
            case "Key":
                gamePanel.playSoundEfect(1);
                gamePanel.obj[index] = null;
                hasKey++;
                gamePanel.ui.showMessage("You got a key!");
                break;
            case "Door":
                if (hasKey > 0) {
                    gamePanel.playSoundEfect(3);
                    gamePanel.obj[index] = null;
                    hasKey--;
                    gamePanel.ui.showMessage("You opened the door!");
                } else {
                    gamePanel.ui.showMessage("You need a key!");
                }
                break;
            case "Chest":
                gamePanel.playSoundEfect(4);
                break;

            case "Boots":
                gamePanel.playSoundEfect(2);
                speed += 1;
                gamePanel.obj[index] = null;
                gamePanel.ui.showMessage("Speed boost!");
                break;

            case "Trofeum":
                gamePanel.stopMusic();
                gamePanel.playSoundEfect(4);
                gamePanel.ui.gameFinished = true;
                break;
            }
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
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

    }
}
