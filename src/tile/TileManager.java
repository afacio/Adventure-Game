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

    public int mapTileNumber[][];

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tiles = new Tile[50];
        mapTileNumber = new int[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("src/res/maps/world02.txt");
    }

    private void loadAtlas(){
        atlas = LoadSave.getAtlas();
    }

    public void getTileImage(){
        setup(0, "old/grass", false);
        setup(1, "old/wall", true);
        setup(2, "old/water", true);
        setup(3, "old/earth", false);
        setup(4, "old/tree", true);
        setup(5, "old/sand", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);

        setup(10, "grass00", false);
        setup(11, "grass01", false);

        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);

        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);

        setup(39, "earth", false);

        setup(40, "wall", true);

        setup(41, "tree", true);
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

        while(worldColumn< gamePanel.maxWorldColumn && worldRow < gamePanel.maxWorldRow){

            int tileNumber = mapTileNumber[worldColumn][worldRow];

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

            if( worldColumn == gamePanel.maxWorldColumn){
                worldColumn = 0;
                worldRow++;
            }
        }  
    }

    public void loadMap(String filePath){
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            int column = 0;
            int row = 0;

            while(column < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow){
                String line = br.readLine();
                while(column < gamePanel.maxWorldColumn){
                    String numbers[] = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = number;
                    column++;
                }
                if(column == gamePanel.maxWorldColumn){
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
