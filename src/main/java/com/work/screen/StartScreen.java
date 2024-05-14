package com.work.screen;
import java.awt.Color;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.work.loadsave.PlayRecord;
import com.work.world.MapPath;

/**
 *
 * @author Aeranythe Echosong
 */
public class StartScreen implements Screen {

    int a;int b;
    int start_length,end_length;
    int now_status = 0;//0表示处于开始游戏菜单，1表示处于加载游戏菜单，2表示加载游戏地图，3表示播放游戏录制
    Color white, red, background;
    String line = "+-----------------+";
    boolean ready_to_multiple = false;

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.VK_ENTER && now_status == 0)
            return new PlayScreen();
        else if(key.getKeyCode() == KeyEvent.VK_ENTER && now_status == 1){
            PlayScreen.switch_load_game_status();
            return new PlayScreen();
        }
        else if(key.getKeyCode() == KeyEvent.VK_ENTER && now_status == 2){
            PlayRecord.switch_record_status();
            try {
                return new RecordScreen();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(key.getKeyCode() == KeyEvent.VK_ENTER && now_status == 3){
            MapPath.choose_path();
            return this;
        }
        else if(key.getKeyCode() == KeyEvent.VK_ENTER && now_status == 4){
            if(ready_to_multiple == true) ready_to_multiple = false;
            else ready_to_multiple = true;
            return this;
        }
        else if(key.getKeyCode() == KeyEvent.VK_ENTER && now_status == 5){
            return new ServerScreen();
        }
        else if(key.getKeyCode() == KeyEvent.VK_ENTER && now_status == 6){
            return new ClientScreen();
        }
        else if(key.getKeyCode() == KeyEvent.VK_S || key.getKeyCode() == KeyEvent.VK_DOWN){
            if(now_status == 0) now_status = 1;
            else if( now_status == 1)now_status = 2;
            else if(now_status == 2)now_status = 3;
            else if(now_status == 3)now_status = 4;
            else if(now_status == 4 && ready_to_multiple == true)now_status = 5;
            else if(now_status == 5 && ready_to_multiple == true)now_status = 6;
            return this;
        }
        else if(key.getKeyCode() == KeyEvent.VK_W ||key.getKeyCode() == KeyEvent.VK_UP){
            if(now_status == 1)now_status = 0;
            else if( now_status == 2)now_status = 1;
            else if( now_status == 3)now_status = 2;
            else if( now_status == 4)now_status = 3;
            else if( now_status == 5 && ready_to_multiple == true)now_status = 4;
            else if( now_status == 6 && ready_to_multiple == true)now_status = 5;
            return this;
        }    
        return this;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        red = Color.RED;
        white = Color.WHITE;
        background = new Color(139, 35, 35);
        String title = "|  _ \\| '__/ _ \\| '_ ` _ \\ / _` | __/ _ \\ ";
        String start_game =   "   Start  Game   ";
        String load_game =    "   Load   Game   ";
        String load_map =     "   Load    Map   ";
        String record_game =  "   Play Record   ";
        String multiple =     " Multiple Player ";
        String server   =     "  Server Player  ";
        String client   =     "  Client Player  ";
        start_length =  (terminal.getWidthInCharacters() - start_game.length())/2;
        end_length = start_length + start_game.length();
        a = (terminal.getWidthInCharacters() - title.length())/2;
        b = terminal.getHeightInCharacters()/4 + 3;
        terminal.write(" ____           ",a, b+0,white);terminal.write("                 _ ",a+16, b+0,red);
        terminal.write("| __ ) _ __ ___ ",a, b+1,white);terminal.write(" _ __ ___   __ _| |_ ___",a+16, b+1,red);
        terminal.write("|  _ \\| '__/ _ \\",a,b+2,white);terminal.write("| '_ ` _ \\ / _` | __/ _ \\ ",a+16,b+2,red);
        terminal.write("| |_) | | | (_) ", a, b+3,white);terminal.write("| | | | | | (_| | || (_) |", a+16, b+3,red);
        terminal.write("|____/|_|  \\___/", a, b+4,white);terminal.write("|_| |_| |_|\\__,_|\\__\\___/", a+16, b+4,red);
        display_option(start_game, b+5, terminal, 0);
        display_option(load_game, b+7, terminal, 1);
        display_option(record_game, b+9, terminal, 2);
        display_option(load_map, b+11, terminal, 3);
        display_option(multiple, b+13, terminal, 4);
        if(ready_to_multiple){
            display_option(server, b+15, terminal, 5);
            display_option(client, b+17, terminal, 6);
            terminal.write(line, start_length - 1, b+19,white);
        }
        else
            terminal.write(line, start_length - 1, b+15,white);
    }

    public void display_option(String option,int height,AsciiPanel terminal,int status){
        terminal.write(line, start_length - 1, height,white);
        terminal.write("|", start_length - 1, height + 1,white);
        if(now_status == status)
            terminal.write(option, start_length, height + 1,white,background);
        else
            terminal.write(option, start_length, height + 1,white);
        terminal.write("|", end_length, height + 1,white);
    }


    public boolean player_live_condition(){
        return true;
    }

    public boolean monsters_live_condition(){
        return false;
    }

}

