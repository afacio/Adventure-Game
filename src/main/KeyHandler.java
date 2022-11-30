package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    // DEBUG
    public boolean checkDrawTime;

    GamePanel gamePanel;

    public KeyHandler(GamePanel gamepanel) {
        this.gamePanel = gamepanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.TITLE_STATE) {
            titleState(code);
        }

        else if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            playState(code);
        }

        else if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            pauseState(code);
        }

        else if (gamePanel.gameState == gamePanel.DIALOGUE_STATE) {
            dialogueState(code);
        }

        else if (gamePanel.gameState == gamePanel.CHARACTER_STATE) {
            characterState(code);
        }

        else if (gamePanel.gameState == gamePanel.OPTIONS_STATE) {
            optionsState(code);
        }

        else if (gamePanel.gameState == gamePanel.GAME_OVER_STATE) {
            gameOverState(code);
        }
    }

    private void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            if (gamePanel.ui.commandNum != 0) {
                gamePanel.ui.commandNum--;
            } else {
                gamePanel.ui.commandNum = 3;
            }
            gamePanel.playSoundEfect(11);
        }
        if (code == KeyEvent.VK_S) {
            if (gamePanel.ui.commandNum != 3) {
                gamePanel.ui.commandNum++;
            } else {
                gamePanel.ui.commandNum = 0;
            }
            gamePanel.playSoundEfect(11);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.ui.commandNum == 0) {
                gamePanel.setupGame();
                gamePanel.playMusic(0);
            }
            if (gamePanel.ui.commandNum == 1) {
                // todo
            }
            if (gamePanel.ui.commandNum == 2) {
                gamePanel.gameState = gamePanel.CREATING_STATE;
            }
            if (gamePanel.ui.commandNum == 3) {
                gamePanel.exitGame();
            }
        }
    }

    private void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.PAUSE_STATE;
        }

        if (code == KeyEvent.VK_I) {
            gamePanel.gameState = gamePanel.CHARACTER_STATE;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.OPTIONS_STATE;
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    private void pauseState(int code) {
        if (code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
        }
    }

    private void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
        }
    }

    private void characterState(int code) {
        if (code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
        } else if (code == KeyEvent.VK_ENTER) {
            gamePanel.player.selectItem();
        } else if (code == KeyEvent.VK_W) {
            if (gamePanel.ui.currentSlotRow > 0) {
                gamePanel.ui.currentSlotRow--;
                gamePanel.playSoundEfect(11);
            } else {
                if (gamePanel.ui.currentSlotCol > 0) {
                    gamePanel.ui.currentSlotCol--;
                    gamePanel.ui.currentSlotRow = gamePanel.ui.SLOT_MAX_ROW - 1;
                    gamePanel.playSoundEfect(11);
                }
            }
        } else if (code == KeyEvent.VK_S) {
            if (gamePanel.ui.currentSlotRow < gamePanel.ui.SLOT_MAX_ROW - 1) {
                gamePanel.ui.currentSlotRow++;
                gamePanel.playSoundEfect(11);
            } else {
                if (gamePanel.ui.currentSlotCol < gamePanel.ui.SLOT_MAX_COL - 1) {
                    gamePanel.ui.currentSlotCol++;
                    gamePanel.ui.currentSlotRow = 0;
                    gamePanel.playSoundEfect(11);
                }
            }
        } else if (code == KeyEvent.VK_A) {
            if (gamePanel.ui.currentSlotCol > 0) {
                gamePanel.ui.currentSlotCol--;
                gamePanel.playSoundEfect(11);
            } else {
                if (gamePanel.ui.currentSlotRow > 0) {
                    gamePanel.ui.currentSlotRow--;
                    gamePanel.ui.currentSlotCol = gamePanel.ui.SLOT_MAX_COL - 1;
                    gamePanel.playSoundEfect(11);
                }
            }
        } else if (code == KeyEvent.VK_D) {
            if (gamePanel.ui.currentSlotCol < gamePanel.ui.SLOT_MAX_COL - 1) {
                gamePanel.ui.currentSlotCol++;
                gamePanel.playSoundEfect(11);
            } else {
                if (gamePanel.ui.currentSlotRow < gamePanel.ui.SLOT_MAX_ROW - 1) {
                    gamePanel.ui.currentSlotRow++;
                    gamePanel.ui.currentSlotCol = 0;
                    gamePanel.playSoundEfect(11);
                }
            }
        }
    }

    private void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
            gamePanel.ui.subState = 0;
            gamePanel.ui.commandNum = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            gamePanel.playSoundEfect(13);
        }

        int maxCommandNumber = 0;
        switch (gamePanel.ui.subState) {
            case 0:
                maxCommandNumber = 5;
                break;
            case 3:
                maxCommandNumber = 1;
                break;
        }

        if (code == KeyEvent.VK_W) {
            if (gamePanel.ui.commandNum != 0) {
                gamePanel.ui.commandNum--;
            } else {
                gamePanel.ui.commandNum = maxCommandNumber;
            }
            gamePanel.playSoundEfect(11);
        }
        if (code == KeyEvent.VK_S) {
            if (gamePanel.ui.commandNum != maxCommandNumber) {
                gamePanel.ui.commandNum++;
            } else {
                gamePanel.ui.commandNum = 0;
            }
            gamePanel.playSoundEfect(11);
        }
        if (code == KeyEvent.VK_A) {
            if (gamePanel.ui.subState == 0) {
                if (gamePanel.music.volumeScale > 0 && gamePanel.ui.commandNum == 1) {
                    gamePanel.music.volumeScale--;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEfect(11);
                }
                if (gamePanel.soundEfect.volumeScale > 0 && gamePanel.ui.commandNum == 2) {
                    gamePanel.soundEfect.volumeScale--;
                    gamePanel.playSoundEfect(11);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gamePanel.ui.subState == 0) {
                if (gamePanel.music.volumeScale < 5 && gamePanel.ui.commandNum == 1) {
                    gamePanel.music.volumeScale++;
                    gamePanel.music.checkVolume();
                    gamePanel.playSoundEfect(11);
                }
                if (gamePanel.soundEfect.volumeScale < 5 && gamePanel.ui.commandNum == 2) {
                    gamePanel.soundEfect.volumeScale++;
                    gamePanel.playSoundEfect(11);
                }
            }
        }

    }

    private void gameOverState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            gamePanel.playSoundEfect(13);
        }

        int maxCommandNumber = 1;
        if (code == KeyEvent.VK_W) {
            if (gamePanel.ui.commandNum != 0) {
                gamePanel.ui.commandNum--;
            } else {
                gamePanel.ui.commandNum = maxCommandNumber;
            }
            gamePanel.playSoundEfect(11);
        }
        if (code == KeyEvent.VK_S) {
            if (gamePanel.ui.commandNum != maxCommandNumber) {
                gamePanel.ui.commandNum++;
            } else {
                gamePanel.ui.commandNum = 0;
            }
            gamePanel.playSoundEfect(11);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }

    }

}
