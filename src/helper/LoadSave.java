package helper;

import java.io.InputStream;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class LoadSave {

    public static BufferedImage getAtlas(){

        BufferedImage atlas = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("/res/tiles/atlas.png");
        try {
            atlas = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atlas;
    }
    
}
