package com.work;

import asciiPanel.AsciiPanel;
import org.junit.jupiter.api.Test;

import com.work.screen.*;


public class LoseScreenTest {
    @Test
    public void testDisplayOutput() {
        AsciiPanel terminal = new AsciiPanel();
        LoseScreen.displayOutput(terminal);
        assert(true);
    }

}