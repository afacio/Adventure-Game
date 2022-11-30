package tiles_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{

    GamePanel gamePanel;
    public boolean destrucible = false;

    public InteractiveTile(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    public void update() {
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void playSoundEfect() { }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }
    
}
