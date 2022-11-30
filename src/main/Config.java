package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
    
    GamePanel gamePanel;

    public Config(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            if(gamePanel.fullScreenOn) {
                bw.write("On");
            } else {
                bw.write("Off"); 
            }
            bw.newLine();

            bw.write(String.valueOf(gamePanel.music.volumeScale));
            bw.newLine();

            bw.write(String.valueOf(gamePanel.soundEfect.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String line = br.readLine();

            if(line.equals("On")) {
                gamePanel.fullScreenOn = true;
            } else if(line.equals("Off")) {
                gamePanel.fullScreenOn = false;
            }

            line = br.readLine();
            gamePanel.music.volumeScale = Integer.parseInt(line);

            line = br.readLine();
            gamePanel.soundEfect.volumeScale = Integer.parseInt(line);

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
