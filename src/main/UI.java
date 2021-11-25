package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {

    GamePanel gamePanel;
    Font arial_40;
    BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key(gamePanel);
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gameFinished) {

            String text = "You win!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gamePanel.screenWidth / 2 - textLength / 2;
            int y = gamePanel.tileSize * 3;
            g2.drawString(text, x, y);

            gamePanel.gameThread = null;

        } else {

            g2.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize,
                    gamePanel.tileSize, null);
            g2.drawString("x " + gamePanel.player.hasKey, 74, 65);

            // MESSAGE
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));

                int textLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth();
                int x = gamePanel.screenWidth / 2 - textLength / 2;
                int y = gamePanel.tileSize * 11;

                g2.drawString(message, x, y);

                messageCounter++;
                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

}
