package environment;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.AlphaComposite;
import java.awt.Color;

public class Lighting {

    GamePanel gamePanel;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    public final int DAY = 0;
    public final int DUSK = 1;
    public final int NIGHT = 2;
    public final int DAWN = 3;
    public int dayState = DAY;

    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLightSource();

    }

    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        String dayStateInformation = "";

        switch(dayState) {
            case DAY: dayStateInformation = "Day"; break;
            case DUSK: dayStateInformation = "Dusk"; break;
            case NIGHT: dayStateInformation = "Night"; break;
            case DAWN: dayStateInformation = "Dawn"; break;
            default: break;
        }

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.drawString(dayStateInformation, 800, 500);
    }

    public void update() {
        if(gamePanel.player.lightUpdated) {
            setLightSource();
            gamePanel.player.lightUpdated = false;
        }

        if(dayState == DAY) {
            dayCounter ++;

            if(dayCounter > 600) {
                dayState = DUSK;
                dayCounter = 0;
            }
        }
        else if(dayState == DUSK) {
            filterAlpha += 0.001f;

            if(filterAlpha > 0.89f) {
                filterAlpha = 0.89f;
                dayState = NIGHT;
            }   
        }
        else if(dayState == NIGHT) {
            dayCounter ++;

            if(dayCounter > 600) {
                dayState = DAWN;
                dayCounter = 0;
            }
        } else if(dayState == DAWN) {
            filterAlpha -= 0.001f;

            if(filterAlpha < 0f) {
                filterAlpha = 0f;
                dayState = DAY;
            }   
        }
    }

    private void setLightSource() {
        darknessFilter = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if(gamePanel.player.currentLightSource == null) {
            g2.setColor(new Color(0,0,0.1f,0.89f));
        } else {
            int centerX = gamePanel.player.screenX + gamePanel.tileSize / 2;
            int centerY = gamePanel.player.screenY + gamePanel.tileSize / 2;
    
            Color color[] = new Color[6];
            float fraction[] = new float[6];
    
            color[0] = new Color(0,0,0.1f,0.10f);
            color[1] = new Color(0,0,0.1f,0.25f);
            color[2] = new Color(0,0,0.1f,0.40f);
            color[3] = new Color(0,0,0.1f,0.65f);
            color[4] = new Color(0,0,0.1f,0.80f);
            color[5] = new Color(0,0,0.1f,0.89f);
    
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
