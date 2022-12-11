package entity;

import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Entity {

    GamePanel gamePanel;

    public double worldX;
    public double worldY;
    public double speed;
    public double defaultSpeed;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int actionLockCounter = 0;
    public int shotAvelibleCounter = 0;
    public int invincibleCounter = 0;
    public int knockBackCounter = 0;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    public String dialogues[] = new String[20];
    public int dialoguesIntex = 0;

    public int maxHealth;
    public int health;
    public int maxMana;
    public int mana;

    public BufferedImage image1, image2, image3;
    public String name;
    public boolean collision = false;

    public boolean knockBack = false;
    public boolean invincible = false;
    public int invincibleTime = 30;

    public int type;
    public static final int PLAYER_TYPE = 0;
    public static final int NPC_TYPE = 1;
    public static final int MONSTER_TYPE = 2;
    public static final int MELEE_WEAPON_TYPE = 3;
    public static final int MAGIC_WEAPON_TYPE = 4;
    public static final int AXE_TYPE = 5;
    public static final int SHIELD_TYPE = 6;
    public static final int CONSUMABLE_TYPE = 7;
    public static final int PICKUP_ONLY_TYPE = 8;
    public static final int OBSTACLE_TYPE = 9;
    public static final int LIGHT_TYPE = 10;

    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2;
    public boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public boolean onPath = false;

    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter;

    public boolean hpBarOn = false;
    public int hpBarCounter;

    public int animationRefresh = 8;

    public int level;
    public int strenght;
    public int knowledge;
    public int currentAttackPower;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentMeleeWeapon;
    public Entity currentMagicWeapon;
    public Entity currentShield;
    public Entity currentLightSource;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useManaCost;
    public int itemPrice = 0;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public int inventoryMaxSize;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        inventoryMaxSize = gamePanel.ui.SLOT_MAX_COL * gamePanel.ui.SLOT_MAX_ROW;

        setDefaultSolidAreaValues();
    }

    private void setDefaultSolidAreaValues() {

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(new File("res" + imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
    }

    public void demageReaction() {
    }

    public void checkDrop() {
    }

    public boolean use(Entity entity) {
        return false;
    }

    public void interact() {
    }

    public void dropItem(Entity droppedItmen) {
        for (int i = 0; i < gamePanel.obj[1].length; i++) {
            if (gamePanel.obj[gamePanel.currentMap][i] == null) {
                gamePanel.obj[gamePanel.currentMap][i] = droppedItmen;
                gamePanel.obj[gamePanel.currentMap][i].worldX = worldX;
                gamePanel.obj[gamePanel.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public void speak() {
        if (dialogues[dialoguesIntex] == null) {
            dialoguesIntex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialoguesIntex];
        dialoguesIntex++;

        switch (gamePanel.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
            default:
                break;
        }
    }

    public void update() {
        if (!dying) {

            if (knockBack) {
                checkCollision();
                if (collisionOn) {
                    knockBack = false;
                    knockBackCounter = 0;
                    speed = defaultSpeed;
                } else if (!collisionOn) {
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

                    knockBackCounter++;
                    if (knockBackCounter == 4) {
                        knockBack = false;
                        knockBackCounter = 0;
                        speed = defaultSpeed;
                    }
                }

            } else {

                setAction();
                checkCollision();

                // IF COLLISION IS FALSE, ENTITY CAN MOVE
                if (!collisionOn) {

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

                spriteCounter++;
                updateSprites();
                if (invincible) {
                    invincibleCounter++;
                    if (invincibleCounter > invincibleTime) {
                        invincible = false;
                        invincibleCounter = 0;
                    }
                }
                if (shotAvelibleCounter < 30) {
                    shotAvelibleCounter++;
                }
            }
        }
    }

    public void updateSprites() {
        if (spriteCounter > animationRefresh) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void damagePlayer(int attackPower) {
        if (!gamePanel.player.invincible) {
            gamePanel.playSoundEffect(6);
            int damage = attackPower - gamePanel.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gamePanel.player.health -= damage;
            gamePanel.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        double screenX = (worldX - gamePanel.player.worldX + gamePanel.player.screenX);
        double screenY = (worldY - gamePanel.player.worldY + gamePanel.player.screenY);

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            image = drawSprites();

            // MONSTER HP BAR
            if (type == MONSTER_TYPE && hpBarOn) {

                double scale = (double) gamePanel.tileSize / maxHealth;
                double hpBarValue = scale * health;

                g2.setColor(Color.darkGray);
                g2.fillRect((int) screenX - 2, (int) screenY + gamePanel.tileSize + 3, gamePanel.tileSize + 4, 8);
                g2.setColor(Color.red);
                g2.fillRect((int) screenX, (int) screenY + gamePanel.tileSize + 5, (int) hpBarValue, 4);

                hpBarCounter++;
                if (hpBarCounter > 300) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, (int) screenX, (int) screenY, null);
            // g2.setColor(Color.red);
            // g2.drawRect((int) (screenX + solidArea.x), (int) (screenY + solidArea.y),
            // solidArea.width, solidArea.height);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    public BufferedImage drawSprites() {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                } else if (spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                } else if (spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                } else if (spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                } else if (spriteNumber == 2) {
                    image = right2;
                }
                break;
            default:
                break;
        }
        return image;
    }

    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        alive = false;

        if (dyingCounter <= i) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }
        if (dyingCounter > i && dyingCounter <= 2 * i) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if (dyingCounter > 2 * i && dyingCounter <= 3 * i) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }
        if (dyingCounter > 3 * i && dyingCounter <= 4 * i) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if (dyingCounter > 4 * i && dyingCounter <= 5 * i) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        }
        if (dyingCounter > 5 * i && dyingCounter <= 6 * i) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        if (dyingCounter > 8 * i) {
            dying = false;
        }
    }

    public Color getParticleColor() {
        return null;
    }

    public int getParticleSize() {
        return 0;
    }

    public int getParticleSpeed() {
        return 0;
    }

    public int getParticleMaxHealth() {
        return 0;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxHealth = generator.getParticleMaxHealth();

        Particle particle1 = new Particle(gamePanel, target, color, size, speed, maxHealth, -2, -1);
        gamePanel.particleList.add(particle1);
        Particle particle2 = new Particle(gamePanel, target, color, size, speed, maxHealth, 2, -1);
        gamePanel.particleList.add(particle2);
        Particle particle3 = new Particle(gamePanel, target, color, size, speed, maxHealth, -2, 1);
        gamePanel.particleList.add(particle3);
        Particle particle4 = new Particle(gamePanel, target, color, size, speed, maxHealth, 2, 1);
        gamePanel.particleList.add(particle4);
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (int) (getLeftX() / gamePanel.tileSize);
        int startRow = (int) (getTopY() / gamePanel.tileSize);

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gamePanel.pathFinder.search()) {
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;

            int entityLeftX = getLeftX();
            int entityRightX = getRightX();
            int entityTopY = getTopY();
            int entityBottomY = getBottomY();

            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize) {
                direction = "up";
            } else if (entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize) {
                direction = "down";
            } else if (entityTopY >= nextY && entityBottomY < nextY + gamePanel.tileSize) {
                if (entityLeftX > nextX) {
                    direction = "left";
                } else if (entityLeftX < nextX) {
                    direction = "right";
                }
            } else if (entityTopY > nextY && entityLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entityTopY > nextY && entityLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (entityTopY < nextY && entityLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entityTopY < nextY && entityLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }

            // int nextCol = gamePanel.pathFinder.pathList.get(0).col;
            // int nextRow = gamePanel.pathFinder.pathList.get(0).row;

            // if(nextCol == goalCol && nextRow == goalRow) {
            // onPath = false;
            // }
        }
    }

    private void checkCollision() {
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.monster);
        gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.interactiveTile);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayerCollision(this);

        if (this.type == MONSTER_TYPE && contactPlayer) {
            damagePlayer(attack);
        }
    }

    public int getDetected(Entity entity, Entity[][] target, String targetName) {
        int index = 999;

        int nextWorldX = entity.getLeftX();
        int nextWorldY = entity.getTopY();

        switch (entity.direction) {
            case "up":
                nextWorldY = entity.getTopY() - 1;
                break;
            case "down":
                nextWorldY = entity.getBottomY() + 1;
                break;
            case "left":
                nextWorldX = entity.getLeftX() - 1;
                break;
            case "right":
                nextWorldX = entity.getRightX() + 1;
                break;
            default:
                break;
        }

        int col = nextWorldX/gamePanel.tileSize;
        int row = nextWorldY/gamePanel.tileSize;

        for(int i = 0; i < target[1].length; i++) {
            if(target[gamePanel.currentMap][i] != null) {
                if(target[gamePanel.currentMap][i].getCol() == col && 
                target[gamePanel.currentMap][i].getRow() == row && 
                target[gamePanel.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    public int getLeftX() {
        return (int) worldX + solidArea.x;
    }

    public int getRightX() {
        return (int) worldX + solidArea.x + solidArea.width;
    }

    public int getTopY() {
        return (int) worldY + solidArea.y;
    }

    public int getBottomY() {
        return (int) worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {
        return getLeftX() / gamePanel.tileSize;
    }

    public int getRow() {
        return getTopY() / gamePanel.tileSize;
    }

    private int searchItemInInventory(String itemName) {
        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;

        if(item.stackable) {
            int index = searchItemInInventory(item.name);
            if(index != 999) {
                inventory.get(index).amount += item.amount;
                canObtain = true;
            } else {
                if(inventory.size() != inventoryMaxSize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        } else {
            if(inventory.size() != inventoryMaxSize) {
                inventory.add(item);
                canObtain = true;
            }
        } 
        return canObtain;       
    }
}
