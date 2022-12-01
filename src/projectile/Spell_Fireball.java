package projectile;

import entity.Entity;
import entity.Projectile;
import java.awt.Color;
import java.awt.Rectangle;
import main.GamePanel;

public class Spell_Fireball extends Projectile {

    GamePanel gamePanel;

    public Spell_Fireball(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Fireball";
        speed = 5;
        maxHealth = 30;
        health = maxHealth;
        attack = 2;
        useManaCost = 1;
        alive = false;
        readImage();
    }

    private void readImage() {
        up1 = setup("/objects/fireball/fireball_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/objects/fireball/fireball_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/objects/fireball/fireball_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/objects/fireball/fireball_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/objects/fireball/fireball_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/objects/fireball/fireball_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/objects/fireball/fireball_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/objects/fireball/fireball_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void playSoundEfect() {
        gamePanel.playSoundEfect(12);
    }

    public boolean haveResource(Entity entity) {
        return entity.mana >= useManaCost;
    }

    public void subtractResource(Entity entity) {
        entity.mana -= useManaCost;
    }

    @Override
    public Color getParticleColor() {
        return new Color(240, 50, 0);
    }

    @Override
    public int getParticleSize() {
        return 5;
    }

    @Override
    public int getParticleSpeed() {
        return 1;
    }

    @Override
    public int getParticleMaxHealth() {
        return 20;
    }

    @Override
    public void update() {
        super.update();

        switch (direction) {
            case "up":
            setVerticalSolidArea();
                break;
            case "down":
            setVerticalSolidArea();
                break;
            case "left":
            setHorizontalSolidArea();
                break;
            case "right":
            setHorizontalSolidArea();
                break;
            default:
                break;
        }
    }

    private void setHorizontalSolidArea() {
        solidArea = new Rectangle(3, 15, 42, 22);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    private void setVerticalSolidArea() {
        solidArea = new Rectangle(10, 3, 22, 42);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}
