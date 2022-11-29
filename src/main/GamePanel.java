package main;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    static final int SCALE = 3;

    public final int tileSize = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public static final int MAX_SCREEN_COLUMN = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public final int screenWidth = tileSize * MAX_SCREEN_COLUMN; // 768 pixels
    public final int screenHeight = tileSize * MAX_SCREEN_ROW; // 579 pixels

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
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityListy = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();

    // GAME STATE       
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOGUE_STATE = 3;
    public static final int CHARACTER_STATE = 4;
    public static final int CREATING_STATE = 5;
    
    public int gameState = TITLE_STATE;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();   
        assetSetter.setMonster();   
        // playMusic(0);
        gameState = PLAY_STATE;
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

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if(gameState == PLAY_STATE){
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
                    if(!monster[i].alive && !monster[i].dying){
                        monster[i] = null;
                    } else {
                        monster[i].update();
                    }
                }
            }      
            // PROJECTILE
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    } else {
                        projectileList.get(i).update();
                    }
                }
            }      
        }
        if(gameState == PAUSE_STATE){
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        // DEBUG

        long drawStart = 0;
        if(keyHandler.checkDrawTime){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == TITLE_STATE){
            ui.draw(g2); 
        }

        // GAME SCREEN
        else {
            // TILE
            tileManager.draw(g2);

            // ADD ENTITIESTO THE LIST
            entityListy.add(player);

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityListy.add(obj[i]);
                }
            }
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityListy.add(npc[i]);
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityListy.add(monster[i]);
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityListy.add(projectileList.get(i));
                }
            }
            // SORT
            Collections.sort(entityListy, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare((int) e1.worldY, (int) e2.worldY);
                    return result;
                }
                
            });
           
            // DRAW ENTITIES
            for(int i = 0; i < entityListy.size(); i++){
                entityListy.get(i).draw(g2);
            }
           
            // EMPTY ENTITY LIST
            entityListy.clear();
            
    
            // UI
            ui.draw(g2); 
        }
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

    public void exitGame(){
        System.exit(0);
    }
}
