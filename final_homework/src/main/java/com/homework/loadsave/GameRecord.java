package com.homework.loadsave;

import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import com.homework.world.World;

public class GameRecord {
    private static boolean is_recording_game = false;
    private static JFileChooser fileChooser = new JFileChooser();
    private static File defaultDirectory = new File("src/main/java/com/homework/resources");
    private static File fileToSave = fileChooser.getSelectedFile();
    private static FileOutputStream fileOut;
    private static ObjectOutputStream out;
    private static List<World> worlds = new ArrayList<>();  
    public static void start_recording(){
        fileChooser.setCurrentDirectory(defaultDirectory);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) 
            fileToSave = fileChooser.getSelectedFile();
        try {
            fileOut = new FileOutputStream(fileToSave);
            out = new ObjectOutputStream(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void record_game(World world) throws IOException, ClassNotFoundException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(world);
        oos.flush();
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        World new_world = (World) ois.readObject();
        worlds.add(new_world);
    }

    public static void end_recording(){
        try {
            for(World world:worlds)
                out.writeObject(world);
            out.close();
            fileOut.close();
            worlds.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switch_recording_status(){
        if(is_recording_game == false)is_recording_game = true;
        else is_recording_game = false;
    }

    public static boolean get_record_status(){
        return is_recording_game;
    }
}
