package com.work.screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;

public interface Screen {
  
    public void displayOutput(AsciiPanel terminal) throws IOException;

    public Screen respondToUserInput(KeyEvent key);

    public boolean player_live_condition();

    public boolean monsters_live_condition();
}
