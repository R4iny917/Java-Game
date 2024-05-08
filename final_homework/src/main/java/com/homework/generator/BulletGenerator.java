package com.homework.generator;

import java.util.List;

import com.homework.screen.ServerScreen;
import com.homework.world.Bullet;
import com.homework.world.Esc;
import com.homework.world.Player;

public class BulletGenerator {
    int width;
    int height;
    Player player;
    List<Bullet>bullets;
    
    public BulletGenerator(int width, int height,Player player,List<Bullet>bullets){
        this.width = width;
        this.height = height;
        this.player = player;
        this.bullets = bullets;
    }

    public void create_bullets( ){
        Thread thread = new Thread() {
            @Override
            public void run() {
                while(true){
                    if(Esc.get_world_status() == false && player.is_alive()){  
                        Bullet bullet;
                        if(player.get_direction().equals("up"))
                            bullet = new Bullet(player.get_x(), player.get_y() - 1, player.get_attack(),player.get_direction(), player, player.get_color(),player.get_world());
                        else if(player.get_direction().equals("down"))
                            bullet = new Bullet(player.get_x(), player.get_y() + 1, player.get_attack(),player.get_direction(), player, player.get_color(),player.get_world());
                        else if(player.get_direction().equals("left"))
                            bullet = new Bullet(player.get_x() - 1, player.get_y(), player.get_attack(),player.get_direction(), player, player.get_color(),player.get_world());
                        else 
                            bullet = new Bullet(player.get_x() + 1, player.get_y(), player.get_attack(),player.get_direction(), player,player.get_color(),player.get_world());
                        if(bullet.get_x()>= 1 && bullet.get_x() < width - 1 && bullet.get_y() >= 1 && bullet.get_y() < height - 1){
                            if(ServerScreen.multiplayer == true)
                                bullet.switch_attack_players_on();
                            bullets.add(bullet);
                            Thread bullet_thread = new Thread(bullet);
                            bullet_thread.start();
                        }
                    }
                    try {
                        Thread.sleep(1000); // 暂停1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
