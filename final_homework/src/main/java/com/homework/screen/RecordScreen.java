package com.homework.screen;

import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.homework.loadsave.PlayRecord;
import com.homework.world.World;

import asciiPanel.AsciiPanel;

public class RecordScreen implements Screen {

    private JFileChooser fileChooser;
    private File defaultDirectory;
    private File fileToOpen;
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private List<World>worlds;
    private int world_index;
    public RecordScreen() throws ClassNotFoundException, IOException{
        super();
        fileToOpen = new File("src/main/java/com/homework/resources/TestPlayRecord");
        if(PlayRecord.is_under_test == false){
            fileChooser = new JFileChooser();
            defaultDirectory = new File("src/main/java/com/homework/resources");
            fileChooser.setCurrentDirectory(defaultDirectory);
            // 显示打开文件对话框
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                fileToOpen = fileChooser.getSelectedFile();
            }
        }
        try {
            fileIn = new FileInputStream(fileToOpen);        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in = new ObjectInputStream(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        worlds = new ArrayList<>();
        while (true) {
            try {
                World world = (World) in.readObject();
                worlds.add(world);
            } catch (EOFException e) {
                break;  
            }
        }
        world_index = 0;
    }

    public void displayOutput(AsciiPanel terminal){
        PlayRecord.display_every_frame(terminal, worlds.get(world_index));
        world_index++;
        if(world_index == worlds.size())
            PlayRecord.switch_record_status();
    }

    public Screen respondToUserInput(KeyEvent key){
        return this;
    }

    @Override
    public boolean player_live_condition(){
        return false;
    }

    @Override
    public boolean monsters_live_condition(){
        return false;
    }
}
