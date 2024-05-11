package com.homework;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
import com.homework.world.Creature;
import com.homework.world.World;
public class CreatureTest {

    @Test
    void testCreature() {

        World mockWorld = mock(World.class);
        Creature creature = new Creature(1, 1, 1, 1, 1, Color.RED, 'A', mockWorld);
        assertEquals(1, creature.get_x());
        assertEquals(1, creature.get_y());
        assertEquals(1, creature.get_attack());
        assertEquals(1, creature.get_health());
        assertEquals(1, creature.get_speed());
        assertEquals(Color.RED, creature.get_color());
        assertEquals('A', creature.get_glyph());
    }

     @Test
    void testMoveBy() {
        World world = mock(World.class);
        when(world.get_width()).thenReturn(82);
        when(world.get_height()).thenReturn(34);

        Creature creature = new Creature(1, 1, 1, 1, 1, Color.RED, 'A', world);
        creature.move_by(1, 1);

        assertEquals(2, creature.get_x());
        assertEquals(2, creature.get_y());

        creature.move_by(-5,-5);
        assertEquals(1, creature.get_x());
        assertEquals(1, creature.get_y());
    }


    @Test
    void testAttacked() {
        World world = mock(World.class);
        Creature creature = new Creature(1, 1, 1, 10, 1, Color.RED, 'A', world);
        creature.attacked(5);

        assertEquals(5, creature.get_health());
    }

    @Test
    void testIsAlive() {
        World world =  mock(World.class);
        Creature creature = new Creature(1, 1, 1, 10, 1, Color.RED, 'A', world);

        assertTrue(creature.is_alive());

        creature.attacked(10);

        assertFalse(creature.is_alive());
    }

    @Test
    void testGetters() {
        Creature creature = new Creature(1, 2, 3, 4, 5, Color.RED, 'A', null);

        assertEquals(4, creature.get_health());
        assertEquals(3, creature.get_attack());
        assertEquals(5, creature.get_speed());
        assertEquals(1, creature.get_x());
        assertEquals(2, creature.get_y());
        assertEquals('A', creature.get_glyph());
        assertEquals(Color.RED, creature.get_color());
    }
}
