package com.work.screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import com.work.multiplayer.Client;
import com.work.world.Bullet;
import com.work.world.Item;
import com.work.world.Player;
import com.work.world.Wall;
import com.work.world.World;

import asciiPanel.AsciiPanel;

public class ClientScreen implements Screen {
    //private static final int SCREEN_WIDTH = 82;
    private static final int SCREEN_HEIGHT = 36;
    private static final int WORLD_WIDTH = 82;
    private static final int WORLD_HEIGHT = 34;
    private World mainWorld;
    private Client client;
    private int index;
    public static boolean client_is_on = true;
    public ClientScreen() {
        mainWorld  = new World(WORLD_WIDTH, WORLD_HEIGHT);
        client = new Client();
        index = client.get_port();
    }

    public void set_world(World world){
        this.mainWorld = world;
        System.out.println("Set the World");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) throws IOException {
        client.readByte();
        mainWorld = client.readMessage();
        if(player_live_condition() == false){
            LoseScreen.displayOutput(terminal);
            if(client_is_on == true){
                client.sendMessage("m");
                client_is_on = false;
            }
            return;
        }

        if(mainWorld.server_is_on == false){
            LoseScreen.displayOutput(terminal);
            LoseScreen.server_is_down = true;
            return;
        }
        List<Player>players = mainWorld.get_players();
        if(players.size() <= 1)return;
        Player mainPlayer = players.get(0);
        for(Player player : players)
            if(player.get_index() == index){
                mainPlayer = player;
                break;
            }
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
            if(player.get_index() == index)mainPlayer = player;
        }
        String state = "Health: " + Integer.toString(mainPlayer.get_health()) + " Attack: "+ Integer.toString(mainPlayer.get_attack()) + " Speed: "+Integer.toString(mainPlayer.get_speed());
        terminal.write(state,0,SCREEN_HEIGHT - 1,AsciiPanel.brightWhite);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char ch = key.getKeyChar();
        client.sendMessage(String.valueOf(ch));
        return this;
    }

    @Override
    public boolean player_live_condition() {
        List<Player>players = mainWorld.get_players();
        for(Player player : players){
            if(player.get_index() == index)
                return true;
        }
        return false;
    }

    @Override
    public boolean monsters_live_condition() {
        return false;
    }
    
}
