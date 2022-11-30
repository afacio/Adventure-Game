package main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;
    String[] soundPath = new String[30];

    public Sound() {

        soundPath[0] = "src/res/sound/BlueBoyAdventure.wav";
        soundPath[1] = "src/res/sound/coin.wav";
        soundPath[2] = "src/res/sound/powerup.wav";
        soundPath[3] = "src/res/sound/unlock.wav";
        soundPath[4] = "src/res/sound/fanfare.wav";
        soundPath[5] = "src/res/sound/hitmonster.wav";
        soundPath[6] = "src/res/sound/receivedamage.wav";
        soundPath[7] = "src/res/sound/swingweapon.wav";
        soundPath[8] = "src/res/sound/blood-pop.wav";
        soundPath[9] = "src/res/sound/player-demage.wav";
        soundPath[10] = "src/res/sound/levelup.wav";
        soundPath[11] = "src/res/sound/cursor.wav";
        soundPath[12] = "src/res/sound/burning.wav";
        soundPath[13] = "src/res/sound/cuttree.wav";
    }

    public void setFile(int index) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath[index]));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
