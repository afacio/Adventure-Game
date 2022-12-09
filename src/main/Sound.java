package main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;
    String[] soundPath = new String[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {

        soundPath[0] = "res/sound/BlueBoyAdventure.wav";
        soundPath[1] = "res/sound/coin.wav";
        soundPath[2] = "res/sound/powerup.wav";
        soundPath[3] = "res/sound/unlock.wav";
        soundPath[4] = "res/sound/fanfare.wav";
        soundPath[5] = "res/sound/hitmonster.wav";
        soundPath[6] = "res/sound/receivedamage.wav";
        soundPath[7] = "res/sound/swingweapon.wav";
        soundPath[8] = "res/sound/blood-pop.wav";
        soundPath[9] = "res/sound/player-demage.wav";
        soundPath[10] = "res/sound/levelup.wav";
        soundPath[11] = "res/sound/cursor.wav";
        soundPath[12] = "res/sound/burning.wav";
        soundPath[13] = "res/sound/cuttree.wav";
        soundPath[14] = "res/sound/gameover.wav";
        soundPath[15] = "res/sound/stairs.wav";
        soundPath[16] = "res/sound/trade.wav";
        soundPath[17] = "res/sound/noMany.wav";
        soundPath[18] = "res/sound/sleep.wav";
    }

    public void setFile(int index) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath[index]));
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
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

    public void checkVolume() {
        switch(volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
            default: break;
        }
        fc.setValue(volume);
    }

}
