package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    // DEBUG
    boolean checkDrawTime;

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

        if(gamePanel.gameState == gamePanel.TITLE_STATE){
            titleState(code);
        }

        else if(gamePanel.gameState == gamePanel.PLAY_STATE){
            playState(code);
        }

        else if(gamePanel.gameState == gamePanel.PAUSE_STATE){
            pauseState(code);
        }

        else if(gamePanel.gameState == gamePanel.DIALOGUE_STATE){
           dialogueState(code);
        }

        else if(gamePanel.gameState == gamePanel.CHARACTER_STATE){
            characterState(code);
        }
    }

    private void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            if(gamePanel.ui.commandNum != 0){
                gamePanel.ui.commandNum--;
            } else {
                gamePanel.ui.commandNum = 3;
            }
        }
        if (code == KeyEvent.VK_S) {
            if(gamePanel.ui.commandNum != 3){
                gamePanel.ui.commandNum++;
            } else {
                gamePanel.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if(gamePanel.ui.commandNum == 0){
                gamePanel.setupGame();
            }
            if(gamePanel.ui.commandNum == 1){
                //todo
            }
            if(gamePanel.ui.commandNum == 2){
                gamePanel.gameState = gamePanel.creatingState;
            }
            if(gamePanel.ui.commandNum == 3){
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
        if (code == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.PAUSE_STATE;
        }

        if (code == KeyEvent.VK_C) {
            gamePanel.gameState = gamePanel.CHARACTER_STATE;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    private void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
        }
    }

    private void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
        }
    }

    private void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
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

    }

}
