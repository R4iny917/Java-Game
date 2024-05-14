package com.work;
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import org.junit.jupiter.api.*;

import com.work.screen.WinScreen;

public class WinSreenTest {

    @Test
    public void testDisplayOutput(){
        AsciiPanel terminal = new AsciiPanel(82, 36, AsciiFont.CP437_9x16);
        WinScreen.displayOutput(terminal);
        assert(true);
    }


}
