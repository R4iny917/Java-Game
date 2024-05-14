package com.work;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.work.loadsave.*;
import com.work.world.Esc;
import com.work.world.World;


public class RecordTest {
    @Test
    public void testStatus(){
        boolean game_record_status = GameRecord.get_record_status();
        GameRecord.switch_recording_status();
        assertNotEquals(game_record_status,GameRecord.get_record_status(),"Record Status unswitched");
        GameRecord.switch_recording_status();
        assertEquals(game_record_status,GameRecord.get_record_status(),"Record Status unswitched");

        boolean game_play_status = PlayRecord.get_record_status();
        PlayRecord.switch_record_status();
        assertNotEquals(game_play_status,PlayRecord.get_record_status(),"Play Status unswitched");
        PlayRecord.switch_record_status();
        assertEquals(game_play_status,PlayRecord.get_record_status(),"Play Status unswitched");

        boolean esc_status = Esc.get_world_status();
        Esc.switch_world_status();
        assertNotEquals(esc_status,Esc.get_world_status(),"Esc Status unswitched");
        Esc.switch_world_status();
        assertEquals(esc_status,Esc.get_world_status(),"Record Status unswitched");

    }

    @Test
    public void testRecordGame() throws ClassNotFoundException, IOException{
        for(int i = 0; i <10; ++i){
            World world = new World(i,i+1);
            GameRecord.record_game(world);
        }
    }

}
