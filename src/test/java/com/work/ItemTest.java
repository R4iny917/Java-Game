package com.work;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import com.work.world.Item;

public class ItemTest {
    
    @Test
    public void testItem() {
        for(int i = 0; i < 10; ++i){
            Item item = new Item(10, 20);
            assertEquals(10, item.get_x());
            assertEquals(20, item.get_y());
            char glyph = item.get_glyph();
            Color color = item.get_color();
            assertTrue(glyph == (char)5 && color == Color.RED ||
                    glyph == (char)3 && color == Color.GREEN ||
                    glyph == (char)4 && color == Color.LIGHT_GRAY);
        }
    }
}
