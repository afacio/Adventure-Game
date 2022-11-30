package tiles_interactive;

import entity.Entity;
import main.GamePanel;
import java.awt.Color;

public class IT_DryTree extends InteractiveTile{

    GamePanel gamePanel;

    public IT_DryTree(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;
        this.worldX = (double)gamePanel.tileSize * col;
        this.worldY = (double)gamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destrucible = true;
        health = 3;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        if(entity.currentMeleeWeapon.type == AXE_TYPE) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        return new IT_Trunk(gamePanel, (int)worldX/gamePanel.tileSize, (int)worldY/gamePanel.tileSize);
    }

    @Override
    public void playSoundEfect() {
        gamePanel.playSoundEfect(13);
    }

    @Override
    public Color getParticleColor() {
        return new Color(65, 50, 30);
    }
    @Override
    public int getParticleSize() {
        return 6;
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
