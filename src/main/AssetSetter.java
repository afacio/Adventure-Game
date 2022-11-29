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

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int i = 0;

        gamePanel.obj[i] = new OBJ_Key(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 25;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 23;
        i++;
        gamePanel.obj[i] = new OBJ_Shield_Iron(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 19;
        i++;
        gamePanel.obj[i] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 18;
        i++;
        gamePanel.obj[i] = new OBJ_Coin_Silver(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 17;
        i++;
        gamePanel.obj[i] = new OBJ_Coin_Gold(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 16;
        i++;
        gamePanel.obj[i] = new OBJ_Axe(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 26;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 21;
        i++;
        gamePanel.obj[i] = new OBJ_Potion_Red(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 27;
        i++;
        gamePanel.obj[i] = new OBJ_Red_Book(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 24;
        i++;
        gamePanel.obj[i] = new OBJ_Blue_Book(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 23;
        i++;
        gamePanel.obj[i] = new OBJ_Green_Book(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 22;

    }
    public void setNPC(){
        gamePanel.npc[0] = new NPC_OldMan(gamePanel);
        gamePanel.npc[0].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = (double)gamePanel.tileSize * 22;
    }

    public void setMonster(){
        gamePanel.monster[0] = new MON_Slime(gamePanel);
        gamePanel.monster[0].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[0].worldY = (double)gamePanel.tileSize * 22;

        gamePanel.monster[1] = new MON_Slime(gamePanel);
        gamePanel.monster[1].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[1].worldY = (double)gamePanel.tileSize * 39;

        gamePanel.monster[2] = new MON_Slime(gamePanel);
        gamePanel.monster[2].worldX = (double)gamePanel.tileSize * 22;
        gamePanel.monster[2].worldY = (double)gamePanel.tileSize * 42;

        gamePanel.monster[3] = new MON_Stranger(gamePanel);
        gamePanel.monster[3].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.monster[3].worldY = (double)gamePanel.tileSize * 22;

    }

}
