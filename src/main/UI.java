package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.BasicStroke;
import java.awt.image.BufferedImage;


public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font maruMonica;
    BufferedImage heart_full, heart_half, heart_empty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";
    public boolean gameFinished = false;
    public int commandNum = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        try {
            InputStream is = getClass().getResourceAsStream("/res/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image1;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }
        else if (gamePanel.gameState == gamePanel.playState) {
            drawPlayerHealth();
        }
        else if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
        else if (gamePanel.gameState == gamePanel.dialogueState) {
            drawDialogueScreen();
        }
        else if (gamePanel.gameState == gamePanel.creatingState) {
            // drawCreatingScreen();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    private void drawTitleScreen(){

        g2.setColor(new Color(33, 33, 33));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96));
        
        String text = "ADVENTURE GAME";
        int x = getXforCenteredText(text);
        int y = 3 * gamePanel.tileSize;

        drawMenuTextWithShadow(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 3;
        drawMenuTextWithShadow(text, x, y);
        if(commandNum == 0){
            drawMenuTextWithShadow(">", x - 40, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += 64;
        drawMenuTextWithShadow(text, x, y);
        if(commandNum == 1){
            drawMenuTextWithShadow(">", x - 40, y);
        }

        text = "MAP EDITOR";
        x = getXforCenteredText(text);
        y += 64;
        drawMenuTextWithShadow(text, x, y);
        if(commandNum == 2){
            drawMenuTextWithShadow(">", x - 40, y);
        }

        text = "EXIT";
        x = getXforCenteredText(text);
        y += 64;
        drawMenuTextWithShadow(text, x, y);
        if(commandNum == 3){
            drawMenuTextWithShadow(">", x - 40, y);
        }
    }

    private void drawMenuTextWithShadow(String text, int x, int y){
        g2.setColor(Color.BLACK);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    private void drawPlayerHealth(){
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        while(i < gamePanel.player.maxHealth / 2){
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gamePanel.tileSize * 1.3 ;
        }

        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i = 0;

        while(i < gamePanel.player.health){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gamePanel.player.health){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gamePanel.tileSize * 1.3 ;
        }

    }

    private void drawPauseScreen() {

        String text = "PAUSED";
        g2.setFont(g2.getFont().deriveFont(maruMonica.getStyle(), 62F));

        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2 - 2* gamePanel.tileSize;


        g2.drawString(text, x, y);

    }

    private void drawDialogueScreen() {
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize / 2;
        y += gamePanel.tileSize;

        g2.setFont(g2.getFont().deriveFont(maruMonica.getStyle(), 26F));

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color transparentBlack = new Color(0, 0, 0, 200);
        g2.setColor(transparentBlack);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    private int getXforCenteredText(String text) {
        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - lenght / 2;
    }

}
