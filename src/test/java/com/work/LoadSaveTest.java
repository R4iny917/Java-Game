package com.work;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.work.loadsave.GameLoad;
import com.work.loadsave.GameSave;
import com.work.world.World;

public class LoadSaveTest {
    @Test
    public void testLoadGame() {
        GameLoad.is_under_test = true;
        //World world = GameLoad.load_game();
        //assertNotNull(world, "Game loading failed.");
        GameLoad.is_under_test = false;
    }

    @Test
    public void testSaveGame() {
        GameSave.is_under_test = true;
        World world = new World(0, 0);
        GameSave.save_game(world);
        File file = new File("src/main/java/com/homework/resources/TestGameSave");
        assertTrue(file.exists());
        assertNotNull(world, "Game loading failed.");
        GameSave.is_under_test = false;
    }
}
