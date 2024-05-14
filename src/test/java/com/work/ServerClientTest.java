package com.work;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.work.multiplayer.Server;
import com.work.screen.ServerScreen;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class ServerClientTest {
    private ServerScreen serverScreen;
    private KeyEvent keyEvent;

    @BeforeEach
    public void setup(){
        serverScreen = new ServerScreen();
        keyEvent = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_A,'a');
    }

    @Test
    public void testRespondToUserInput() throws InterruptedException, IOException{
        
        TimeUnit.MILLISECONDS.sleep(50);
        
        AsciiPanel terminal = new AsciiPanel(82, 36, AsciiFont.CP437_9x16);
        serverScreen.displayOutput(terminal);
        terminal.clear();

    }

    @Test
    public void testMoveUp(){
        keyEvent.setKeyCode(KeyEvent.VK_W);
        serverScreen.respondToUserInput(keyEvent);
       
    }

    @Test
    public void testMoveDown(){
        keyEvent.setKeyCode(KeyEvent.VK_S);
        serverScreen.respondToUserInput(keyEvent);
       
    }

    @Test
    public void testMoveLeft(){
        keyEvent.setKeyCode(KeyEvent.VK_A);
        serverScreen.respondToUserInput(keyEvent);
       
    }

    @Test
    public void testMoveRight(){
        keyEvent.setKeyCode(KeyEvent.VK_D);
        serverScreen.respondToUserInput(keyEvent);
       
    }
    @Test
    public void testConstructKeyEvent() throws IOException{
        Server server = new Server("localhost");
        server.construct_KeyEvent(0,"w");
        server.construct_KeyEvent(0,"a");
        server.construct_KeyEvent(0,"s");
        server.construct_KeyEvent(0,"d");
    }
}
