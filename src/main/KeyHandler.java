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

        //PLAY STATE
        if(gamePanel.gameState == gamePanel.playState){

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
                gamePanel.gameState = gamePanel.pauseState;
            }

            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
    
            // DEBUG
            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
        }

        //PAUSE STATE
        else if(gamePanel.gameState == gamePanel.pauseState){
            if (code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

        //DIALOGUE STATE
        else if(gamePanel.gameState == gamePanel.dialogueState){
            if (code == KeyEvent.VK_ENTER) {
                gamePanel.gameState = gamePanel.playState;
            }
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
