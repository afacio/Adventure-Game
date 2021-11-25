package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Entity {
    public double worldX, worldY;
    public double speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

}
