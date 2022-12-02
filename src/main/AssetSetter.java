package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_Slime;
import monster.MON_Stranger;
import object.OBJ_Axe;
import object.OBJ_Blue_Book;
import object.OBJ_Coin_Bronze;
import object.OBJ_Coin_Gold;
import object.OBJ_Coin_Silver;
import object.OBJ_Green_Book;
import object.OBJ_Red_Book;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Iron;
import tiles_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        gamePanel.obj[mapNum][i] = new OBJ_Axe(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 26;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 21;

    }
    public void setNPC(){
        int mapNum = 0;
        int i = 0;
        gamePanel.npc[mapNum][i] = new NPC_OldMan(gamePanel);
        gamePanel.npc[mapNum][i].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][i].worldY = (double)gamePanel.tileSize * 22;
        
        mapNum = 1;
        gamePanel.npc[mapNum][i] = new NPC_Merchant(gamePanel);
        gamePanel.npc[mapNum][i].worldX = (double)gamePanel.tileSize * 12;
        gamePanel.npc[mapNum][i].worldY = (double)gamePanel.tileSize * 7;
    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 41;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 39;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 42;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 36;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 36;
        i++; 
        gamePanel.monster[mapNum][i] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][i].worldX = (double)gamePanel.tileSize * 37;
        gamePanel.monster[mapNum][i].worldY = (double)gamePanel.tileSize * 37;

    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 27, 12);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 28, 12);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 29, 12);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 30, 12);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 31, 12);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 32, 12);
        i++;
        gamePanel.interactiveTile[mapNum][i] = new IT_DryTree(gamePanel, 33, 12);
         
    }
}
