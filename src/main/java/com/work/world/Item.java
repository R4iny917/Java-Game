package com.work.world;

import java.io.Serializable;
import java.util.Random;
import java.awt.Color;

public class Item implements Serializable {
    private int x;
    private int y;
    private char glyph;
    private Color color;

    public Item(int x, int y){
        this.x = x;
        this.y = y;
        Random random = new Random();
        int choice = random.nextInt(3);
        if(choice == 0){
            glyph = (char)5;
            color = Color.RED;
        }
        else if(choice == 1){
            glyph = (char)3;
            color = Color.GREEN;
        }
        else{
            glyph = (char)4;
            color = Color.LIGHT_GRAY;
        }
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
}
