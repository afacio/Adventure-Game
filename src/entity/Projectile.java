package entity;

import main.GamePanel;

public class Projectile extends Entity {

    Entity entity;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }

    public void set(Double worldX, Double worldY, String direction, boolean alive, Entity entity) {

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.entity = entity;
        this.health = this.maxHealth;
    }
    public void update() {

        if(entity == gamePanel.player) {
            int monsterIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monster);
            if( monsterIndex != 999 && !gamePanel.monster[monsterIndex].invincible) {
                gamePanel.player.damageMonster(monsterIndex, attack * gamePanel.player.attack);
                alive = false;
            }
        } else if(entity != gamePanel.player) {
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayerCollision(this);
            if (contactPlayer && !invincible) {
                damagePlayer(entity.attack);
                alive = false;
                }
            }

        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
            default: break;
        }

        health--;
        if(health <= 0) {
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) { spriteNumber = 2; } 
            else if (spriteNumber == 2) { spriteNumber = 1; } 
            spriteCounter = 0;
        }

    }

    public void playSoundEfect() {}
    public boolean haveResource(Entity entity) {
        return entity.mana >= useManaCost;
    }
    public void subtractResource(Entity entity) {}
    
}
