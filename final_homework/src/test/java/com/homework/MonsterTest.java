package com.homework;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.homework.world.*;

import asciiPanel.AsciiPanel;

import java.awt.Color;
class MonsterTest {

    @Test
    void testMonster() {
        World world = mock(World.class);
        Player player = mock(Player.class);

        Monster monster = new Monster(1, 2, 3, 4, 5, Color.RED, 'A', world, player);

        assertEquals(4, monster.get_health());
        assertEquals(3, monster.get_attack());
        assertEquals(5, monster.get_speed());
        assertEquals(1, monster.get_x());
        assertEquals(2, monster.get_y());
        assertEquals('A', monster.get_glyph());
        assertEquals(Color.RED, monster.get_color());
    }

    @Test
    void testSetIndex() {
        World world = mock(World.class);
        Player player = mock(Player.class);
        Monster monster = new Monster(1, 2, 3, 4, 5, Color.RED, 'A', world, player);

        monster.set_index(10);
        assertEquals(10, monster.get_index());
    }

    @Test
    void testAttackJudge() {
        World world = mock(World.class);
        Player player = mock(Player.class);

        Monster monster = new Monster(1, 2, 3, 4, 5, Color.RED, 'A', world, player);

        when(player.get_x()).thenReturn(1);
        when(player.get_y()).thenReturn(2);
        assertTrue(monster.attack_judge());

        when(player.get_x()).thenReturn(3);
        when(player.get_y()).thenReturn(4);
        assertFalse(monster.attack_judge());
    }

    @Test
    void testAttacked() {
        World world = mock(World.class);
        Player player = mock(Player.class);
        Monster monster = new Monster(1, 2, 3, 10, 5, Color.RED, 'A', world, player);
        monster.set_index(1);
        monster.attacked(5);
        assertEquals(5, monster.get_health());

        monster.attacked(5);
        verify(world, times(1)).remove_monster(monster);
    }

    @Test
    void testChasingPlayer() {
        World world = mock(World.class);
        Player player = new Player(82/2, 34/2, 5, 100, 10, AsciiPanel.brightWhite, (char)1, world);
        Monster monster1 = new Monster(1, 2, 3, 4, 5, Color.RED, 'A', world, player);
        Monster monster2 = new Monster(82/2, 10, 3, 4, 5, Color.RED, 'A', world, player);
        Monster monster3 = new Monster(82/2, 20, 3, 4, 5, Color.RED, 'A', world, player);
        Monster monster4 = new Monster(10, 34/2, 3, 4, 5, Color.RED, 'A', world, player);
        Monster monster5 = new Monster(60, 34/2, 3, 4, 5, Color.RED, 'A', world, player);
        monster1.chasing_player();
        monster2.chasing_player();
        monster3.chasing_player();
        monster4.chasing_player();
        monster5.chasing_player();
        //assertEquals(2, monster.get_x());
        //assertEquals(3, monster.get_y());

        //assertEquals("right", monster.get_direction());
    }

    @Test
    void testGetters() {
        World world = mock(World.class);
        Player player = mock(Player.class);
        Monster monster = new Monster(1, 2, 3, 4, 5, Color.RED, 'A', world, player);
        monster.set_index(10);

        assertEquals(10, monster.get_index());
        assertEquals("up", monster.get_direction());
    }
}
