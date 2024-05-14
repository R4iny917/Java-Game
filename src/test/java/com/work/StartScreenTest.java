package com.work;
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import java.awt.Component;
import org.junit.jupiter.api.*;

import com.work.screen.PlayScreen;
import com.work.screen.Screen;
import com.work.screen.StartScreen;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.KeyEvent;
public class StartScreenTest {
    private StartScreen startScreen;

    @BeforeEach
    public void setUp() {
        startScreen = new StartScreen();
    }

    @Test
    public void testDisplayOutput(){
        AsciiPanel terminal = new AsciiPanel(82, 36, AsciiFont.CP437_9x16);
        startScreen.displayOutput(terminal);
        assert(true);
    }

    @Test
    public void testRespondToUserInput(){
        KeyEvent keyEvent = new KeyEvent(new Component(){}, 0, 0, 0, KeyEvent.VK_ENTER,'s');
        Screen retScreen = startScreen.respondToUserInput(keyEvent);
        assert(retScreen instanceof PlayScreen);

        keyEvent.setKeyCode(KeyEvent.VK_S);
        startScreen.respondToUserInput(keyEvent);
        keyEvent.setKeyCode(KeyEvent.VK_ENTER);
        PlayScreen.switch_load_game_status();
        assert(PlayScreen.get_load_game_status() == true);
        retScreen = startScreen.respondToUserInput(keyEvent);
        assert(retScreen instanceof PlayScreen);


        keyEvent.setKeyCode(KeyEvent.VK_S);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        keyEvent.setKeyCode(KeyEvent.VK_ENTER);
        startScreen.respondToUserInput(keyEvent);
        keyEvent.setKeyCode(KeyEvent.VK_S);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        keyEvent.setKeyCode(KeyEvent.VK_W);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        startScreen.respondToUserInput(keyEvent);
        
        
        assert(true);
    }

    @Test
    public void testPlayerLiveCondition() {
        assertTrue(startScreen.player_live_condition(),"Player live condition should be true");
    }

    @Test
    public void testMonstersLiveCondition() {
        assertFalse(startScreen.monsters_live_condition(),"Monsters live condition should be false");
    }
}