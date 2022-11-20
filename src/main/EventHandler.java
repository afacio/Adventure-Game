package main;

public class EventHandler {

    GamePanel gamePanel;
    EventRectangle[][] eventRect = null;

    int previousEventX;
    int previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new EventRectangle[gamePanel.MAX_WORLD_COLUMN][gamePanel.MAX_WORLD_ROW];
        fillEventArray();

    }

    private void fillEventArray() {
        int col = 0;
        int row = 0;
        while (col < gamePanel.MAX_WORLD_COLUMN && row < gamePanel.MAX_WORLD_ROW) {
            eventRect[col][row] = new EventRectangle();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gamePanel.MAX_WORLD_COLUMN) {
                col = 0;
                row++;
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
            if (hit(27, 17, "right")) {
                damagePit(gamePanel.DIALOGUE_STATE);
            }
            if (hit(23, 12, "up")) {
                healingPool(gamePanel.DIALOGUE_STATE);
            }
        }
    }

    public boolean hit(int col, int row, String requiredDirection) {

        boolean hit = false;

        gamePanel.player.solidArea.x = (int) (gamePanel.player.worldX) + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = (int) (gamePanel.player.worldY) + gamePanel.player.solidArea.y;
        eventRect[col][row].x = col * gamePanel.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.tileSize + eventRect[col][row].y;

        if (gamePanel.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gamePanel.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                hit = true;

                previousEventX = (int) gamePanel.player.worldX;
                previousEventY = (int) gamePanel.player.worldY;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    private void damagePit(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.playSoundEfect(6);
        gamePanel.ui.currentDialogue = "You fall into a pit!";
        gamePanel.player.health--;
        canTouchEvent = false;

    }

    private void healingPool(int gameState) {

        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEfect(2);
            gamePanel.ui.currentDialogue = "You drink the water.\nYout life has been recovered.";
            gamePanel.player.health = gamePanel.player.maxHealth;
        }
    }

}
