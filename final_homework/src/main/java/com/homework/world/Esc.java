package com.homework.world;

public class Esc {
    private static boolean stop = false; //false表示世界没有暂停
    public static void switch_world_status(){
        if(stop == false)stop = true;
        else stop = false;
    }

    public static boolean get_world_status(){
        return stop;
    }

}
