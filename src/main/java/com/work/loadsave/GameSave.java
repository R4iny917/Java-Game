package com.homework.loadsave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import com.homework.world.World;

public class GameSave {
    public static boolean is_under_test = false;
    public static void save_game(World world){
        File fileToSave = new File("src/main/java/com/homework/resources/TestSaveGame");
        if(is_under_test == false){
            JFileChooser fileChooser = new JFileChooser();
            File defaultDirectory = new File("src/main/java/com/homework/resources");
            fileChooser.setCurrentDirectory(defaultDirectory);
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                fileToSave = fileChooser.getSelectedFile();
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(fileToSave);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(world);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
