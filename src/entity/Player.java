package entity;

import main.KeyHandler;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyHandler;

    public int health = 100;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;
    public boolean attackCanceled = false;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventoryMaxSize = gamePanel.ui.SLOT_MAX_COL * gamePanel.ui.SLOT_MAX_ROW;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = (double) gamePanel.tileSize * 23;
        worldY = (double) gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";

        maxHealth = 6;
        health = maxHealth;

        level = 1;
        strenght = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;

        currentWeapon = new OBJ_Sword_Normal(gamePanel);
        currentShield = new OBJ_Shield_Wood(gamePanel);
        attack = getAttack();
        defense = getDefense();

        setInventoryItems();
    }
    
    private void setInventoryItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gamePanel));
    }

    private int getAttack() {
        attackArea = currentWeapon.attackArea;
        attack = strenght * currentWeapon.attackValue;
        return attack;
    }

    private int getDefense() {
        defense = dexterity * currentShield.defenseValue;
        return defense;
    }

    private void getPlayerImage() {
        up1 = setup("/player/old_player/Walking sprites/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/old_player/Walking sprites/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/old_player/Walking sprites/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/old_player/Walking sprites/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/old_player/Walking sprites/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/old_player/Walking sprites/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/old_player/Walking sprites/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/old_player/Walking sprites/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    private void getPlayerAttackImage() {
        if(currentWeapon.type == SWORD_TYPE) {
            attackUp1 = setup("/player/old_player/Attacking sprites/boy_attack_up_1", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackUp2 = setup("/player/old_player/Attacking sprites/boy_attack_up_2", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackDown1 = setup("/player/old_player/Attacking sprites/boy_attack_down_1", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackDown2 = setup("/player/old_player/Attacking sprites/boy_attack_down_2", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackLeft1 = setup("/player/old_player/Attacking sprites/boy_attack_left_1", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
            attackLeft2 = setup("/player/old_player/Attacking sprites/boy_attack_left_2", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
            attackRight1 = setup("/player/old_player/Attacking sprites/boy_attack_right_1", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
            attackRight2 = setup("/player/old_player/Attacking sprites/boy_attack_right_2", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
        }
        if(currentWeapon.type == AXE_TYPE) {
            attackUp1 = setup("/player/old_player/Attacking sprites/boy_axe_up_1", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackUp2 = setup("/player/old_player/Attacking sprites/boy_axe_up_2", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackDown1 = setup("/player/old_player/Attacking sprites/boy_axe_down_1", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackDown2 = setup("/player/old_player/Attacking sprites/boy_axe_down_2", gamePanel.tileSize,
                    2 * gamePanel.tileSize);
            attackLeft1 = setup("/player/old_player/Attacking sprites/boy_axe_left_1", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
            attackLeft2 = setup("/player/old_player/Attacking sprites/boy_axe_left_2", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
            attackRight1 = setup("/player/old_player/Attacking sprites/boy_axe_right_1", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
            attackRight2 = setup("/player/old_player/Attacking sprites/boy_axe_right_2", 2 * gamePanel.tileSize,
                    gamePanel.tileSize);
        }
    }

    public void update() {

        if (attacking) {
            attacking();
        } else if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed
                || keyHandler.enterPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            }
            if (keyHandler.downPressed) {
                direction = "down";
            }
            if (keyHandler.leftPressed) {
                direction = "left";
            }
            if (keyHandler.rightPressed) {
                direction = "right";
            }

            collisionOn = false;

            // CHECK TILE COLLISION
            gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECTS COLLISION
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // // CHECK NPC COLLISION
            int npcIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npc);
            interactNPC(npcIndex);

            // // CHECK MONSTER COLLISION
            int monsterIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monster);
            monsterContact(monsterIndex);

            // CHECK EVENT
            gamePanel.eventHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyHandler.enterPressed) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    default:
                        break;
                }
            }

            if (keyHandler.enterPressed && !attackCanceled) {
                gamePanel.playSoundEfect(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gamePanel.keyHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNumber = 1;
                standCounter = 0;
            }
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    private void attacking() {

        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 15) {
            spriteNumber = 2;

            int currentWorldX = (int) worldX;
            int currentWorldY = (int) worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
                default:
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 15) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void pickUpObject(int index) {
        if (index != 999) {
            String information;

            if(inventory.size() < inventoryMaxSize) {
                inventory.add(gamePanel.obj[index]);
                gamePanel.playSoundEfect(1);
                information = "Got a " + gamePanel.obj[index].name + ".";
            } else {
                information = "You cannot carry any more.";
            }
            gamePanel.ui.addMessage(information);
            gamePanel.obj[index] = null;
        }
    }

    private void interactNPC(int index) {
    if (gamePanel.keyHandler.enterPressed) {
            if (index != 999) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.DIALOGUE_STATE;
                gamePanel.npc[index].speak();
            }
        }
    }

    private void monsterContact(int index) {
        if (index != 999) {
            if (!invincible) {
                gamePanel.playSoundEfect(9);
                int damage = gamePanel.monster[index].attack - defense;
                if(damage < 0) {
                    damage = 0;
                }
                health -= damage;
                invincible = true;
            }
        }
    }

    private void damageMonster(int index) {
        if (index != 999) {
            if (!gamePanel.monster[index].invincible) {
                gamePanel.playSoundEfect(5);

                int damage = attack - gamePanel.monster[index].defense;
                if(damage < 0) {
                    damage = 0;
                }

                gamePanel.monster[index].health -= damage;
                gamePanel.monster[index].invincible = true;
                gamePanel.monster[index].demageReaction();

                if (gamePanel.monster[index].health <= 0) {
                    gamePanel.playSoundEfect(8);
                    gamePanel.monster[index].dying = true;
                    exp += gamePanel.monster[index].exp;
                    gamePanel.ui.addMessage("+ Exp: " + gamePanel.monster[index].exp);
                    checkLevelUp();
                }
            }
        }
    }

    private void checkLevelUp() {
        if(exp >= nextLevelExp) {
            exp = exp - nextLevelExp;
            level += 1;
            nextLevelExp = nextLevelExp*2;
            maxHealth += 2;
            strenght += 1;
            dexterity += 1;
            
            attack = getAttack();
            defense = getDefense();

            gamePanel.playSoundEfect(10);
            gamePanel.ui.addMessage("Level up!");
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = up1;
                    } else if (spriteNumber == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackUp1;
                    } else if (spriteNumber == 2) {
                        image = attackUp2;
                    }
                    tempScreenY -= gamePanel.tileSize;
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = down1;
                    } else if (spriteNumber == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackDown1;
                    } else if (spriteNumber == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = left1;
                    } else if (spriteNumber == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackLeft1;
                    } else if (spriteNumber == 2) {
                        image = attackLeft2;
                    }
                    tempScreenX -= gamePanel.tileSize;
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = right1;
                    } else if (spriteNumber == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackRight1;
                    } else if (spriteNumber == 2) {
                        image = attackRight2;
                    }
                }
                break;
            default:
                break;
        }

        int x = screenX;
        int y = screenY;

        if (screenX > worldX) {
            x = (int) worldX;
        }
        if (screenY > worldY) {
            y = (int) worldY;
        }
        int rightOffset = gamePanel.screenWidth - screenX;
        if (rightOffset > gamePanel.worldWidth - worldX) {
            x = (int) (gamePanel.screenWidth - (gamePanel.worldWidth - worldX));
        }
        int bottomOffset = gamePanel.screenHeight - screenY;
        if (bottomOffset > gamePanel.worldHeight - worldY) {
            y = (int) (gamePanel.screenHeight - (gamePanel.worldHeight - worldY));
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setColor(Color.green);
        g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void selectItem() {
        int itemIndex = gamePanel.ui.getCurrentItemInventoryIndex();

        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
    
            if(selectedItem.type == SWORD_TYPE || selectedItem.type == AXE_TYPE) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            else if(selectedItem.type == SHIELD_TYPE) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            else if(selectedItem.type == CONSUMABLE_TYPE) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
}
