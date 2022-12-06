package environment;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Color;

public class Lighting {

    GamePanel gamePanel;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLightSource();

    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }

    public void update() {
        if(gamePanel.player.lightUpdated) {
            setLightSource();
            gamePanel.player.lightUpdated = false;
        }
    }

    private void setLightSource() {
        darknessFilter = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if(gamePanel.player.currentLightSource == null) {
            g2.setColor(new Color(0,0,0,0.85f));
        } else {
            int centerX = gamePanel.player.screenX + gamePanel.tileSize / 2;
            int centerY = gamePanel.player.screenY + gamePanel.tileSize / 2;
    
            Color color[] = new Color[6];
            float fraction[] = new float[6];
    
            color[0] = new Color(0,0,0,0.10f);
            color[1] = new Color(0,0,0,0.25f);
            color[2] = new Color(0,0,0,0.40f);
            color[3] = new Color(0,0,0,0.65f);
            color[4] = new Color(0,0,0,0.80f);
            color[5] = new Color(0,0,0,0.85f);
    
            fraction[0] = 0.10f;
            fraction[1] = 0.25f;
            fraction[2] = 0.40f;
            fraction[3] = 0.65f;
            fraction[4] = 0.80f;
            fraction[5] = 1f;
    
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gamePanel.player.currentLightSource.lightRadius / 2, fraction, color);
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g2.dispose();
    }

}
