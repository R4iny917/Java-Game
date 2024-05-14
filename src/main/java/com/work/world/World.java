package com.work.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFileChooser;

public class World implements Serializable  {
    private int width;
    private int height;
    public boolean server_is_on = false;
    private Player player;
    private List<Monster>monsters;
    private List<Bullet>bullets;
    private List<Wall>walls;
    private List<Item>items;
    private List<Player>players;
    private static final long serialVersionUID = 1L;
    
    public World(int width,int height){
        this.width = width;
        this.height = height;      
        walls= new ArrayList<>();
        monsters= new ArrayList<>();
        bullets= new ArrayList<>();
        items= new ArrayList<>();
        players = new ArrayList<>();
    }

    public void load_map(){
        try (BufferedReader reader = new BufferedReader(new FileReader(MapPath.get_path()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for(int i = 0; i < line.length();++i){
                    String[] parts = line.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    Wall wall = new Wall(x,y,width,height);
                    walls.add(wall);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(MapPath.get_choice() == false){
            int index = 0;
            Random random = new Random();
            while(index != 64){
                int x = random.nextInt(width - 2) + 1;
                int y = random.nextInt(height - 2) + 1;
                Wall wall = new Wall(x,y,width,height);
                walls.add(wall);
                index++;
            }
        }  
    }

    public void save_map(){
        JFileChooser fileChooser = new JFileChooser();
        File defaultDirectory = new File("D:/Advanced JAVA Programming/j05-R4iny917/final_homework/src/main/java/com/homework/resources");
        fileChooser.setCurrentDirectory(defaultDirectory);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(file)) {
                for(Wall wall:walls){
                    String coordinate = Integer.toString(wall.get_x()) + ","+Integer.toString(wall.get_y());
                    out.println(coordinate);
                }    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void restart_every_thread(){
        for(Bullet bullet: bullets){
            //System.out.println("here");
            Thread bullet_thread = new Thread(bullet);
            bullet_thread.start();
        }
        for(Monster monster: monsters){
            Thread monster_thread = new Thread(monster);
            monster_thread.start();
        }
    }

    public void add_player(Player player){
        players.add(player);
    }

    public void set_player(Player player){
        this.player = player;
    }

    public void set_everything(List<Monster>monsters,List<Bullet>bullets,List<Wall>walls,List<Item>items ){
        this.monsters = monsters;
        this.bullets = bullets;
        this.walls = walls;
        this.items = items;
    }

    public Player get_player( ){
        return this.player; 
    }

    public List<Player> get_players( ){
        return this.players; 
    }

    public int get_width(){
        return this.width;
    }

    public int get_height(){
        return this.height;
    } 
    
    
    public List<Monster> get_monsters(){
        return monsters;
    }

    public List<Bullet> get_bullets(){
        return bullets;
    }

    public List<Wall> get_walls(){
        return walls;
    }

    public List<Item> get_items(){
        return items;
    }

    public synchronized void remove_monster(Monster monster){
        monsters.remove(monster);
    }

    public synchronized void remove_bullet(Bullet bullet){
        bullets.remove(bullet);
    }

    public void remove_item(Item item){
        items.remove(item);
    }

    public synchronized void bullet_attack_monster(Monster monster,int attack){
        monster.attacked(attack);
    }
}
