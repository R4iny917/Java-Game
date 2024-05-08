package com.homework.world;
import java.awt.*;
import java.io.Serializable;


public class Player extends Creature implements Serializable {
    private String direction;
    private int index;
    private long lastTime;
    public Player(int x, int y, int attack, int health,int speed ,Color color, char glyph,World world){
        super(x, y, attack, health, speed, color, glyph, world);
        direction = "right";
    }

    @Override
    public synchronized void attacked(int damage){
        if(this.get_health() > 0){
            super.attacked(damage);
        }
    
    }

    public String get_direction(){
        return this.direction;
    }

    public void set_direction(String direction){
        this.direction = direction;
    }

    public void set_index(int index){
        this.index = index;
    }

    public int get_index(){
        return this.index;
    }

    public void set_lastTime(long time){
        this.lastTime = time;
    }

    public long get_lastTime( ){
        return this.lastTime;
    }

    public void attach_items(){
        for(Item item:world.get_items()){
            if(this.x == item.get_x() && this.y == item.get_y()){
                if(item.get_color() == Color.GREEN)
                    health += 10;
                else if(item.get_color() == Color.RED)
                    attack += 5;
                else 
                    speed  += 10;
                world.remove_item(item);
                break;
            }
        }
    }

    public World get_world( ){
        return this.world;
    }
}
