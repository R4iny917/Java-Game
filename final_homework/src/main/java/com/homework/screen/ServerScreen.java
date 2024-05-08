package com.homework.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.homework.generator.BulletGenerator;
import com.homework.generator.ItemGenerator;
import com.homework.multiplayer.Server;
import com.homework.world.*;

import asciiPanel.AsciiPanel;

public class ServerScreen implements Screen {
    //private static final int SCREEN_WIDTH = 82;
    private static final int SCREEN_HEIGHT = 36;
    private static final int WORLD_WIDTH = 82;
    private static final int WORLD_HEIGHT = 34;
    private World mainWorld;
    private Player mainPlayer;
    private Server server;
    public static boolean multiplayer = false;
    public ServerScreen(){
        mainWorld  = new World(WORLD_WIDTH, WORLD_HEIGHT);
        mainWorld.server_is_on = true;
        Random random = new Random();
        float hue = random.nextFloat();
        float saturation = random.nextFloat();
        float brightness = 0.5f + random.nextFloat() / 2; // 生成0.5-1的随机亮度
        // 创建一个随机的亮色
        mainPlayer =  new Player(random.nextInt(WORLD_WIDTH - 2) + 1, random.nextInt(WORLD_HEIGHT - 2) + 1, 5, 20, 10, Color.getHSBColor(hue, saturation, brightness), (char)1, mainWorld);
        mainPlayer.set_index(0);
        mainPlayer.set_lastTime(0);
        mainWorld.add_player(mainPlayer);
        mainWorld.load_map();
        multiplayer = true;
        ItemGenerator itemGenerator = new ItemGenerator(WORLD_WIDTH, WORLD_HEIGHT, mainPlayer, mainWorld.get_items());
        itemGenerator.create_items();
        BulletGenerator bulletGenerator = new BulletGenerator(WORLD_WIDTH, WORLD_HEIGHT, mainPlayer, mainWorld.get_bullets());
        bulletGenerator.create_bullets();
        try {
            server = new Server("localhost");     
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.set_screen(this);
        new Thread(server).start();
    }

    public void create_client(int index){
        System.out.println("Create Client World:" + index);
        Random random = new Random();
        float hue = random.nextFloat();
        float saturation = random.nextFloat();
        float brightness = 0.5f + random.nextFloat() / 2; 
        Player player =  new Player(random.nextInt(WORLD_WIDTH - 2) + 1, random.nextInt(WORLD_HEIGHT - 2) + 1, 5, 20, 10, Color.getHSBColor(hue, saturation, brightness), (char)1, mainWorld);
        player.set_index(index);
        player.set_lastTime(0);
        mainWorld.add_player(player);
        BulletGenerator bulletGenerator = new BulletGenerator(WORLD_WIDTH, WORLD_HEIGHT, player, mainWorld.get_bullets());
        bulletGenerator.create_bullets();
        try {
            server.broadcast(mainWorld);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void displayOutput(AsciiPanel terminal) throws IOException {
        if(player_live_condition() == false){
            LoseScreen.server_is_down = true;
            LoseScreen.displayOutput(terminal);
            if(Server.channel_number > 0){
                mainWorld.server_is_on = false;
                server.broadcast(mainWorld);
            }
            return;
        }
        List<Player>players = mainWorld.get_players();
        remove_dead_players(players);
        for(Wall wall:mainWorld.get_walls()){
            terminal.write(wall.get_glyph(),wall.get_x(),wall.get_y(),wall.get_color());
        }
        for(Item item:mainWorld.get_items()){
            terminal.write(item.get_glyph(),item.get_x(),item.get_y(),item.get_color());
        }
        for(Bullet bullet:mainWorld.get_bullets()){
            terminal.write(bullet.get_glyph(),bullet.get_x(),bullet.get_y(),bullet.get_color());
        }
        for(Player player: players){
            terminal.write(player.get_glyph(),player.get_x(),player.get_y(),player.get_color());
            if(player.get_index() == 0)mainPlayer = player;
        }
        String state = "Health: " + Integer.toString(mainPlayer.get_health()) + " Attack: "+ Integer.toString(mainPlayer.get_attack()) + " Speed: "+Integer.toString(mainPlayer.get_speed());
        terminal.write(state,0,SCREEN_HEIGHT - 1,AsciiPanel.brightWhite);
        if(Server.channel_number >= 1)
            server.broadcast(mainWorld);
    }

    public void remove_dead_players(List<Player> players) {
        Iterator<Player> playerIterator = players.iterator();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();
            if (!player.is_alive()) {
                playerIterator.remove();
            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        
        List<Player>players = mainWorld.get_players();
        Player mainplayer = players.get(0);
        for(Player player : players)
            if(player.get_index() == key.getID()){
                mainplayer = player;
                break;
            }
        //System.out.println("Index: " + mainplayer.get_index());
        long currentTime = System.currentTimeMillis();
        long lastTime = mainplayer.get_lastTime();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A:
                if(currentTime - lastTime  >= 5000/mainplayer.get_speed()){
                    mainplayer.move_by(-1, 0);
                    mainplayer.set_direction("left");
                    mainplayer.attach_items();
                    mainplayer.set_lastTime(currentTime);
                }
                break;
            case KeyEvent.VK_D:
                if(currentTime - lastTime >= 5000/mainplayer.get_speed()){
                    mainplayer.move_by(1, 0);
                    mainplayer.set_direction("right");
                    mainplayer.attach_items();
                    mainplayer.set_lastTime(currentTime);
                }
                break;
            case KeyEvent.VK_W:
                if(currentTime - lastTime >= 5000/mainplayer.get_speed()){
                    mainplayer.move_by(0, -1);
                    mainplayer.set_direction("up");
                    mainplayer.attach_items();
                    mainplayer.set_lastTime(currentTime);
                }
                break;
            case KeyEvent.VK_S:
                if(currentTime - lastTime >= 5000/mainplayer.get_speed()){
                    mainplayer.move_by(0, 1);
                    mainplayer.set_direction("down");
                    mainplayer.attach_items();
                    mainplayer.set_lastTime(currentTime);
                }
                break;
            }
        return this;
    }

    @Override
    public boolean player_live_condition() {
        List<Player>players = mainWorld.get_players();
        return players.get(0).is_alive();
    }

    @Override
    public boolean monsters_live_condition() {
        return false;
    }
    
}
