package com.homework.world;
import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Monster extends Creature implements Runnable,Serializable{
    private int index;
    private Player player;
    private String direction;

    public Monster(int x, int y, int attack, int health,int speed ,Color color, char glyph,World world,Player player){
        super(x, y, attack, health, speed, color, glyph, world);
        direction = "up";
        this.player = player;
    }

    public void set_index(int index){
        this.index = index;
    }

    public int get_index(){
        return this.index;
    }

    public String get_direction(){
        return this.direction;
    }

    public void run(){
        while(this.is_alive()){
            if(Esc.get_world_status() == false){
                if(attack_judge() == true){
                        player.attacked(this.get_attack());
                    }
                    else{
                        chasing_player();
                    }
                }
            try {
                TimeUnit.MILLISECONDS.sleep(1000); // 暂停1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        world.remove_monster(this);
    }

    public boolean attack_judge(){
        if(Math.abs(this.get_x() - this.player.get_x()) *Math.abs(this.get_x() - this.player.get_x()) + Math.abs(this.get_y() - this.player.get_y()) * Math.abs(this.get_y() - this.player.get_y()) <= 1){
            return true;
        }
        else 
            return false;
    }

    @Override
    public synchronized void attacked(int damage){
        super.attacked(damage);
        if(this.get_health() ==0 )
            world.remove_monster(this);
    }

    public void chasing_player(){
        if(this.get_x() !=this.player.get_x() && this.get_y() != this.player.get_y()){
            if(direction.equals("up") || direction.equals("down")){
                if(this.player.get_x() > this.get_x()){
                    this.move_by(1, 0);direction = "right";
                }
                else{
                    this.move_by(-1, 0);direction = "left";
                }
            }
            else{
                if(this.player.get_y() > this.get_y()){
                    this.move_by(0, 1);direction = "down";
                }
                else{
                    this.move_by(0, -1);direction = "up";
                }
            }
        }
        else if(this.get_x() == this.player.get_x() && this.get_y() != this.player.get_y()){
            if(this.player.get_y() > this.get_y()){
                    this.move_by(0, 1);direction = "down";
                }
            else{
                this.move_by(0, -1);direction = "up";
            }
        }
        else if(this.get_x() != this.player.get_x() && this.get_y() == this.player.get_y()){
            if(this.player.get_x() > this.get_x()){
                    this.move_by(1, 0);direction = "right";
                }
            else{
                this.move_by(-1, 0);direction = "left";
            }
        }
    }

}
