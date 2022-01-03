package main;

public class EventHandler {

    GamePanel gamePanel;
    EventRectangle eventRect[][] = null;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new EventRectangle[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
        fillEventRectArray();

    }

    private void fillEventRectArray() {
        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
            eventRect[col][row] = new EventRectangle();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            System.out.print(col);
            System.out.print(" ");

            System.out.print(row);
            System.out.print("\n");

            col++;
            if (col == gamePanel.maxWorldColumn) {
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
                damagePit(27, 17, gamePanel.dialogueState);
            }
            if (hit(23, 12, "up")) {
                healingPool(gamePanel.dialogueState);
            }
        }
    }

    public boolean hit(int col, int row, String requiredDirection) {

        boolean hit = false;

        gamePanel.player.solidArea.x = (int) (gamePanel.player.worldX) + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = (int) (gamePanel.player.worldY) + gamePanel.player.solidArea.y;
        System.out.print(eventRect.length);
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

    private void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into a pit!";
        gamePanel.player.health--;
        canTouchEvent = false;

    }

    private void healingPool(int gameState) {

        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.ui.currentDialogue = "You drink the water.\nYout life has been recovered.";
            gamePanel.player.health = gamePanel.player.maxHealth;
        }
    }

}
