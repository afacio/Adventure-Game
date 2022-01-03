package main;

import entity.NPC_Stranger;
import object.OBJ_Door;
import object.OBJ_Key;

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
        gamePanel.npc[0].worldX = gamePanel.tileSize * 22;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 22;

        // gamePanel.npc[1] = new NPC_Stranger(gamePanel);
        // gamePanel.npc[1].worldX = gamePanel.tileSize * 22;
        // gamePanel.npc[1].worldY = gamePanel.tileSize * 21;

        // gamePanel.npc[2] = new NPC_Stranger(gamePanel);
        // gamePanel.npc[2].worldX = gamePanel.tileSize * 21;
        // gamePanel.npc[2].worldY = gamePanel.tileSize * 24;

        // gamePanel.npc[3] = new NPC_Stranger(gamePanel);
        // gamePanel.npc[3].worldX = gamePanel.tileSize * 22;
        // gamePanel.npc[3].worldY = gamePanel.tileSize * 22;
    }

}
