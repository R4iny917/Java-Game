package com.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.homework.world.*;

import asciiPanel.AsciiPanel;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BulletTest {
    private Bullet bullet;
    private Player player;
    private World world;

    @BeforeEach
    public void setUp() {
        player = new Player(82/2, 34/2, 5, 100, 10, AsciiPanel.brightWhite, (char)1, world); // You might need to replace this with actual Player object initialization
        player.set_index(0);
        world = new World(82, 34); // You might need to replace this with actual World object initialization
        bullet = new Bullet(0, 0, 10, "up", player, Color.YELLOW, world);
    }

    @Test
    void testGetters() {
        assertEquals(10, bullet.get_attack());
        assertEquals(0, bullet.get_x());
        assertEquals(0, bullet.get_y());
        assertEquals(0x1E, bullet.get_glyph());
        assertEquals(Color.YELLOW, bullet.get_color());
    }

    @Test
    public void testKeepMovingUp() {
        bullet = new Bullet(0, 1, 10, "up", player, Color.RED, world);
        bullet.keep_moving();
        assertEquals(0, bullet.get_y());
    }

    @Test
    public void testKeepMovingDown() {
        bullet = new Bullet(0, 0, 10, "down", player, Color.RED, world);
        bullet.keep_moving();
        assertEquals(1, bullet.get_y());
    }

    @Test
    public void testKeepMovingRight() {
        bullet = new Bullet(0, 0, 10, "right", player, Color.RED, world);
        bullet.keep_moving();
        assertEquals(1, bullet.get_x());
    }

    @Test
    public void testKeepMovingLeft() {
        bullet = new Bullet(1, 0, 10, "left", player, Color.RED, world);
        bullet.keep_moving();
        assertEquals(0, bullet.get_x());
    }

    @Test
    void testAttackMonster() {
        World world = mock(World.class);
        Monster monster = mock(Monster.class);

        when(world.get_monsters()).thenReturn(Arrays.asList(monster));
        when(monster.get_x()).thenReturn(1);
        when(monster.get_y()).thenReturn(1);
        when(monster.get_health()).thenReturn(1);

        Bullet yourObject =new Bullet(player.get_x() + 1, player.get_y(), player.get_attack(),player.get_direction(), player, AsciiPanel.brightYellow,world);
        yourObject.attack_monster();

        verify(world, times(0)).bullet_attack_monster(monster, yourObject.get_attack());
        assert(true);
    }

    @Test
    void testAttackPlayer() {
        World world = mock(World.class);
        Bullet yourObject =new Bullet(player.get_x() + 1, player.get_y(), player.get_attack(),player.get_direction(), player, AsciiPanel.brightYellow,world);
        yourObject.switch_attack_players_on();
        Player player1 = new Player(82/2, 34/2, 5, 100, 10, AsciiPanel.brightWhite, (char)1, world); 
        player1.set_index(10);
        Player player2 = new Player(82/2, 34/2, 5, 100, 10, AsciiPanel.brightWhite, (char)1, world); 
        player2.set_index(20);
        world.add_player(player1);
        world.add_player(player2);
        yourObject.attack_players();
    }

}
