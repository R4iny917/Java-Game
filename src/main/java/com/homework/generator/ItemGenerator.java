package com.homework.generator;

import java.util.List;
import java.util.Random;

import com.homework.screen.PlayScreen;

import com.homework.world.Esc;
import com.homework.world.Item;
import com.homework.world.Player;

public class ItemGenerator {
    int width;
    int height;
    Player player;
    List<Item>items;
    
    public ItemGenerator(int width, int height,Player player,List<Item>items){
        this.width = width;
        this.height = height;
        this.player = player;
        this.items = items;
    }
    public void create_items(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                if(PlayScreen.get_load_game_status() == false){
                    try {
                        Thread.sleep(1000); // 等待5秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(true){
                    if(Esc.get_world_status() == false){
                        Random random = new Random();
                        int x = random.nextInt(width - 2) + 1;
                        int y = random.nextInt(height - 2) + 1;  
                        Item item = new Item(x,y);
                        items.add(item);
                    }
                    try {
                        Thread.sleep(5000); // 暂停5秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();  
    }
}
