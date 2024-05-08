package com.homework.world;
import java.awt.Color;
import java.io.Serializable;


public class Wall implements Serializable {
    int x;
    int y;
    char glyph;
    Color color;
    public Wall(int x, int y,int width,int height){
        this.x = x;
        this.y = y;
        if( x >= 1 && x < width - 1 && y >= 1 && y < height -1){
            color = Color.darkGray;
            glyph = (char)7;
        }
        else{
            color = new Color(139, 90, 43);
            glyph = 0xDB;
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
