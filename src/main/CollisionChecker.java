package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {

        double entityLeftWorldX = entity.worldX + entity.solidArea.x;
        double entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        double entityTopWorldY = entity.worldY + entity.solidArea.y;
        double entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = (int) (entityLeftWorldX / gamePanel.tileSize);
        int entityRightCol = (int) (entityRightWorldX / gamePanel.tileSize);
        int entityTopRow = (int) (entityTopWorldY / gamePanel.tileSize);
        int entityBottomRow = (int) (entityBottomWorldY / gamePanel.tileSize);

        int tileNum1;
        int tileNum2;

        String direction = entity.direction;
        if(entity.knockBack) {
            direction = entity.knockbackDirection;
        }

        switch (direction) {
            case "up":
                entityTopRow = (int) ((entityTopWorldY - entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (int) ((entityBottomWorldY + entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (int) ((entityLeftWorldX - entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (int) ((entityRightWorldX + entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            default:
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gamePanel.obj[1].length; i++) {
            if (gamePanel.obj[gamePanel.currentMap][i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);

                // Get the object's solid area position
                gamePanel.obj[gamePanel.currentMap][i].solidArea.x = (int) gamePanel.obj[gamePanel.currentMap][i].worldX + gamePanel.obj[gamePanel.currentMap][i].solidArea.x;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.y = (int) gamePanel.obj[gamePanel.currentMap][i].worldY + gamePanel.obj[gamePanel.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    default:
                        break;
                }

                if (entity.solidArea.intersects(gamePanel.obj[gamePanel.currentMap][i].solidArea)) {
                    if (gamePanel.obj[gamePanel.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.x = gamePanel.obj[gamePanel.currentMap][i].solidAreaDefaultX;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.y = gamePanel.obj[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // NPC OR MONSTER COLLISION
    public int checkEntityCollision(Entity entity, Entity[][] targets) {

        int index = 999;

        String direction = entity.direction;
        if(entity.knockBack) {
            direction = entity.knockbackDirection;
        }

        for (int i = 0; i < targets[1].length; i++) {
            if (targets[gamePanel.currentMap][i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);

                // Get the object's solid area position
                targets[gamePanel.currentMap][i].solidArea.x = (int) targets[gamePanel.currentMap][i].worldX + targets[gamePanel.currentMap][i].solidArea.x;
                targets[gamePanel.currentMap][i].solidArea.y = (int) targets[gamePanel.currentMap][i].worldY + targets[gamePanel.currentMap][i].solidArea.y;

                switch (direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    default:
                        break;
                }

                if (entity.solidArea.intersects(targets[gamePanel.currentMap][i].solidArea)) {
                    if (targets[gamePanel.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                targets[gamePanel.currentMap][i].solidArea.x = targets[gamePanel.currentMap][i].solidAreaDefaultX;
                targets[gamePanel.currentMap][i].solidArea.y = targets[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;

    }

    public boolean checkPlayerCollision(Entity entity) {

        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
        entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);

        // Get the object's solid area position
        gamePanel.player.solidArea.x = (int) gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = (int) gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
            default:
                break;
        }

        if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
