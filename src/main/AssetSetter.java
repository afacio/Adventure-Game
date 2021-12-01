package main;

import entity.NPC_Stranger;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
       
    }
    public void setNPC(){
        gamePanel.npc[0] = new NPC_Stranger(gamePanel, 1L);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;

        gamePanel.npc[1] = new NPC_Stranger(gamePanel, 2L);
        gamePanel.npc[1].worldX = gamePanel.tileSize * 22;
        gamePanel.npc[1].worldY = gamePanel.tileSize * 21;

        gamePanel.npc[2] = new NPC_Stranger(gamePanel, 3L);
        gamePanel.npc[2].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[2].worldY = gamePanel.tileSize * 22;

        gamePanel.npc[3] = new NPC_Stranger(gamePanel, 4L);
        gamePanel.npc[3].worldX = gamePanel.tileSize * 22;
        gamePanel.npc[3].worldY = gamePanel.tileSize * 22;
    }

}
