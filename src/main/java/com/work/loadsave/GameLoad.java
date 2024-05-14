package com.work.loadsave;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;

import com.work.world.World;

public class GameLoad {
    public static boolean is_under_test = false;
    public static World load_game(){
        File fileToOpen = new File("src/main/java/com/homework/resources/TestGameLoad");
        if(is_under_test == false){
            JFileChooser fileChooser = new JFileChooser();
            File defaultDirectory = new File("src/main/java/com/homework/resources");
            fileChooser.setCurrentDirectory(defaultDirectory);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                fileToOpen = fileChooser.getSelectedFile();
            }
        }
        try {
            FileInputStream fileIn = new FileInputStream(fileToOpen);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            World world = (World) in.readObject();
            in.close();
            fileIn.close();
            //System.out.println("Deserialized data is read from " + fileToOpen.getAbsolutePath());
            return world;
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("MyObject class not found");
            c.printStackTrace();
        }
        return null;
    }
}
