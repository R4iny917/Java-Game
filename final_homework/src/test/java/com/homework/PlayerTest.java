package com.homework;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.homework.world.*;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import com.homework.world.Item;;
class PlayerTest {

    @Test
    void testAttacked() {
        World world = mock(World.class);
        Player player = new Player(1, 2, 3, 10, 5, Color.RED, 'A', world);
        player.attacked(5);
        assertEquals(5, player.get_health());
    }

    @Test
    void testGetDirection() {
        World world = mock(World.class);
        Player player = new Player(1, 2, 3, 4, 5, Color.RED, 'A', world);
        player.set_direction("right");
        assertEquals("right", player.get_direction());
        
        player.set_direction("left");
        assertEquals("left", player.get_direction());
        
    }

    @Test
    public void testAttachItems(){
        World world = new World(82,34);
        List<Item>items = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            Item item = new Item(10,10);
            items.add(item);
        }
        world.set_everything(null, null, null, items);
        Player player = new Player(10, 10, 0, 0, 0, null, (char)1, world);
        for(int i = 0; i < 50; ++i)
            player.attach_items();
    }
}
