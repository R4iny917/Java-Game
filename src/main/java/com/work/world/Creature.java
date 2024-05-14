package com.work.world;
import java.awt.*;
import java.io.Serializable;

public class Creature implements Serializable{
    protected int x;
    protected int y;
    protected int speed;
    protected int health;
    protected int attack;
    protected Color color;
    protected char glyph;
    protected World world;

    public Creature(int x, int y, int attack, int health,int speed ,Color color, char glyph,World world){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.glyph = glyph;
        this.world = world;
        this.attack = attack;
        this.health = health;
    }

    public int get_health(){
        return this.health;
    }

    public int get_attack(){
        return this.attack;
    }

    public int get_speed(){
        return this.speed;
    }

    public int get_x(){
        return this.x;
    }

    public int get_y(){
        return this.y;
    }

    public char get_glyph(){
        return this.glyph;
    }

    public Color get_color(){
        return this.color;
    }

    public void move_by(int x, int y){
        this.x +=x;
        this.y +=y;
        if(this.x < 1)this.x = 1;
        if(this.x >=world.get_width() - 1)this.x = world.get_width() - 2;
        if(this.y < 1)this.y = 1;
        if(this.y >= world.get_height() - 1)this.y = world.get_height() - 2;
    }

    public synchronized  void attacked(int damage){
        this.health -= damage;
    }

    public boolean is_alive(){
        if(this.get_health() > 0)return true;
        else return false;
    }
}
