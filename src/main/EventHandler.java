package main;

import entity.Entity;

public class EventHandler {

    GamePanel gamePanel;
    EventRectangle[][][] eventRect = null;

    int previousEventX;
    int previousEventY;
    boolean canTouchEvent = true;
    int tempMap;
    int tempCol;
    int tempRow;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new EventRectangle[gamePanel.maxMap][gamePanel.MAX_WORLD_COLUMN][gamePanel.MAX_WORLD_ROW];
        fillEventArray();

    }

    private void fillEventArray() {
        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gamePanel.maxMap && col < gamePanel.MAX_WORLD_COLUMN && row < gamePanel.MAX_WORLD_ROW) {
            eventRect[map][col][row] = new EventRectangle();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gamePanel.MAX_WORLD_COLUMN) {
                col = 0;
                row++;

                if (row == gamePanel.MAX_WORLD_ROW) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {

        // Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs((int) gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs((int) gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gamePanel.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            if (hit(0,27, 17, "right")) {
                damagePit(gamePanel.DIALOGUE_STATE);
            }
            else if (hit(0, 23, 12, "up")) {
                healingPool(gamePanel.DIALOGUE_STATE);
            }
            else if (hit(0, 10,39, "up")) {
                teleport(1, 12, 13);
            }
            else if (hit(1, 12, 13, "down")) {
                teleport(0, 10,39);
            }
            else if (hit(1, 12, 9, "up")) {
                trade(gamePanel.npc[1][0]);
            }
        }
    }

    public boolean hit(int map, int col, int row, String requiredDirection) {

        boolean hit = false;

        if (map == gamePanel.currentMap) {

            gamePanel.player.solidArea.x = (int) (gamePanel.player.worldX) + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = (int) (gamePanel.player.worldY) + gamePanel.player.solidArea.y;
            eventRect[map][col][row].x = col * gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.tileSize + eventRect[map][col][row].y;

            if (gamePanel.player.solidArea.intersects(eventRect[map][col][row])
                    && !eventRect[map][col][row].eventDone) {
                if (gamePanel.player.direction.contentEquals(requiredDirection)
                        || requiredDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = (int) gamePanel.player.worldX;
                    previousEventY = (int) gamePanel.player.worldY;
                }
            }

            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    private void damagePit(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEffect(6);
        gamePanel.ui.currentDialogue = "You fall into a pit!";
        gamePanel.player.health--;
        canTouchEvent = false;

    }

    private void healingPool(int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(2);
            gamePanel.ui.currentDialogue = "You drink the water.\nYout life and mana has been recovered.";
            gamePanel.player.health = gamePanel.player.maxHealth;
            gamePanel.player.mana = gamePanel.player.maxMana;
            gamePanel.assetSetter.setMonster();
        }
    }

    private void teleport(int map, int col, int row) {
        gamePanel.gameState = gamePanel.MAP_TRANSITION_STATE;
        tempCol = col;
        tempRow = row;
        tempMap = map;
        canTouchEvent = false;
        gamePanel.playSoundEffect(15);
    }

    private void trade(Entity entity){
        if(gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gamePanel.TRADE_STATE;
            gamePanel.player.attackCanceled = true;
            entity.speak();
        }
    }

}
