package com.work.screen;

import asciiPanel.AsciiPanel;

import java.awt.Color;

public class LoseScreen{

    public static boolean server_is_down = false;

    public static void displayOutput(AsciiPanel terminal){
        Color color1 = Color.LIGHT_GRAY;
        Color color2 = Color.WHITE;
        String title1 = " | |  _  / _` || '_ ` _ \\  / _ \\ | | | |\\ \\ / // _ \\| '__|";
        String start_word ="Oops!Please try again.";
        int start_length =  (terminal.getWidthInCharacters() - start_word.length())/2;
        int x_1 = (terminal.getWidthInCharacters() - title1.length())/2;
        int y_1 = terminal.getHeightInCharacters()/4 + 2;
        terminal.write("   ____                            ___                    ",x_1, y_1+0,color1);
        terminal.write("  / ___|  __ _  _ __ ___    ___   / _ \\ __   __ ___  _ __ ",x_1, y_1+1,color1);
        terminal.write(" | |  _  / _` || '_ ` _ \\  / _ \\ | | | |\\ \\ / // _ \\| '__|",x_1,y_1+2,color1);
        terminal.write(" | |_| || (_| || | | | | ||  __/ | |_| | \\ V /|  __/| |   ", x_1, y_1+3,color1);
        terminal.write("  \\____| \\__,_||_| |_| |_| \\___|  \\___/   \\_/  \\___||_|   ", x_1, y_1+4,color1);
        terminal.write(start_word, start_length, y_1+6,color2);
        if(server_is_down == true){
            String waring = "Warning!Server stops working.";
            terminal.write(waring, (terminal.getWidthInCharacters() - waring.length())/2, y_1+7,color2);
        }
    }
}
