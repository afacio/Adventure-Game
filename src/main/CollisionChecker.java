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

        int tileNum1, tileNum2;

        switch (entity.direction) {
        case "up":
            entityTopRow = (int) ((entityTopWorldY - entity.speed) / gamePanel.tileSize);
            tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
            if (gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "down":
            entityBottomRow = (int) ((entityBottomWorldY + entity.speed) / gamePanel.tileSize);
            tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
            tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
            if (gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "left":
            entityLeftCol = (int) ((entityLeftWorldX - entity.speed) / gamePanel.tileSize);
            tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
            if (gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "right":
            entityRightCol = (int) ((entityRightWorldX + entity.speed) / gamePanel.tileSize);
            tileNum1 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
            if (gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);

                // Get the object's solid area position
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX + gamePanel.obj[i].solidArea.x;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY + gamePanel.obj[i].solidArea.y;

                switch (entity.direction) {
                case "up":
                    entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                        if (gamePanel.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                        if (gamePanel.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                        if (gamePanel.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(gamePanel.obj[i].solidArea)) {
                        if (gamePanel.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (player) {
                            index = i;
                        }
                    }
                    break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
                gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    //NPC OR MONSTER COLLISION
    public int checkEntityCollision(Entity entity, Entity[] targets){

        int index = 999;

        for (int i = 0; i < targets.length; i++) {
            if (targets[i] != null && entity.id != targets[i].id) {

                // Get entity's solid area position
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);

                // Get the object's solid area position
                targets[i].solidArea.x = (int)targets[i].worldX + targets[i].solidArea.x;
                targets[i].solidArea.y = (int)targets[i].worldY + targets[i].solidArea.y;

                switch (entity.direction) {
                case "up":
                    entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(targets[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                    }
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(targets[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                    }
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(targets[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                    }
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(targets[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                    }
                    break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                targets[i].solidArea.x = targets[i].solidAreaDefaultX;
                targets[i].solidArea.y = targets[i].solidAreaDefaultY;
            }
        }
        return index;

    }

    public void checkPlayerCollision(Entity entity){

         // Get entity's solid area position
         entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
         entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);

         // Get the object's solid area position
         gamePanel.player.solidArea.x = (int)gamePanel.player.worldX + gamePanel.player.solidArea.x;
         gamePanel.player.solidArea.y = (int)gamePanel.player.worldY + gamePanel.player.solidArea.y;

         switch (entity.direction) {
         case "up":
             entity.solidArea.y -= entity.speed;
             if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                     entity.collisionOn = true;
             }
             break;
         case "down":
             entity.solidArea.y += entity.speed;
             if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                     entity.collisionOn = true;
             }
             break;
         case "left":
             entity.solidArea.x -= entity.speed;
             if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                     entity.collisionOn = true;
             }
             break;
         case "right":
             entity.solidArea.x += entity.speed;
             if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                     entity.collisionOn = true;
             }
             break;
         }
         entity.solidArea.x = entity.solidAreaDefaultX;
         entity.solidArea.y = entity.solidAreaDefaultY;
         gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
         gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

    }
}
