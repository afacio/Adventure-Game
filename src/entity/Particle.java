package entity;

import main.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gamePanel, Entity generator, Color color, int size, int speed, int maxHealth, int xd, int yd) {
        super(gamePanel);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.xd = xd;
        this.yd = yd;

        health = maxHealth;
        int offset = gamePanel.tileSize/2 - size/2;
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update() {
        worldX += xd*speed;
        worldY += yd*speed;
        health--;

        if(health < maxHealth/3) {
            yd++;
        }
        if(health <= 0) {
            alive = false;
        }
    }
    public void draw(Graphics2D g2) {

        int screenX = (int)(worldX - gamePanel.player.worldX) + gamePanel.player.screenX;
        int screenY = (int)(worldY - gamePanel.player.worldY) + gamePanel.player.screenY;
        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);

    }
    
}
