package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {


    public static void saveProgram(GamePanel gamePanel) throws IOException{
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("saves/data-serialization.bin"))) {
            outputStream.writeObject(gamePanel);
        }  
    }

    public static GamePanel readProgram() throws ClassNotFoundException, IOException{
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("saves/data-serialization.bin"))) {
            return (GamePanel) inputStream.readObject();
        }
    }
    
}
