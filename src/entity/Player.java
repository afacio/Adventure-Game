package entity;

import main.KeyHandler;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import projectile.Spell_Fireball;
import main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

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
        // worldX = (double) gamePanel.tileSize * 12;
        // worldY = (double) gamePanel.tileSize * 12;
        speed = 4;
        defaultSpeed = speed;
        direction = "down";

        solidArea = new Rectangle(14, 12, 22, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        maxHealth = 6;
        health = maxHealth;
        maxMana = 6;
        mana = maxMana;

        level = 1;
        strenght = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 200;

        currentMeleeWeapon = new OBJ_Sword_Normal(gamePanel);
        currentShield = new OBJ_Shield_Wood(gamePanel);
        projectile = new Spell_Fireball(gamePanel);
        attack = getMeleAttackPower();
        defense = getDefense();

        setInventoryItems();
    }

    public void setInventoryItems() {
        inventory.clear();
        inventory.add(currentMeleeWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gamePanel));
    }

    private int getMeleAttackPower() {
        attackArea = currentMeleeWeapon.attackArea;
        attack = strenght * currentMeleeWeapon.attackValue;
        return attack;
    }

    private int getMagicAttackPower() {
        attackArea = currentMagicWeapon.attackArea;
        attack = knowledge * currentMagicWeapon.attackValue;
        return attack;
    }

    private int getDefense() {
        defense = dexterity * currentShield.defenseValue;
        return defense;
    }

    public void getPlayerImage() {
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
        if (currentMeleeWeapon.type == MELEE_WEAPON_TYPE) {
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
        if (currentMeleeWeapon.type == AXE_TYPE) {
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

            // // CHECK INTERACTIVE TAIL COLLISION
            int interactiveTileIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.interactiveTile);


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

        if (gamePanel.keyHandler.shotKeyPressed && !projectile.alive && shotAvelibleCounter == 30
                && projectile.haveResource(this)) {
            projectile.set(worldX, worldY, direction, true, this);

            for(int i = 0; i < gamePanel.projectileList[1].length; i++) {
                if(gamePanel.projectileList[gamePanel.currentMap][i] == null) {
                    gamePanel.projectileList[gamePanel.currentMap][i] = projectile;
                    break;
                }
            }

            projectile.playSoundEfect();
            shotAvelibleCounter = 0;
            projectile.subtractResource(this);
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvelibleCounter < 30) {
            shotAvelibleCounter++;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        if (health > maxHealth) {
            health = maxHealth;
        }
        if(health <= 0) {
            gamePanel.gameState = gamePanel.GAME_OVER_STATE;
            gamePanel.ui.commandNum = -1;
            gamePanel.keyHandler.enterPressed = false;
            gamePanel.stopMusic();
            gamePanel.playSoundEfect(14);
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
            damageMonster(monsterIndex, attack, currentMeleeWeapon.knockBackPower);

            int interactiveTileIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.interactiveTile);
            damageInteractiveTile(interactiveTileIndex);

            int projectileIdnex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.projectileList);
            damageProjectile(projectileIdnex);

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

            if (gamePanel.obj[gamePanel.currentMap][index].type == PICKUP_ONLY_TYPE) {

                gamePanel.obj[gamePanel.currentMap][index].use(this);
                gamePanel.obj[gamePanel.currentMap][index] = null;

            } else if(gamePanel.obj[gamePanel.currentMap][index].type == OBSTACLE_TYPE) {
                if(keyHandler.enterPressed) {
                    attackCanceled = true;
                    gamePanel.obj[gamePanel.currentMap][index].interact();
                }
            } else {
                String information;

                if (canObtainItem(gamePanel.obj[gamePanel.currentMap][index])) {
                    gamePanel.playSoundEfect(1);
                    information = "Got a " + gamePanel.obj[gamePanel.currentMap][index].name + ".";
                } else {
                    information = "You cannot carry any more.";
                }
                gamePanel.ui.addMessage(information);
                gamePanel.obj[gamePanel.currentMap][index] = null;
            }
        }
    }

    private void interactNPC(int index) {
        if (gamePanel.keyHandler.enterPressed) {
            if (index != 999) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.DIALOGUE_STATE;
                gamePanel.npc[gamePanel.currentMap][index].speak();
            }
        }
    }

    public void monsterContact(int index) {
        if (index != 999) {
            if (!invincible && !gamePanel.monster[gamePanel.currentMap][index].dying) {
                gamePanel.playSoundEfect(9);
                int damage = gamePanel.monster[gamePanel.currentMap][index].attack - defense;
                if (damage <= 0) {
                    damage = 1;
                }
                health -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int index, int attackPower, int knockBackPower) {
        if (index != 999) {
            if (!gamePanel.monster[gamePanel.currentMap][index].invincible) {
                gamePanel.playSoundEfect(5);

                if(knockBackPower > 0) {
                    knockBack(gamePanel.monster[gamePanel.currentMap][index], knockBackPower);
                }

                int damage = attackPower - gamePanel.monster[gamePanel.currentMap][index].defense;
                if (damage <= 0) {
                    damage = 1;
                }

                gamePanel.monster[gamePanel.currentMap][index].health -= damage;
                gamePanel.monster[gamePanel.currentMap][index].invincible = true;
                gamePanel.monster[gamePanel.currentMap][index].demageReaction();

                if (gamePanel.monster[gamePanel.currentMap][index].health <= 0) {
                    gamePanel.playSoundEfect(8);
                    gamePanel.monster[gamePanel.currentMap][index].dying = true;
                    exp += gamePanel.monster[gamePanel.currentMap][index].exp;
                    gamePanel.ui.addMessage("+ Exp: " + gamePanel.monster[gamePanel.currentMap][index].exp);
                    checkLevelUp();
                }
            }
        }
    }

    private void damageInteractiveTile(int interactiveTileIndex) {
        if(interactiveTileIndex != 999 && 
        gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].destrucible && 
        gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].isCorrectItem(this) && 
        !gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].invincible) {

            gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].health--;
            gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].invincible = true;
            gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].playSoundEfect();
            gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].generateParticle(gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex], gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex]);
            if(gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].health <= 0) {
                gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex] = gamePanel.interactiveTile[gamePanel.currentMap][interactiveTileIndex].getDestroyedForm();
            }
        }
    } 

    private void damageProjectile(int i) {
        if(i != 999) {
            generateParticle(gamePanel.projectileList[gamePanel.currentMap][i], gamePanel.projectileList[gamePanel.currentMap][i]);
            gamePanel.projectileList[gamePanel.currentMap][i].alive = false;
        }
    }

    private void checkLevelUp() {
        if (exp >= nextLevelExp) {
            exp = exp - nextLevelExp;
            level += 1;
            nextLevelExp = nextLevelExp * 2;
            maxHealth += 2;
            health = maxHealth;
            if (level % 2 == 0) {
                maxMana += 1;
            }
            mana += 2;
            strenght += 1;
            dexterity += 1;

            attack = getMeleAttackPower();
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
        // g2.setColor(Color.green);
        // g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void selectItem() {
        int itemIndex = gamePanel.ui.getCurrentItemInventoryIndex(gamePanel.ui.playerCurrentSlotCol, gamePanel.ui.playerCurrentSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == MELEE_WEAPON_TYPE || selectedItem.type == AXE_TYPE) {
                currentMeleeWeapon = selectedItem;
                attack = getMeleAttackPower();
                getPlayerAttackImage();
            } else if (selectedItem.type == SHIELD_TYPE) {
                currentShield = selectedItem;
                defense = getDefense();
            } else if (selectedItem.type == CONSUMABLE_TYPE) {
                if(selectedItem.use(this)) {
                    if(inventory.get(itemIndex).amount > 1) {
                        inventory.get(itemIndex).amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            } else if (selectedItem.type == LIGHT_TYPE) {
                if(currentLightSource == selectedItem) {
                   currentLightSource = null; 
                } else {
                    currentLightSource = selectedItem;
                }
                lightUpdated = true;
            }
        }
    }
    private void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed = knockBackPower;
        entity.knockBack = true;
    }

    public void getSleepingImage(BufferedImage image) {
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image; 
    }
}
