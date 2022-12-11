package tile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;


import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public BufferedImage atlas;
    public boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> tileCollisionStatus = new ArrayList<>();

    public int[][][] mapTileNumber;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        try( FileReader fr = new FileReader("res/tiles/tiles-v2/tiledata.txt");) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                fileNames.add(line);
                tileCollisionStatus.add(br.readLine());
            }
          
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tiles = new Tile[fileNames.size()];
        getTileImage();

        // try (FileReader fr = new FileReader("res/maps/worldmap.txt")) {
        //     BufferedReader br = new BufferedReader(fr);
        //     String line = br.readLine();
        //     String maxTile[] = line.split(" ");
        //     gamePanel.maxWorldCol = maxTile.length;
        //     gamePanel.maxWorldRow = maxTile.length;

        //     br.close();
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }

        mapTileNumber = new int[gamePanel.maxMap][gamePanel.MAX_WORLD_COLUMN][gamePanel.MAX_WORLD_ROW];

        loadMap("res/tiles/tiles-v2/worldmap.txt", 0);
        // loadMap("res/maps/indoor01.txt", 1);
        // loadMap("res/maps/dungeon01.txt", 2);
        // loadMap("res/maps/dungeon02.txt", 3);
    }

    private void getTileImage(){
           for(int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            boolean collision;
            if(tileCollisionStatus.get(i).equals("true")) {
                collision = true;
            } else {
                collision = false;
            }
            setup(i, fileName, collision);
        }
    }

    private void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(new File("res/tiles/tiles-v2/" + imageName));
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
            } 
            else if( 
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
        // if(drawPath) {
        //     g2.setColor(new Color(255,0,0,70));

        //     for(int i = 0; i < gamePanel.pathFinder.pathList.size(); i++) {
        //         int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
        //         int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
        //         int screenX = (int)(worldX - gamePanel.player.worldX + gamePanel.player.screenX);
        //         int screenY = (int)(worldY - gamePanel.player.worldY + gamePanel.player.screenY);
                
        //         g2.fillRect(screenX,screenY, gamePanel.tileSize, gamePanel.tileSize);
        //     }
        // }
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
