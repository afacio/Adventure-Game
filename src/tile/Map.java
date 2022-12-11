package tile;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Map extends TileManager{

    GamePanel gamePanel;
    BufferedImage worldMap[];
    public boolean miniMapOn = false;

    public Map(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        createWorldMap();
    }

    private void createWorldMap() {
        worldMap = new BufferedImage[gamePanel.maxMap];
        int worldMapWidth = gamePanel.tileSize * gamePanel.MAX_WORLD_COLUMN; 
        int worldMapHeight = gamePanel.tileSize * gamePanel.MAX_WORLD_ROW; 

        for(int i = 0; i < gamePanel.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while(col < gamePanel.MAX_WORLD_COLUMN && row < gamePanel.MAX_WORLD_ROW) {
                int tileNum = mapTileNumber[i][col][row];
                int x = gamePanel.tileSize * col;
                int y = gamePanel.tileSize * row;
                g2.drawImage(tiles[tileNum].image, x, y, null);

                col++;
                if(col == gamePanel.MAX_WORLD_COLUMN) {
                    col = 0;
                    row++;
                    if(row == gamePanel.MAX_WORLD_ROW) {
                        break;
                    }
                }

            }
            g2.dispose();
        }
    }

    public void drawFullMapScreen(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int width = 500;
        int height = 500;
        int x = gamePanel.screenWidth/2 - width/2;
        int y = gamePanel.screenHeight/2 - height/2;

        g2.drawImage(worldMap[gamePanel.currentMap], x, y, width, height, null);

        double scale = (double) gamePanel.tileSize * gamePanel.MAX_WORLD_COLUMN / width;
        int playerX = (int)(x + (gamePanel.player.worldX / scale));
        int playerY = (int)(y + (gamePanel.player.worldY / scale));
        int playerSize = (int)(gamePanel.tileSize/3);

        g2.drawImage(gamePanel.player.down1, playerX-3, playerY-3, playerSize, playerSize, null);

        g2.setFont(g2.getFont().deriveFont(32f));
        g2.setColor(Color.WHITE);
        g2.drawString("Press M to close", 750, 550);
    }

    public void drawMiniMap(Graphics2D g2) {
        if(miniMapOn) {

            int width = 200;
            int height = 200;
            int x = gamePanel.screenWidth - width - 50;
            int y = 50;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, width, height);
            g2.drawImage(worldMap[gamePanel.currentMap], x, y, width, height, null);

            double scale = (double) gamePanel.tileSize * gamePanel.MAX_WORLD_COLUMN / width;
            int playerX = (int)(x + (gamePanel.player.worldX / scale));
            int playerY = (int)(y + (gamePanel.player.worldY / scale));
            int playerSize = (int)(gamePanel.tileSize/3);

            g2.drawImage(gamePanel.player.down1, playerX-6, playerY-6, playerSize, playerSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }
    
}
