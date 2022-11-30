package main;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tiles_interactive.InteractiveTile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    static final int SCALE = 3;

    public final int tileSize = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public static final int MAX_SCREEN_COLUMN = 20;
    public static final int MAX_SCREEN_ROW = 12;
    public final int screenWidth = tileSize * MAX_SCREEN_COLUMN; // 960 pixels
    public final int screenHeight = tileSize * MAX_SCREEN_ROW; // 579 pixels
    // FOR FULL SCREEN SETTINGS
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    public Graphics2D g2;
    public boolean fullScreenOn = false;

    // WORLD SETTINGS
    public static final int MAX_WORLD_COLUMN = 50;
    public static final int MAX_WORLD_ROW = 50;
    public final int worldWidth = tileSize * MAX_WORLD_COLUMN;
    public final int worldHeight = tileSize * MAX_WORLD_ROW;

    // FPS
    static final int FPS = 60;

    // SYSTEM
    public KeyHandler keyHandler = new KeyHandler(this);
    TileManager tileManager = new TileManager(this);
    Sound music = new Sound();
    Sound soundEfect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public Entity[] obj = new Entity[50];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[50];
    public InteractiveTile[] interactiveTile = new InteractiveTile[50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOGUE_STATE = 3;
    public static final int CHARACTER_STATE = 4;
    public static final int OPTIONS_STATE = 5;
    public static final int CREATING_STATE = 6;

    public int gameState = TITLE_STATE;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
        gameState = PLAY_STATE;

    }

    public void setFullScreen() {

        // GENT LOCAL SCREEN DEVICE 
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { // Game loop

        double drawInterval = (double) 1000000000 / FPS; // 0.01666 seconsds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        setFullScreen();

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
            if(timer >= 1000000000) {
                // System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            // MONSTER
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (!monster[i].alive && !monster[i].dying) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    } else {
                        monster[i].update();
                    }
                }
            }
            // PROJECTILE
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    } else {
                        projectileList.get(i).update();
                    }
                }
            }
            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    } else {
                        particleList.get(i).update();
                    }
                }
            }
            // INTERACTIVE TILE
            for (int i = 0; i < interactiveTile.length; i++) {
                if (interactiveTile[i] != null) {
                    interactiveTile[i].update();
                }
            }
        }
        // if (gameState == PAUSE_STATE) {
        // }
    }

    public void drawToTempScreen() {
        // DEBUG
        long drawStart = 0;
        if (keyHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == TITLE_STATE) {
            ui.draw(g2);
        }

        // GAME SCREEN
        else {
            // TILE
            tileManager.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < interactiveTile.length; i++) {
                if (interactiveTile[i] != null) {
                    interactiveTile[i].draw(g2);
                }
            }

            // ADD ENTITIESTO THE LIST
            entityList.add(player);

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }
            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare((int) e1.worldY, (int) e2.worldY);
                }

            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g2);

            // DEBUG
            if (keyHandler.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.WHITE);
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("World X: " + player.worldX, x, y);
                y += lineHeight;
                g2.drawString("World Y: " + player.worldY, x, y);
                y += lineHeight;
                g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
                y += lineHeight;
                g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
                y += lineHeight;
                g2.drawString("Draw Time: " + passed, x, y);
            }
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEfect(int index) {
        soundEfect.setFile(index);
        soundEfect.play();
    }

    public void exitGame() {
        System.exit(0);
    }
}
