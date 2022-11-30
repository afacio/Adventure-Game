package main;

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

        gamePanel.obj[mapNum][i] = new OBJ_Key(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 25;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 23;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Shield_Iron(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 19;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 18;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Coin_Silver(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 17;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Coin_Gold(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 16;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Axe(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 26;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 21;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Potion_Red(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 27;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Red_Book(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 24;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Blue_Book(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 23;
        i++;
        gamePanel.obj[mapNum][i] = new OBJ_Green_Book(gamePanel);
        gamePanel.obj[mapNum][i].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.obj[mapNum][i].worldY = (double)gamePanel.tileSize * 22;

    }
    public void setNPC(){
        int mapNum = 0;
        gamePanel.npc[mapNum][0] = new NPC_OldMan(gamePanel);
        gamePanel.npc[mapNum][0].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.npc[mapNum][0].worldY = (double)gamePanel.tileSize * 22;
    }

    public void setMonster(){
        int mapNum = 0;
        gamePanel.monster[mapNum][0] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][0].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[mapNum][0].worldY = (double)gamePanel.tileSize * 22;

        gamePanel.monster[mapNum][1] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][1].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[mapNum][1].worldY = (double)gamePanel.tileSize * 39;

        gamePanel.monster[mapNum][2] = new MON_Slime(gamePanel);
        gamePanel.monster[mapNum][2].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[mapNum][2].worldY = (double)gamePanel.tileSize * 42;

        gamePanel.monster[mapNum][3] = new MON_Stranger(gamePanel);
        gamePanel.monster[mapNum][3].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.monster[mapNum][3].worldY = (double)gamePanel.tileSize * 22;

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
