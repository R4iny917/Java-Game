package com.homework.world;

import java.io.File;


import javax.swing.JFileChooser;

public class MapPath {
    private static String path = "src/main/java/com/homework/resources/standard_map.map";
    private static boolean path_is_chosen = false;
    public static String get_path(){
        return path;
    }

    public static boolean get_choice(){
        return path_is_chosen;
    }

    public static void choose_path(){
        path_is_chosen = true;
        JFileChooser fileChooser = new JFileChooser();
        File defaultDirectory = new File("src/main/java/com/homework/resources");
        fileChooser.setCurrentDirectory(defaultDirectory);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
            //System.out.println(path);
        }
    }
}
