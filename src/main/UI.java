package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.awt.BasicStroke;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40, maruMonica;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public String currentDialogue = "";

    public boolean gameFinished = false;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        try {
            InputStream is = getClass().getResourceAsStream("/res/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.playState) {
            // Do play state stuff later
        }
        else if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
        else if (gamePanel.gameState == gamePanel.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
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
