package com.homework.loadsave;


import com.homework.world.Bullet;
import com.homework.world.Item;
import com.homework.world.Monster;
import com.homework.world.Wall;
import com.homework.world.World;


import asciiPanel.AsciiPanel;

public class PlayRecord {
    private static boolean is_playing_record = false;
    public static boolean is_under_test = false;
    public static boolean get_record_status(){
        return is_playing_record;
    }

    public static void switch_record_status(){
        if(is_playing_record == true) is_playing_record = false;
        else is_playing_record =true;
    }

    public static void display_every_frame(AsciiPanel terminal,World world){
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
        terminal.write(world.get_player().get_glyph(),world.get_player().get_x(),world.get_player().get_y(),world.get_player().get_color());
        String state = "Health: " + Integer.toString(world.get_player().get_health()) + " Attack: "+ Integer.toString(world.get_player().get_attack()) + " Speed: "+Integer.toString(world.get_player().get_speed());
        terminal.write(state,0,world.get_height() + 1,AsciiPanel.brightWhite);
    }
}
