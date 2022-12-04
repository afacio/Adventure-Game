package entity;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Iron;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gamePanel) {
        super(gamePanel);
        
        direction = "down";
        speed = 0;
        type = NPC_TYPE;
        animationRefresh = 30;
        getImage();
        setDialogue();
        setInventoryItems();
        coin = 50;
    }

    public void getImage() {
        up1 = setup("/npc/merchant/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/npc/merchant/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);                   
        down1 = setup("/npc/merchant/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/npc/merchant/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);                   
        left1 = setup("/npc/merchant/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/npc/merchant/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);                   
        right1 = setup("/npc/merchant/merchant_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/npc/merchant/merchant_down_2", gamePanel.tileSize, gamePanel.tileSize);                   
    }
    
    public void setDialogue(){
        dialogues[0] = "He he, so you found me.\nI have some good stuff for you.\nDo you want to trade?";
    }

    public void setInventoryItems() {
        inventory.clear();
        inventory.add(new OBJ_Shield_Wood(gamePanel));
        inventory.add(new OBJ_Shield_Iron(gamePanel));
        inventory.add(new OBJ_Sword_Normal(gamePanel));
        inventory.add(new OBJ_Axe(gamePanel));
        inventory.add(new OBJ_Potion_Red(gamePanel));
        inventory.add(new OBJ_Key(gamePanel));
    }

    @Override
    public void speak(){
        super.speak();
        gamePanel.gameState = gamePanel.TRADE_STATE;
        gamePanel.ui.entity = this;
    }
    
}
