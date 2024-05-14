package com.work;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.work.loadsave.GameRecord;
import com.work.screen.PlayScreen;
import com.work.world.*;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class PlayScreenTest {

    private PlayScreen playScreen;
    @BeforeEach
    public void BeforeEach(){
        playScreen = new PlayScreen();
    }

    @Test
public void testPlayScreen() {
    PlayScreen playScreen2 = new PlayScreen();
    assertNotNull(playScreen2);

    PlayScreen.switch_load_game_status();
    PlayScreen.switch_load_game_status();
}

 
    @Test
    public void testDisplayOutput() throws InterruptedException, IOException { 
       // PlayScreen playScreen = new PlayScreen();
        AsciiPanel Terminal = new AsciiPanel(82, 36, AsciiFont.CP437_9x16);
        TimeUnit.MILLISECONDS.sleep(6000);
        playScreen.displayOutput(Terminal);
        assert(true);
}

    @Test
    public void testDisplayEscWindow() throws IOException{
        //playScreen = new PlayScreen();
        AsciiPanel terminal = new AsciiPanel(82, 36, AsciiFont.CP437_9x16);
        Esc.switch_world_status();
        playScreen.displayOutput(terminal);
        terminal.clear();
        GameRecord.switch_recording_status();
        playScreen.displayOutput(terminal);
        GameRecord.switch_recording_status();
        Esc.switch_world_status();

        KeyEvent keyEvent = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_A,'a');
        keyEvent.setKeyCode(KeyEvent.VK_ESCAPE);
        playScreen.respondToUserInput(keyEvent);

        keyEvent.setKeyCode(KeyEvent.VK_DOWN);
        playScreen.respondToUserInput(keyEvent);
        playScreen.display_esc_window(terminal);

        keyEvent.setKeyCode(KeyEvent.VK_DOWN);
        playScreen.respondToUserInput(keyEvent);
        playScreen.display_esc_window(terminal);
    }
    
    @Test
    public void testRespondToUserInput() throws InterruptedException {
        //PlayScreen playScreen = new PlayScreen();
        KeyEvent keyEvent = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_A,'a');
        playScreen.respondToUserInput(keyEvent);
        assertEquals("left", playScreen.getPlayer().get_direction());
        
        TimeUnit.MILLISECONDS.sleep(500);
        keyEvent.setKeyCode(KeyEvent.VK_D);
        playScreen.respondToUserInput(keyEvent);
        assertEquals("right", playScreen.getPlayer().get_direction());

        TimeUnit.MILLISECONDS.sleep(500);
        keyEvent.setKeyCode(KeyEvent.VK_W);
        playScreen.respondToUserInput(keyEvent);
        assertEquals("up", playScreen.getPlayer().get_direction());

        TimeUnit.MILLISECONDS.sleep(500);
        keyEvent.setKeyCode(KeyEvent.VK_S);
        playScreen.respondToUserInput(keyEvent);
        assertEquals("down", playScreen.getPlayer().get_direction());

        TimeUnit.MILLISECONDS.sleep(500);
        keyEvent.setKeyCode(KeyEvent.VK_SPACE);
        playScreen.respondToUserInput(keyEvent);
        assertEquals("down", playScreen.getPlayer().get_direction());

        keyEvent.setKeyCode(KeyEvent.VK_ESCAPE);
        playScreen.respondToUserInput(keyEvent);

        keyEvent.setKeyCode(KeyEvent.VK_S);
        playScreen.respondToUserInput(keyEvent);
        playScreen.respondToUserInput(keyEvent);

        keyEvent.setKeyCode(KeyEvent.VK_W);
        playScreen.respondToUserInput(keyEvent);
        playScreen.respondToUserInput(keyEvent);

        keyEvent.setKeyCode(KeyEvent.VK_ESCAPE);
        playScreen.respondToUserInput(keyEvent);
        
    }

    @Test
    public void testPlayerLiveCondition() {

        assertTrue(playScreen.player_live_condition());
        playScreen.getPlayer().attacked(100);
        assertFalse(playScreen.player_live_condition());
    }

    @Test
    public void testMonstersLiveCondition() {

        assertTrue(playScreen.monsters_live_condition());
    }

@Test
    public void testGetters() {
   
        Player player = playScreen.getPlayer();
        assertEquals(player, playScreen.getPlayer());
        World world = playScreen.getWorld();
        assertEquals(world, playScreen.getWorld());
    }

}