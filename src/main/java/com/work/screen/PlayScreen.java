package com.work.screen;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.work.generator.*;
import com.work.loadsave.GameLoad;
import com.work.loadsave.GameRecord;
import com.work.loadsave.GameSave;
import com.work.world.*;

import java.awt.Color;

import asciiPanel.*;

public class PlayScreen implements Screen {
    //private static final int SCREEN_WIDTH = 82;
    private static final int SCREEN_HEIGHT = 36;
    private static final int WORLD_WIDTH = 82;
    private static final int WORLD_HEIGHT = 34;
    private int status = 0;//0表示保存地图，1表示保存游戏进度，2表示开始录制游戏
    private World world;
    private Player player;
    private static long lastTime = 0;
    private static boolean is_load_game = false;

    public PlayScreen(){
        super();
        //System.out.println("Show Play Screen");
        if(is_load_game == false)
            world = new World(WORLD_WIDTH, WORLD_HEIGHT);
        else
            world = GameLoad.load_game();
        player = new Player(WORLD_WIDTH/2, WORLD_HEIGHT/2, 5, 20, 10, AsciiPanel.brightWhite, (char)1, world);
        world.set_player(player);
        if(is_load_game == true){
            world.restart_every_thread();
        }
        else{
            world.load_map();
        }
        BulletGenerator bulletGenerator = new BulletGenerator(WORLD_WIDTH, WORLD_HEIGHT, player, world.get_bullets());
        bulletGenerator.create_bullets();
        MonstersGenerator monstersGenerator = new MonstersGenerator(WORLD_WIDTH, WORLD_HEIGHT, player, world.get_monsters());
        monstersGenerator.create_monster(); 
        ItemGenerator itemGenerator = new ItemGenerator(WORLD_WIDTH, WORLD_HEIGHT, player, world.get_items());
        itemGenerator.create_items();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) throws IOException{
        if(monsters_live_condition() == false){
            WinScreen.displayOutput(terminal);
            return;
        }
        if(player_live_condition() == false){
            LoseScreen.displayOutput(terminal);
            return;
        }
        for(Wall wall:world.get_walls()){
            terminal.write(wall.get_glyph(),wall.get_x(),wall.get_y(),wall.get_color());
        }
        for(Item item:world.get_items()){
            terminal.write(item.get_glyph(),item.get_x(),item.get_y(),item.get_color());
        }
        for(Bullet bullet:world.get_bullets()){
            terminal.write(bullet.get_glyph(),bullet.get_x(),bullet.get_y(),bullet.get_color());
        }
        for(Monster monster:world.get_monsters()){
            terminal.write(monster.get_glyph(),monster.get_x(),monster.get_y(),monster.get_color());
        }
        terminal.write(player.get_glyph(),player.get_x(),player.get_y(),player.get_color());
        String state = "Health: " + Integer.toString(player.get_health()) + " Attack: "+ Integer.toString(player.get_attack()) + " Speed: "+Integer.toString(player.get_speed());
        terminal.write(state,0,SCREEN_HEIGHT - 1,AsciiPanel.brightWhite);
        world.set_player(player);
        if(Esc.get_world_status() == true)
            display_esc_window(terminal); 
        if(GameRecord.get_record_status() == true && Esc.get_world_status() == false){        
            try {
                GameRecord.record_game(world);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
         
        }  
    }

    public void display_esc_window(AsciiPanel terminal){
        String line =    "+-------------+";
        String save_map =  "  Save Map    ";
        String save_game=  "  Save Game   ";
        String record_game="";
        if(GameRecord.get_record_status() == false)
            record_game = " Record Game  ";
        else
            record_game = " Stop Record  ";
        Color white = Color.WHITE;
        Color background = new Color(139, 35, 35);
        int start_height = terminal.getHeightInCharacters()/4 + 3;
        int start_width = (terminal.getWidthInCharacters() - line.length())/2;
        display_option(save_map,start_height, terminal, 0, line, start_width + 1,start_width+line.length() - 1, background);
        display_option(save_game,start_height + 2, terminal, 1, line, start_width + 1,start_width+line.length() - 1, background);
        display_option(record_game,start_height + 4, terminal, 2, line, start_width + 1,start_width+line.length() - 1, background);
        terminal.write(line, start_width, start_height+6,white);
    }

    public void display_option(String option,int height,AsciiPanel terminal,int status,String line,int start_length,int end_length,Color background){
        terminal.write(line, start_length - 1, height,Color.WHITE);
        terminal.write("|", start_length - 1, height + 1,Color.WHITE);
        if(this.status == status)
            terminal.write(option, start_length, height + 1,Color.WHITE,background);
        else
            terminal.write(option, start_length, height + 1,Color.WHITE);
        terminal.write("|", end_length, height + 1,Color.WHITE);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key){
        long currentTime = System.currentTimeMillis();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A:
                if(Esc.get_world_status() == false && currentTime - lastTime >= 5000/player.get_speed()){
                    player.move_by(-1, 0);
                    player.set_direction("left");
                    player.attach_items();
                    lastTime = currentTime;
                }
                break;
            case KeyEvent.VK_D:
                if(Esc.get_world_status() == false && currentTime - lastTime >= 5000/player.get_speed()){
                    player.move_by(1, 0);
                    player.set_direction("right");
                    player.attach_items();
                    lastTime = currentTime;
                }
                break;
            case KeyEvent.VK_W:
                if(Esc.get_world_status() == false && currentTime - lastTime >= 5000/player.get_speed()){
                    player.move_by(0, -1);
                    player.set_direction("up");
                    player.attach_items();
                    lastTime = currentTime;
                }
                else{
                    if(status == 1)status = 0;
                    else if(status == 2)status = 1;
                }
                break;
            case KeyEvent.VK_S:
                if(Esc.get_world_status() == false && currentTime - lastTime >= 5000/player.get_speed()){
                    player.move_by(0, 1);
                    player.set_direction("down");
                    player.attach_items();
                    lastTime = currentTime;
                }
                else{
                    if(status == 0)status = 1;
                    else if(status == 1)status = 2;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                Esc.switch_world_status();
                break;
            case KeyEvent.VK_ENTER:
                if(Esc.get_world_status() == true && status == 0)
                    world.save_map();
                if(Esc.get_world_status() == true && status == 1)
                    GameSave.save_game(world);
                if(Esc.get_world_status() == true && status == 2){
                    GameRecord.switch_recording_status();
                    if(GameRecord.get_record_status() == true){
                        GameRecord.start_recording();
                    }
                    else{
                        GameRecord.end_recording();
                    }
                    Esc.switch_world_status();
                }
                break;
        }
        return this;
    }
    
    @Override
    public boolean player_live_condition(){
        return player.is_alive();
    }

    @Override
    public boolean monsters_live_condition(){
        if(world.get_monsters().isEmpty() && MonstersGenerator.get_monster_index()  >= MonstersGenerator.total_monsters)return false;
        else return true;
    }

    public Player getPlayer(){
        return this.player;
    }

    public World getWorld(){
        return this.world;
    }
    
    public static void switch_load_game_status(){
        if(is_load_game == false)PlayScreen.is_load_game = true;
        else is_load_game = false;
    }

    public static boolean get_load_game_status(){
        return is_load_game;
    }
}
