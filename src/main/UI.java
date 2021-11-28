package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gamePanel.gameState == gamePanel.playState){
            // Do play state stuff later
        }
        if(gamePanel.gameState == gamePanel.pauseState){
            drawPauseScreen();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    private void drawPauseScreen(){

        String text = "PAUSED";
        
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight/2;
        
        g2.drawString(text, x, y);

    }

    private int getXforCenteredText(String text){
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth/2 - lenght/2;
    }

}
