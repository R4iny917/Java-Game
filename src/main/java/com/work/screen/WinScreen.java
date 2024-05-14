package com.work.screen;
import asciiPanel.AsciiPanel;
import java.awt.Color;
public class WinScreen   {

    public static void displayOutput(AsciiPanel terminal){
        Color color1 = new Color(255,215,0);
        Color color2 = AsciiPanel.brightWhite;
        String title1 = " __     __  ___    ____   _____    ___    ____   __   __";
        String start_word ="Congratulation!You have passed the game!";
        int start_length =  (terminal.getWidthInCharacters() - start_word.length())/2;
        int x_1 = (terminal.getWidthInCharacters() - title1.length())/2;
        int y_1 = terminal.getHeightInCharacters()/4 + 2;
        terminal.write(" __     __  ___    ____   _____    ___    ____   __   __",x_1, y_1+0,color1);
        terminal.write(" \\ \\   / / |_ _|  / ___| |_   _|  / _ \\  |  _ \\  \\ \\ / /",x_1, y_1+1,color1);
        terminal.write("  \\ \\ / /   | |  | |       | |   | | | | | |_) |  \\ V / ",x_1,y_1+2,color1);
        terminal.write("   \\ V /    | |  | |___    | |   | |_| | |  _ <    | |  ", x_1, y_1+3,color1);
        terminal.write("    \\_/    |___|  \\____|   |_|    \\___/  |_| \\_\\   |_|  ", x_1, y_1+4,color1);
        terminal.write(start_word, start_length, y_1+6,color2);
    }

}
