package com.homework;

import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import com.homework.loadsave.PlayRecord;
import com.homework.screen.*;

public class MainApplication extends JFrame implements KeyListener
{   private AsciiPanel terminal;
    private Screen screen;

    public MainApplication(){
        super();
        terminal = new AsciiPanel(82, 36, AsciiFont.CP437_9x16);
        ImageIcon icon = new ImageIcon("D:/Advanced JAVA Programming/j05-R4iny917/final_homework/src/main/java/com/homework/resources/Icon.jpg");
        this.setIconImage(icon.getImage());
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();

    }

    public void repaint(){
        terminal.clear();
        if(screen instanceof RecordScreen && PlayRecord.get_record_status() == false)
            screen = new StartScreen();
        try {
            screen.displayOutput(terminal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        if(screen instanceof ClientScreen)return;
        if(screen instanceof ServerScreen)return;
        repaint();
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }
    
    public static void main( String[] args)
    {
        MainApplication app = new MainApplication();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        while(true){
            app.repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(50);        
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
