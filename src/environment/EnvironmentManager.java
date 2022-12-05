package environment;

import main.GamePanel;
import java.awt.Graphics2D;

public class EnvironmentManager {
    
    GamePanel gamePanel;
    Lighting lighting;

    public EnvironmentManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setup() {
        lighting = new Lighting(gamePanel, 450);
    }

    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}
