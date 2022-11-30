package tile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import helper.LoadSave;

import java.awt.image.BufferedImage;


import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public BufferedImage atlas;

    public int[][][] mapTileNumber;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tiles = new Tile[50];
        mapTileNumber = new int[gamePanel.maxMap][gamePanel.MAX_WORLD_COLUMN][gamePanel.MAX_WORLD_ROW];
        getTileImage();
        loadMap("src/res/maps/worldV3.txt", 0);
        loadMap("src/res/maps/interior01.txt", 1);
    }

    public void getTileImage(){
        setup(6, "New version/grass00", false);
        setup(7, "New version/grass00", false);
        setup(8, "New version/grass00", false);
        setup(9, "New version/grass00", false);

        setup(10, "New version/grass00", false);
        setup(11, "New version/grass01", false);

        setup(12, "New version/water00", true);
        setup(13, "New version/water01", true);
        setup(14, "New version/water02", true);
        setup(15, "New version/water03", true);
        setup(16, "New version/water04", true);
        setup(17, "New version/water05", true);
        setup(18, "New version/water06", true);
        setup(19, "New version/water07", true);
        setup(20, "New version/water08", true);
        setup(21, "New version/water09", true);
        setup(22, "New version/water10", true);
        setup(23, "New version/water11", true);
        setup(24, "New version/water12", true);
        setup(25, "New version/water13", true);

        setup(26, "New version/road00", false);
        setup(27, "New version/road01", false);
        setup(28, "New version/road02", false);
        setup(29, "New version/road03", false);
        setup(30, "New version/road04", false);
        setup(31, "New version/road05", false);
        setup(32, "New version/road06", false);
        setup(33, "New version/road07", false);
        setup(34, "New version/road08", false);
        setup(35, "New version/road09", false);
        setup(36, "New version/road10", false);
        setup(37, "New version/road11", false);
        setup(38, "New version/road12", false);
        setup(39, "New version/earth", false);
        setup(40, "New version/wall", true);
        setup(41, "New version/tree", true);
        setup(42, "New version/hut", false);
        setup(43, "New version/floor01", false);
        setup(44, "New version/table01", true);
    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(new File("src/res/tiles/" + imageName + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldColumn = 0;
        int worldRow = 0;

        while(worldColumn< gamePanel.MAX_WORLD_COLUMN && worldRow < gamePanel.MAX_WORLD_ROW){

            int tileNumber = mapTileNumber[gamePanel.currentMap][worldColumn][worldRow];

            int worldX = worldColumn * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;

            double screenX = (worldX - gamePanel.player.worldX + gamePanel.player.screenX);
            double screenY = (worldY - gamePanel.player.worldY + gamePanel.player.screenY);

            // Stop moving the camera ate the edge

            if(gamePanel.player.screenX > gamePanel.player.worldX){
                screenX = worldX;
            }
            if(gamePanel.player.screenY > gamePanel.player.worldY){
                screenY = worldY;
            }
            int rightOffset = gamePanel.screenWidth - gamePanel.player.screenX;
            if(rightOffset > gamePanel.worldWidth - gamePanel.player.worldX){
                screenX = gamePanel.screenWidth - (gamePanel.worldWidth - worldX);
            }
            int bottomOffset = gamePanel.screenHeight - gamePanel.player.screenY;
            if(bottomOffset > gamePanel.worldHeight- gamePanel.player.worldY){
                screenY = gamePanel.screenHeight - (gamePanel.worldHeight - worldY);
            }

            if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
               worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX && 
               worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
               worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ){

                g2.drawImage(tiles[tileNumber].image, (int) screenX, (int) screenY, null);
            } else if( 
                    gamePanel.player.screenX > gamePanel.player.worldX || 
                    gamePanel.player.screenY > gamePanel.player.worldY || 
                    rightOffset > gamePanel.worldWidth - gamePanel.player.worldX ||
                    bottomOffset > gamePanel.worldHeight- gamePanel.player.worldY){
                        g2.drawImage(tiles[tileNumber].image, (int) screenX, (int) screenY, null);
                    }

            worldColumn++;

            if( worldColumn == gamePanel.MAX_WORLD_COLUMN){
                worldColumn = 0;
                worldRow++;
            }
        }  
    }

    public void loadMap(String filePath, int map){
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            int column = 0;
            int row = 0;

            while(column < gamePanel.MAX_WORLD_COLUMN && row < gamePanel.MAX_WORLD_ROW){
                String line = br.readLine();
                while(column < gamePanel.MAX_WORLD_COLUMN){
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumber[map][column][row] = number;
                    column++;
                }
                if(column == gamePanel.MAX_WORLD_COLUMN){
                    column = 0;
                    row++;
                }
            }
            fr.close();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
