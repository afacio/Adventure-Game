package projectile;

import entity.Projectile;
import main.GamePanel;
import java.awt.Color;
import java.awt.Rectangle;

public class Rock extends Projectile {

    GamePanel gamePanel;

    public Rock(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Rock";
        speed= 10;
        maxHealth= 30;
        health = maxHealth;
        attack = 2;
        useManaCost = 1;
        alive = false;
        readImage();

        solidArea = new Rectangle(13, 13, 21, 21);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    private void readImage(){
        up1 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/objects/rock", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void playSoundEffect() {
        gamePanel.playSoundEffect(7);
    }
    
    @Override
    public Color getParticleColor() {
        return new Color(40, 50, 0);
    }
    @Override
    public int getParticleSize() {
        return 8;
    }
    @Override
    public int getParticleSpeed() {
        return 1;
    }
    @Override
    public int getParticleMaxHealth() {
        return 20;
    }
    
}
