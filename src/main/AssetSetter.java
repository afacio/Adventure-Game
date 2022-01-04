package main;

import entity.NPC_Stranger;
import monster.MON_Slime;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {

        // gamePanel.obj[0] = new OBJ_Key(gamePanel);
        // gamePanel.obj[0].worldX = gamePanel.tileSize * 22;
        // gamePanel.obj[0].worldY = gamePanel.tileSize * 22;
       
    }
    public void setNPC(){
        gamePanel.npc[0] = new NPC_Stranger(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 22;
    }

    public void setMonster(){
        gamePanel.monster[0] = new MON_Slime(gamePanel);
        gamePanel.monster[0].worldX = gamePanel.tileSize * 22;
        gamePanel.monster[0].worldY = gamePanel.tileSize * 22;

    }

}
