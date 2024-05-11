package com.homework;

import com.homework.screen.*;

import asciiPanel.AsciiPanel;
import org.junit.jupiter.api.Test;


public class LoseScreenTest {
    @Test
    public void testDisplayOutput() {
        AsciiPanel terminal = new AsciiPanel();
        LoseScreen.displayOutput(terminal);
        assert(true);
    }

}