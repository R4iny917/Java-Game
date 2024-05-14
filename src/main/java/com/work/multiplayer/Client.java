package com.work.multiplayer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.work.screen.ClientScreen;
import com.work.world.World;

/**
 * 
 * Test client for NIO server
 *
 */
public class Client  {
    private ByteBuffer buffer;
    private SocketChannel client;
    private int port;
    private int capacity;

	public  Client() {

		InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8000);
        buffer = ByteBuffer.allocate(1024);
        try {
            client = SocketChannel.open(hostAddress);
            System.out.println("Client... started");
            //System.out.println("Connected to server from local port: " + client.socket().getLocalPort());
            port = client.socket().getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
            ClientScreen.client_is_on = false;
        }	
	}

    public int get_port(){
        return this.port;
    }

    public void sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            client.write(buffer);
            buffer.clear();
            //System.out.println("Send!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readByte(){
        try {
            ByteBuffer readBuffer = ByteBuffer.allocate(4);
            int numRead = client.read(readBuffer);
            if (numRead > 0) {
                readBuffer.flip();
                this.capacity = readBuffer.getInt();
                //System.out.println("Got capacity: " + capacity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World readMessage() {
        try {
            //System.out.println("Read Message");
            ByteBuffer readBuffer = ByteBuffer.allocate(capacity * 4);
            int numRead = client.read(readBuffer);
            if (numRead > 0) {
                byte[] data = new byte[numRead];
                System.arraycopy(readBuffer.array(), 0, data, 0, numRead);

                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(bis);
                World world = (World) ois.readObject();
                //System.out.println("Got mainWorld");
                return world;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
