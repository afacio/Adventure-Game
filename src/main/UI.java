package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Coin_Gold;
import object.OBJ_Coin_Silver;

import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font maruMonica;
    BufferedImage bronzeCoin, silverCoin, goldCoin;
    public boolean messageOn = false;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public boolean gameFinished = false;
    public int commandNum = 0;
    public int playerCurrentSlotCol = 0;
    public int playerCurrentSlotRow = 0;
    public int entityCurrentSlotCol = 0;
    public int entityCurrentSlotRow = 0;
    public static final int SLOT_MAX_COL = 6;
    public static final int SLOT_MAX_ROW = 4;
    public int subState = 0;
    int counter = 0;
    public Entity entity;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Entity bronzeCoinOBJ = new OBJ_Coin_Bronze(gamePanel);
        bronzeCoin = bronzeCoinOBJ.down1;
        Entity silverCoinOBJ = new OBJ_Coin_Silver(gamePanel);
        silverCoin = silverCoinOBJ.down1;
        Entity goldCoinOBJ = new OBJ_Coin_Gold(gamePanel);
        goldCoin = goldCoinOBJ.down1;
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
            drawInventoryScreen(gamePanel.player, true);
        } else if (gamePanel.gameState == GamePanel.OPTIONS_STATE) {
            drawOptionsScreen();
        } else if (gamePanel.gameState == GamePanel.GAME_OVER_STATE) {
            gameOverScreen();
        } else if (gamePanel.gameState == GamePanel.MAP_TRANSITION_STATE) {
            transitionStateScreen();
        } else if (gamePanel.gameState == GamePanel.TRADE_STATE) {
            tradeScreen();
        } else if (gamePanel.gameState == GamePanel.STORAGE_STATE) {
            storageScreen();
        } else if (gamePanel.gameState == GamePanel.SLEEP_STATE) {
            sleepScreen();
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

    private void drawInventoryScreen(Entity entity, boolean cursor) {

        int frameX;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * (SLOT_MAX_COL + 1);
        int frameHeight = gamePanel.tileSize * (SLOT_MAX_ROW + 1);
        int slotCol;
        int slotRow;

        if (entity == gamePanel.player) {
            frameX = gamePanel.tileSize * 12;
            slotCol = playerCurrentSlotCol;
            slotRow = playerCurrentSlotRow;
        } else {
            frameX = gamePanel.tileSize;
            slotCol = entityCurrentSlotCol;
            slotRow = entityCurrentSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        final int firstSlotX = frameX + 20;
        final int firstSlotY = frameY + 20;
        int slotX = firstSlotX;
        int slotY = firstSlotY;
        int slotSize = gamePanel.tileSize + 1;

        for (int item = 0; item < entity.inventory.size(); item++) {

            if (entity.inventory.get(item) == entity.currentMeleeWeapon
                    || entity.inventory.get(item) == entity.currentShield
                    || entity.inventory.get(item) == entity.currentLightSource) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(item).down1, slotX, slotY, null);

            if (entity.inventory.get(item).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX = 0;
                int amountY = slotY + gamePanel.tileSize;
                String s = Integer.toString(entity.inventory.get(item).amount);
                amountX = getXforAlignToRightText(s, slotX + 44);

                drawTextWithShadow(s, amountX, amountY);
            }

            slotX += slotSize;

            if (item == 5 || item == 11 || item == 17) {
                slotX = firstSlotX;
                slotY += slotSize;
            }

        }
        if (cursor) {
            int cursorX = firstSlotX + (slotSize * slotCol);
            int cursorY = firstSlotY + (slotSize * slotRow);
            int cursorWidth = gamePanel.tileSize;
            int cursorHeight = gamePanel.tileSize;

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            frameHeight = gamePanel.tileSize * 4;
            frameY = SLOT_MAX_ROW * gamePanel.tileSize + 2 * gamePanel.tileSize;

            int descriptionX = frameX + 20;
            int descriptionY = frameY + gamePanel.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getCurrentItemInventoryIndex(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()) {

                drawSubWindow(frameX, frameY, frameWidth, frameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, descriptionX, descriptionY);
                    descriptionY += 32;
                }
            }
        }
    }

    public int getCurrentItemInventoryIndex(int slotCol, int slotRow) {
        return slotCol + (slotRow * (SLOT_MAX_ROW + 2));
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
        g2.drawString("Sound Effect", textX, textY);
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
        g2.fillRect(textX, textY, 24 * gamePanel.soundEffect.volumeScale, 24);

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

        g2.drawString("Pause", textX, textY);
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
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += 60;
        drawTextWithShadow(text, x, y);
        if (commandNum == 1) {
            drawTextWithShadow(">", x - 25, y);
        }

    }

    private void transitionStateScreen() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        if (counter == 50) {
            counter = 0;
            gamePanel.gameState = gamePanel.PLAY_STATE;
            gamePanel.currentMap = gamePanel.eventHandler.tempMap;
            gamePanel.player.worldX = (double) gamePanel.tileSize * gamePanel.eventHandler.tempCol;
            gamePanel.player.worldY = (double) gamePanel.tileSize * gamePanel.eventHandler.tempRow;
            gamePanel.eventHandler.previousEventX = (int) gamePanel.player.worldX;
            gamePanel.eventHandler.previousEventY = (int) gamePanel.player.worldY;
        }
    }

    private void tradeScreen() {
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
            default:
                break;
        }
        gamePanel.keyHandler.enterPressed = false;
    }

    private void trade_select() {
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 7);
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize / 2;
        y += gamePanel.tileSize;

        g2.setFont(g2.getFont().deriveFont(maruMonica.getStyle(), 26F));

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

        x = gamePanel.tileSize * 15;
        y = gamePanel.tileSize / 2;
        width = gamePanel.tileSize * 3;

        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 20, y);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 1;
            }
        }
        y += 40;
        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 20, y);
            if (gamePanel.keyHandler.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }
        y += 70;
        g2.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 20, y);
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.PLAY_STATE;
                subState = 0;
                commandNum = 0;
            }
        }
    }

    private void trade_buy() {
        drawInventoryScreen(gamePanel.player, false);
        drawInventoryScreen(entity, true);

        drawPlayersCoins();

        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 10;
        int width = gamePanel.tileSize * 7;
        int height = gamePanel.tileSize;

        int itemIndex = getCurrentItemInventoryIndex(entityCurrentSlotCol, entityCurrentSlotRow);
        if (itemIndex < entity.inventory.size()) {

            drawSubWindow(x, y, width, height);
            g2.drawImage(getCoinImage(entity.inventory.get(itemIndex).itemPrice), x + 10, y + 8, 32, 32, null);
            g2.drawString("Cost: " + entity.inventory.get(itemIndex).itemPrice, x + 40, y + 33);
        }

        if (gamePanel.keyHandler.enterPressed) {
            tradeAction(entity, gamePanel.player, itemIndex);
        }
        drawTradeMessage("right");
    }

    private void trade_sell() {
        drawInventoryScreen(gamePanel.player, true);
        drawInventoryScreen(entity, false);

        drawPlayersCoins();

        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 6;
        int width = gamePanel.tileSize * 7;
        int height = gamePanel.tileSize;

        drawSubWindow(x, y, width, height);
        g2.drawImage(getCoinImage(entity.coin), x + 10, y + 8, 32, 32, null);
        g2.drawString("Merchant coins: " + entity.coin, x + 40, y + 33);

        int itemIndex = getCurrentItemInventoryIndex(playerCurrentSlotCol, playerCurrentSlotRow);
        if (itemIndex < gamePanel.player.inventory.size()) {
            x = 12 * gamePanel.tileSize;
            y += gamePanel.tileSize * 4;
            drawSubWindow(x, y, width, height);
            g2.drawImage(getCoinImage(gamePanel.player.inventory.get(itemIndex).itemPrice), x + 10, y + 8, 32, 32,
                    null);
            g2.drawString("Selling price: " + (int) (gamePanel.player.inventory.get(itemIndex).itemPrice * 0.7), x + 40,
                    y + 33);
        }

        if (gamePanel.keyHandler.enterPressed) {
            tradeAction(gamePanel.player, entity, itemIndex);
        }
        drawTradeMessage("left");
    }

    private void drawPlayersCoins() {
        int x = gamePanel.tileSize * 9;
        int y = gamePanel.tileSize;
        int width = gamePanel.tileSize * 2;
        int height = gamePanel.tileSize;

        drawSubWindow(x, y, width, height);

        int playerCoin = gamePanel.player.coin;

        g2.drawImage(getCoinImage(playerCoin), x + 10, y + 8, 32, 32, null);

        String price = Integer.toString(playerCoin);
        x = getXforAlignToRightText(price, x + gamePanel.tileSize - 7);
        g2.drawString(price, x + 40, y + 33);
    }

    private void tradeAction(Entity seller, Entity buyer, int itemIndex) {
        if (itemIndex < seller.inventory.size()) {

            int price = seller.inventory.get(itemIndex).itemPrice;
            if (buyer != gamePanel.player) {
                price = (int) (price * 0.7);
            }

            if (price > buyer.coin) {
                gamePanel.playSoundEffect(17);
                addMessage("Not enough coins!");
            } else if (buyer.inventoryMaxSize == buyer.inventory.size()) {
                gamePanel.playSoundEffect(17);
                addMessage("Not enough space in inventory!");
            } else {
                if (buyer == gamePanel.player) {
                    gamePanel.player.canObtainItem(seller.inventory.get(itemIndex));
                    gamePanel.playSoundEffect(16);
                    buyer.coin -= price;
                    seller.coin += price;
                } else if (seller == gamePanel.player) {
                    if (seller.inventory.get(itemIndex) != seller.currentMeleeWeapon
                            && seller.inventory.get(itemIndex) != seller.currentShield && seller.inventory.get(itemIndex) != seller.currentLightSource) {
                        if (seller.inventory.get(itemIndex).amount > 1) {
                            seller.inventory.get(itemIndex).amount -= 1;
                        } else {
                            seller.inventory.remove(itemIndex);
                        }
                        buyer.coin -= price;
                        seller.coin += price;
                        gamePanel.playSoundEffect(16);
                    } else {
                        gamePanel.playSoundEffect(17);
                        addMessage("You can't sell holding items!");
                    }
                } else {
                    buyer.coin -= price;
                    seller.coin += price;
                    gamePanel.playSoundEffect(16);
                }
            }
        }
    }

    private BufferedImage getCoinImage(int coinValue) {
        BufferedImage bImage = null;
        if (coinValue < 50) {
            bImage = bronzeCoin;
        } else if (coinValue >= 50 && coinValue < 100) {
            bImage = silverCoin;
        } else if (coinValue >= 100) {
            bImage = goldCoin;
        }
        return bImage;
    }

    private void drawTradeMessage(String displaySpace) {
        int messageX = 0;
        int messageY = gamePanel.tileSize * 8;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));

        if (!messages.isEmpty()) {
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i) != null) {
                    if (displaySpace.equals("left")) {
                        messageX = gamePanel.tileSize;
                    } else if (displaySpace.equals("right")) {
                        messageX = getXforAlignToRightText(messages.get(i), gamePanel.tileSize * 19);
                    }
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

    private void storageScreen() {
        int itemIndex = 999;

        if (subState == 1) {
            drawInventoryScreen(entity, true);
            drawInventoryScreen(gamePanel.player, false);
            if (gamePanel.keyHandler.enterPressed) {
                itemIndex = getCurrentItemInventoryIndex(entityCurrentSlotCol, entityCurrentSlotRow);
                storageItemTransition(entity, gamePanel.player, itemIndex);
                drawTradeMessage("right");
            }

        } else if (subState == 2) {
            drawInventoryScreen(entity, false);
            drawInventoryScreen(gamePanel.player, true);
            if (gamePanel.keyHandler.enterPressed) {
                itemIndex = getCurrentItemInventoryIndex(playerCurrentSlotCol, playerCurrentSlotRow);
                storageItemTransition(gamePanel.player, entity, itemIndex);
                drawTradeMessage("left");
            }
        }
    }

    private void storageItemTransition(Entity dispenser, Entity receiver, int itemIndex) {
        if (itemIndex < dispenser.inventory.size()) {
            if (receiver.inventoryMaxSize == receiver.inventory.size()) {
                gamePanel.playSoundEffect(17);
                addMessage("Not enough space in inventory!");
            } else {
                if (dispenser == gamePanel.player) {
                    if (dispenser.inventory.get(itemIndex) != dispenser.currentMeleeWeapon
                    && dispenser.inventory.get(itemIndex) != dispenser.currentShield && dispenser.inventory.get(itemIndex) != dispenser.currentLightSource) {
                        receiver.canObtainItem(dispenser.inventory.get(itemIndex));
                        dispenser.inventory.remove(itemIndex);
                    } else {
                        gamePanel.playSoundEffect(17);
                        addMessage("You can't move holding items!");
                    }

                } else {
                    receiver.canObtainItem(dispenser.inventory.get(itemIndex));
                    dispenser.inventory.remove(itemIndex);
                }
            }
        }
        gamePanel.keyHandler.enterPressed = false;
    }

    private void sleepScreen() {
        counter++;

        if (counter < 120) {
            gamePanel.environmentManager.lighting.filterAlpha += 0.01f;
            if (gamePanel.environmentManager.lighting.filterAlpha >= 1f) {
                gamePanel.environmentManager.lighting.filterAlpha = 1f;
            }
        } else if (counter >= 120) {
            gamePanel.environmentManager.lighting.filterAlpha -= 0.01f;
            if (gamePanel.environmentManager.lighting.filterAlpha <= 0f) {
                gamePanel.environmentManager.lighting.filterAlpha = 0f;
                counter = 0;
                gamePanel.environmentManager.lighting.dayState = gamePanel.environmentManager.lighting.DAY;
                gamePanel.environmentManager.lighting.dayCounter = 0;
                gamePanel.gameState = gamePanel.PLAY_STATE;
                gamePanel.player.getPlayerImage();
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

    private void drawTextWithShadow(String text, int x, int y) {
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
}
