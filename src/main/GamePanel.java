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
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public int tileSize = originalTileSize * scale; // 48x48 tile
    public int maxScreenColumn = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenColumn; // 768 pixels
    public int screenHeight = tileSize * maxScreenRow; // 579 pixels

    // WORLD SETTINGS
    public final int maxWorldColumn = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldColumn;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    final int FPS = 60;

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
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityListy = new ArrayList<>();

    // GAME STATE       
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int creatingState = 4;
    
    public int gameState = titleState;

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
        playMusic(0);
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { // Game loop

        double drawInterval = 1000000000 / FPS; // 0.01666 seconsds
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
        if(gameState == playState){
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
                    monster[i].update();
                }
            }      
        }
        if(gameState == pauseState){
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
        if(gameState == titleState){
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
            for(int i = 0; i < entityListy.size(); i++){
                entityListy.remove(i);
            }
    
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
