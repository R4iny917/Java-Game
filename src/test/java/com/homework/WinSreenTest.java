package com.homework;
import com.homework.screen.WinScreen;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import org.junit.jupiter.api.*;

public class WinSreenTest {

    @Test
    public void testDisplayOutput(){
        AsciiPanel terminal = new AsciiPanel(82, 36, AsciiFont.CP437_9x16);
        WinScreen.displayOutput(terminal);
        assert(true);
    }


}
