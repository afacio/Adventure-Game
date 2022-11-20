package main;

import entity.NPC_OldMan;
import entity.NPC_Stranger;
import monster.MON_Slime;
import object.OBJ_Key;

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
        gamePanel.obj[i] = new OBJ_Key(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 19;
        i++;
        gamePanel.obj[i] = new OBJ_Key(gamePanel);
        gamePanel.obj[i].worldX = (double)gamePanel.tileSize * 26;
        gamePanel.obj[i].worldY = (double)gamePanel.tileSize * 21;

    }
    public void setNPC(){
        gamePanel.npc[0] = new NPC_OldMan(gamePanel);
        gamePanel.npc[0].worldX = (double)gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = (double)gamePanel.tileSize * 22;

        gamePanel.npc[1] = new NPC_Stranger(gamePanel);
        gamePanel.npc[1].worldX = (double)gamePanel.tileSize * 23;
        gamePanel.npc[1].worldY = (double)gamePanel.tileSize * 22;
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

    }

}
