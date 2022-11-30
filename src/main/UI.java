package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public boolean gameFinished = false;
    public int commandNum = 0;
    public int currentSlotCol = 0;
    public int currentSlotRow = 0;
    public static final int SLOT_MAX_COL = 7;
    public static final int SLOT_MAX_ROW = 5;
    public int subState = 0;

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

        if (gamePanel.gameState == GamePanel.TITLE_STATE) {
            drawTitleScreen();
        } else if (gamePanel.gameState == GamePanel.PLAY_STATE) {
            drawPlayerHealth();
            drawPlayerManaBar();
            drawMessage();
        } else if (gamePanel.gameState == GamePanel.PAUSE_STATE) {
            drawPlayerHealth();
            drawPlayerManaBar();
            drawPauseScreen();
        } else if (gamePanel.gameState == GamePanel.DIALOGUE_STATE) {
            drawPlayerHealth();
            drawPlayerManaBar();
            drawDialogueScreen();
        } else if (gamePanel.gameState == GamePanel.CHARACTER_STATE) {
            drawPlayerStatsScreen();
            drawPlayerInventoryScreen();
            drawObjectDescriptionScreen();
        } else if (gamePanel.gameState == GamePanel.OPTIONS_STATE) {
            drawOptionsScreen();
        } else if (gamePanel.gameState == GamePanel.GAME_OVER_STATE) {
            gameOverScreen();
        } else if (gamePanel.gameState == GamePanel.CREATING_STATE) {
            // drawCreatingScreen();
        }
    }

    public void addMessage(String text) {
        messages.add(text);
        messageCounter.add(0);
    }

    private void drawMessage() {
        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));

        if (!messages.isEmpty()) {
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i) != null) {
                    g2.setColor(Color.BLACK);
                    g2.drawString(messages.get(i), messageX + 2, messageY + 2);
                    g2.setColor(Color.WHITE);
                    g2.drawString(messages.get(i), messageX, messageY);

                    int counter = messageCounter.get(i) + 1;
                    messageCounter.set(i, counter);
                    messageY += 50;

                    if (messageCounter.get(i) > 180) {
                        messages.remove(i);
                        messageCounter.remove(i);
                    }
                }
            }
        }
    }

    private void drawTitleScreen() {

        g2.setColor(new Color(33, 33, 33));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96));

        String text = "ADVENTURE GAME";
        int x = getXforCenteredText(text);
        int y = 3 * gamePanel.tileSize;

        drawTextWithShadow(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 3;
        drawTextWithShadow(text, x, y);
        if (commandNum == 0) {
            drawTextWithShadow(">", x - 40, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += 64;
        drawTextWithShadow(text, x, y);
        if (commandNum == 1) {
            drawTextWithShadow(">", x - 40, y);
        }

        text = "MAP EDITOR";
        x = getXforCenteredText(text);
        y += 64;
        drawTextWithShadow(text, x, y);
        if (commandNum == 2) {
            drawTextWithShadow(">", x - 40, y);
        }

        text = "EXIT";
        x = getXforCenteredText(text);
        y += 64;
        drawTextWithShadow(text, x, y);
        if (commandNum == 3) {
            drawTextWithShadow(">", x - 40, y);
        }
    }

    private void drawTextWithShadow(String text, int x, int y) {
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    private void drawPlayerHealth() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize / 2;

        double scale = (double) width / gamePanel.player.maxHealth;
        double hpBarValue = scale * gamePanel.player.health;

        g2.setColor(Color.darkGray);
        g2.fillRect(x, y, width + 4, height);
        g2.setColor(Color.red);
        g2.fillRect(x + 2, y + 2, (int) hpBarValue, height - 4);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(maruMonica.getStyle(), 16F));
        g2.drawString(gamePanel.player.health + "/" + gamePanel.player.maxHealth, x + width / 2 - 8, y + height - 6);
    }

    private void drawPlayerManaBar() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize + 10;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize / 2;

        double scale = (double) width / gamePanel.player.maxMana;
        double manaBarValue = scale * gamePanel.player.mana;

        g2.setColor(Color.darkGray);
        g2.fillRect(x, y, width + 4, height);
        g2.setColor(Color.BLUE);
        g2.fillRect(x + 2, y + 2, (int) manaBarValue, height - 4);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(maruMonica.getStyle(), 16F));
        g2.drawString(gamePanel.player.mana + "/" + gamePanel.player.maxMana, x + width / 2 - 8, y + height - 6);
    }

    private void drawPauseScreen() {

        String text = "PAUSED";
        g2.setFont(g2.getFont().deriveFont(maruMonica.getStyle(), 62F));

        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2 - 2 * gamePanel.tileSize;

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

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    private void drawPlayerStatsScreen() {
        int frameX = gamePanel.tileSize;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 5;
        int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);

        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.health + "/" + gamePanel.player.maxHealth);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.mana + "/" + gamePanel.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.strenght);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gamePanel.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gamePanel.player.currentMeleeWeapon.down1, tailX - gamePanel.tileSize, textY - 24, null);
        textY += gamePanel.tileSize;
        g2.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 24, null);

    }

    private void drawPlayerInventoryScreen() {

        int frameX = gamePanel.tileSize * 11;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * (SLOT_MAX_COL + 1);
        int frameHeight = gamePanel.tileSize * (SLOT_MAX_ROW + 1);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        final int firstSlotX = frameX + 20;
        final int firstSlotY = frameY + 20;
        int slotX = firstSlotX;
        int slotY = firstSlotY;
        int slotSize = gamePanel.tileSize + 1;

        for (int item = 0; item < gamePanel.player.inventory.size(); item++) {

            if (gamePanel.player.inventory.get(item) == gamePanel.player.currentMeleeWeapon
                    || gamePanel.player.inventory.get(item) == gamePanel.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }

            g2.drawImage(gamePanel.player.inventory.get(item).down1, slotX, slotY, null);
            slotX += slotSize;

            if (item == 6 || item == 13 || item == 20 || item == 27) {
                slotX = firstSlotX;
                slotY += slotSize;
            }

        }

        int cursorX = firstSlotX + (slotSize * currentSlotCol);
        int cursorY = firstSlotY + (slotSize * currentSlotRow);
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    private void drawObjectDescriptionScreen() {

        int frameX = gamePanel.tileSize * 11;
        int frameY = SLOT_MAX_ROW * gamePanel.tileSize + 2 * gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * (SLOT_MAX_COL + 1);
        int frameHeight = gamePanel.tileSize * 4;

        int descriptionX = frameX + 20;
        int descriptionY = frameY + gamePanel.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getCurrentItemInventoryIndex();
        if (itemIndex < gamePanel.player.inventory.size()) {

            drawSubWindow(frameX, frameY, frameWidth, frameHeight);

            for (String line : gamePanel.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, descriptionX, descriptionY);
                descriptionY += 32;
            }
        }
    }

    public int getCurrentItemInventoryIndex() {
        return currentSlotCol + (currentSlotRow * (SLOT_MAX_ROW + 2));
    }

    private void drawOptionsScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gamePanel.tileSize * 6;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 8;
        int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullscreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gamePanel.keyHandler.enterPressed = false;

    }

    private void options_top(int frameX, int frameY) {

        int textX;
        int textY;

        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.fullScreenOn = !gamePanel.fullScreenOn;
                subState = 1;
            }
        }

        // MUSIC
        textY += gamePanel.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // SE
        textY += gamePanel.tileSize;
        g2.drawString("Sound Efect", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // CONTROL
        textY += gamePanel.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // END GAME
        textY += gamePanel.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // BACK
        textY += gamePanel.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 0;
                gamePanel.gameState = gamePanel.PLAY_STATE;
            }
        }

        // FULL SCREEN CHECKBOX
        textX = frameX + (int) (gamePanel.tileSize * 4.5);
        textY = frameY + gamePanel.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX, textY, 24, 24);
        if (gamePanel.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // MUSIC VOLUME SLIDER
        textY += gamePanel.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        g2.fillRect(textX, textY, 24 * gamePanel.music.volumeScale, 24);

        // SE VOLUME SLIDER
        textY += gamePanel.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        g2.fillRect(textX, textY, 24 * gamePanel.soundEfect.volumeScale, 24);

        gamePanel.config.saveConfig();
    }

    private void options_fullscreenNotification(int frameX, int frameY) {
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        String information = "The change will take\neffect after restarting\nthe game";

        for (String line : information.split("\n")) {
            textX = getXforCenteredText(line);
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY = frameY + gamePanel.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 0;
            }
        }
    }

    private void options_control(int frameX, int frameY) {
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        g2.drawString("Move", textX, textY);

        textY += gamePanel.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gamePanel.tileSize;

        g2.drawString("Shoot/Cast", textX, textY);
        textY += gamePanel.tileSize;

        g2.drawString("Player Screen", textX, textY);
        textY += gamePanel.tileSize;

        g2.drawString("Pouse", textX, textY);
        textY += gamePanel.tileSize;

        g2.drawString("Options", textX, textY);

        // BACK
        textY = frameY + gamePanel.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }

        textX = frameX + gamePanel.tileSize * 6;
        textY = frameY + gamePanel.tileSize * 2;
        g2.drawString("WSAD", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("F", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("I", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("P", textX, textY);
        textY += gamePanel.tileSize;
        g2.drawString("ESC", textX, textY);

    }

    private void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        String information = "Quit the qame and\nreturn to title screen?";

        for (String line : information.split("\n")) {
            textX = getXforCenteredText(line);
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                gamePanel.gameState = gamePanel.TITLE_STATE;
                gamePanel.stopMusic();
            }
        }

        text = "No";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    private void gameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        String text = "Game Over";
        int x;
        int y;
        g2.setFont(g2.getFont().deriveFont(110F));

        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize * 4;
        drawTextWithShadow(text, x, y);

        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 4;
        drawTextWithShadow(text, x, y);
        if (commandNum == 0) {
            drawTextWithShadow(">", x - 25, y);
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.PLAY_STATE;
                gamePanel.restart();
                gamePanel.keyHandler.enterPressed = false;
            }
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += 60;
        drawTextWithShadow(text, x, y);
        if (commandNum == 1) {
            drawTextWithShadow(">", x - 25, y);
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.TITLE_STATE;
                gamePanel.restart();
                commandNum = 0;
                gamePanel.keyHandler.enterPressed = false;
            }
        }

    }

    private void drawSubWindow(int x, int y, int width, int height) {
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

    private int getXforAlignToRightText(String text, int tailX) {
        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - lenght;
    }

}
