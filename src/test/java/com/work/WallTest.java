package com.homework;

import org.junit.jupiter.api.Test;

import com.homework.world.Wall;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

public class WallTest {
    @Test
    public void testWall() {
        int x = 5;
        int y = 5;
        int width = 10;
        int height = 10;
        Wall wall = new Wall(x, y, width, height);

        assertEquals(x, wall.get_x());
        assertEquals(y, wall.get_y());
        assertEquals((char)7, wall.get_glyph());
        assertEquals(Color.darkGray, wall.get_color());
        wall = new Wall(0,0,width,height);
    }
}
