package main;

import java.util.ArrayList;

import entity.Entity;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_Slime;
import object.OBJ_Axe;
import object.OBJ_Chest;
import object.OBJ_Potion_Red;
import object.OBJ_Sign;
import tiles_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        ArrayList<Entity> inv = new ArrayList<>();
        gamePanel.obj[mapNum][i] = new OBJ_Sign(gamePanel, "To do an attack/interaction use „ENTER”.");
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 19;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 15;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Sign(gamePanel, "To use your magic power use „F”.");
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 17;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 30;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Sign(gamePanel, "To see your inventory and character stats use „I”.");
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 28;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 25;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Sign(gamePanel, "Cut dry tree using the axe.");
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 36;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 37;
        i++;
        inv.add(new OBJ_Potion_Red(gamePanel));
        gamePanel.obj[mapNum][i] = new OBJ_Chest(gamePanel, inv);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 27;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 25;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Axe(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 31;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 36;
    }
    public void setNPC(){
        int mapNum = 0;
        int i = 0;
        gamePanel.npc[mapNum][i] = new NPC_OldMan(gamePanel);
        gamePanel.npc[mapNum][i].worldX = (double)gamePanel.tileSize * 14;
        gamePanel.npc[mapNum][i].worldY = (double)gamePanel.tileSize * 11;
        
        // mapNum = 1;
        // gamePanel.npc[mapNum][i] = new NPC_Merchant(gamePanel);
        // gamePanel.npc[mapNum][i].worldX = (double)gamePanel.tileSize * 12;
        // gamePanel.npc[mapNum][i].worldY = (double)gamePanel.tileSize * 7;
    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 22;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 18;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 35;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 35;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 26;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 40;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 31;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 37;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 34;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 27;

    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 35, 36);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 34, 34);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 35, 28);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 28);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 32, 20);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 16);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 34, 10);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 38, 13);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 37, 17);
         
    }
}
