package com.work;

import org.junit.jupiter.api.Test;

import com.work.world.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import asciiPanel.AsciiPanel;

import java.util.List;
import java.util.ArrayList;
class WorldTest {

    @Test
    void testLoadMap(){
        World world = new World(10, 10);
        world.load_map();
    }

    @Test
    void testRestartEveryThread(){
        World world = new World(10, 10);
        List<Bullet>bullets = new ArrayList<>();
        List<Monster>monsters = new ArrayList<>();
        Bullet bullet = new Bullet(5, 5, 5, "right", null, null, world);
        bullets.add(bullet);
        Monster monster = new Monster(10, 10, 5, 10, 10, null, (char)1, world, null);
        monsters.add(monster);
        world.set_everything(monsters, bullets, null, null);
        world.restart_every_thread();
        Player player = new Player(0, 0, 0, 0, 0, null, (char)0, world);
        world.set_player(player);
    }

    @Test
    void testGetters() {
        World world = new World(10, 20);
        assertEquals(10, world.get_width());
        assertEquals(20, world.get_height());
        List<Monster> monsters = world.get_monsters();
        assertNotNull(monsters);
        List<Bullet> bullets = world.get_bullets();
        assertNotNull(bullets);
        List<Item> items = world.get_items();
        assertNotNull(items);
        List<Wall> walls = world.get_walls();
        assertNotNull(walls);
        Player player = mock(Player.class);
        world.set_player(player);
        Player retplayer = world.get_player();
        assertEquals(player, retplayer);
    }

        @Test
    void testRemoveMonster() {
        World world = new World(10, 20);
        Monster monster = mock(Monster.class);
        List<Monster> monsters = new ArrayList<>();
        monsters.add(monster);
        world.remove_monster(monster);

        assertFalse(world.get_monsters().contains(monster));
    }

    @Test
    void testRemoveBullet() {
        World world = new World(10, 20);
        Bullet bullet = mock(Bullet.class);
        List<Bullet> bullets = new ArrayList<>();
        bullets.add(bullet);
        world.remove_bullet(bullet);
        assertFalse(world.get_bullets().contains(bullet));
    }

    @Test
    void testRemoveItem() {
        World world = new World(10, 20);
        Item item = mock(Item.class);
        List<Item> items = new ArrayList<>();
        items.add(item);
        world.remove_item(item);
        assertFalse(world.get_items().contains(item));
    }

    @Test
    void testBulletAttackMonster() {
        World world = new World(10, 20);
        Monster monster = mock(Monster.class);
        List<Monster> monsters = new ArrayList<>();
        monsters.add(monster);
        world.bullet_attack_monster(monster, 5);
        verify(monster, times(1)).attacked(5);
    }

       @Test
    public void testCreateEverything() {
        World world = new World(82, 34);
        Player player = new Player(82/2, 34/2, 5, 20, 10, AsciiPanel.brightWhite, (char)1, world);
        world.set_player(player); 
    }

}
