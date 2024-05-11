package com.homework.world;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Bullet implements Runnable,Serializable{
    private String direction;
    private int x;
    private int y;
    private int attack;
    private World world;
    private Color color;
    private char glyph;
    private boolean status;
    private Player player;
    private boolean attack_player = false;

    public Bullet(int x, int y, int attack,String direction, Player player,Color color,World world){
        this.x = x;
        this.y = y;
        this.attack = attack;
        this.direction = direction;
        this.world = world;
        this.color = color;
        this.status = true;
        this.player = player;
        switch (direction) {
            case "up":glyph = 0x1E;
                break;
            case "down":glyph = 0x1F;
                break;
            case "left":glyph = 0x11;
                break;
            case "right":glyph = 0x10;
                break;
            default:
                break;
        }
    }

    public void attack_monster(){
        List<Monster>monsters = world.get_monsters();
        for(Monster monster:monsters){
            if((monster.get_x() - x == 1 && monster.get_y() == y && this.direction.equals("right"))
            || (x - monster.get_x() == 1 && monster.get_y() == y && this.direction.equals("left"))
            || (monster.get_y() - y == 1 && monster.get_x() == x && this.direction.equals("down"))
            || (y - monster.get_y() == 1 && monster.get_x() == x && this.direction.equals("up"))
            || (y == monster.get_y() && x == monster.get_x())){
                if(monster.get_health() > 0){
                    world.bullet_attack_monster(monster, attack);
                    status = false;
                    break;
                }
            } 
        }  
    }

    public void attack_players(){
        List<Player>players = world.get_players();
        for(Player player:players){
            if(player.get_index() != this.player.get_index())
                if((player.get_x() - x == 1 && player.get_y() == y && this.direction.equals("right"))
                || (x - player.get_x() == 1 && player.get_y() == y && this.direction.equals("left"))
                || (player.get_y() - y == 1 && player.get_x() == x && this.direction.equals("down"))
                || (y - player.get_y() == 1 && player.get_x() == x && this.direction.equals("up"))
                || (y == player.get_y() && x == player.get_x())){
                    if(player.get_health() > 0){
                        player.attacked(this.attack);
                        status = false;
                        break;
                    }
                } 
        } 
    }

    public void switch_attack_players_on(){
        this.attack_player = true;
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

    public int get_attack(){
        return this.attack;
    }

    public void keep_moving(){
        if(direction.equals("up"))y-=1;
        if(direction.equals("down"))y+=1;
        if(direction.equals("right"))x+=1;
        if(direction.equals("left"))x-=1;
        if((x < 2 && direction.equals("left")) || (y < 2 && direction.equals("up"))
         ||(x >= world.get_width() - 2 && direction.equals("right"))
         ||(y >= world.get_height() - 2 && direction.equals("down"))){
            status = false;
        }
    } 

    public void run(){
        this.attack_monster();
        while (status == true) {
            if(Esc.get_world_status() == false){
                this.keep_moving();
                this.attack_monster();
                if(attack_player == true);
                    this.attack_players();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50); // 暂停0.1秒       
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        world.remove_bullet(this);
    }
}
