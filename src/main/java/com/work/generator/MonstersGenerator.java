package com.homework.generator;

import java.util.List;
import java.util.Random;

import com.homework.screen.PlayScreen;
import com.homework.world.Esc;
import com.homework.world.Monster;
import com.homework.world.Player;

import asciiPanel.AsciiPanel;

public class MonstersGenerator {
    int width;
    int height;
    Player player;
    List<Monster>monsters;
    private static int monster_index = 0;
    public static int total_monsters = 8;
    
    public  MonstersGenerator(int width, int height,Player player,List<Monster>monsters){
        this.width = width;
        this.height = height;
        this.player = player;
        this.monsters = monsters;
    }

    public static int get_monster_index(){
        return monster_index;
    }

    public void create_monster(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                if(PlayScreen.get_load_game_status() == false){
                    try {
                        Thread.sleep(4000); // 等待4秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(monster_index < total_monsters){
                    if(Esc.get_world_status() == false){  
                        //System.out.println("怪兽生成数量：" + monster_index);
                        Random random = new Random();
                        int x = 0; int y = 0;int i = random.nextInt(4);
                        if(i%4 == 0){x = random.nextInt(width - 2) + 1; y = height - 2;}
                        if(i%4 == 1){x = width - 2; y = random.nextInt(height - 2) + 1;}
                        if(i%4 == 2){x =  random.nextInt(width -2) + 1; y = 1;}
                        if(i%4 == 3){x = 1; y = random.nextInt(height - 2) + 1;}
                        Monster monster = new Monster(x, y, 5, 20, 10, AsciiPanel.brightGreen, (char)2, player.get_world(),player);
                        monster.set_index(i);
                        monsters.add(monster);
                        Thread monster_thread = new Thread(monster);
                        monster_thread.start();
                        monster_index++;
                        // 判断条件是否满足
                    }
                    try {
                        Thread.sleep(4000); // 暂停4秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
       
    }
}
