package com.work.multiplayer;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.work.screen.*;
import com.work.world.*;;
/**
 * 
 * This is a simple NIO based server.
 *
 */
public class Server implements Runnable{
	private Selector selector;
    private ServerScreen screen;
	private InetSocketAddress listenAddress;
	private final static int PORT = 8000;
    public static int channel_number = 0;

	public Server(String address) throws IOException {
		listenAddress = new InetSocketAddress(address, PORT);
	}

    @Override
    public void run() {
        try {
            this.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set_screen(ServerScreen screen){
        this.screen = screen;
    }
	/**
	 * Start the server
	 * 
	 * @throws IOException
	 */
	private void startServer() throws IOException {
		this.selector = Selector.open();
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		serverChannel.socket().bind(listenAddress);
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

		System.out.println("Server started on port >> " + PORT);

		while (true) {
			// wait for events
			int readyCount = selector.select();
			if (readyCount == 0) {
				continue;
			}
			// process selected keys...
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();
				// Remove key from set so we don't process it twice
				iterator.remove();
				if (!key.isValid()) {
					continue;
				}
				if (key.isAcceptable()) { // Accept client connections
					this.accept(key);
				} else if (key.isReadable()) { // Read from client
					this.read(key);
				}
			}
		}
	}

	// accept client connection
	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);
		/*
		 * Register channel with selector for further IO (record it for read/write
		 * operations, here we have used read operation)
		 */
		channel.register(this.selector, SelectionKey.OP_READ);
        String str_addr = remoteAddr.toString();
        String realAddress = str_addr.substring(str_addr.length() - 5);
        this.screen.create_client(Integer.valueOf(realAddress));
        channel_number++;
	}

	// read from the socket channel
	private void read(SelectionKey key){
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(256);
		int numRead = -1;
		try {
            numRead = channel.read(buffer);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        String msg = new String(data);
		if (numRead == -1 || msg.equals("m")) {
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			System.out.println("Connection closed by client: " + remoteAddr);
            channel_number--;
			try {
                channel.close();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
			key.cancel();
			return;
		}

		
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        String str_addr = remoteAddr.toString();
        String realAddress = str_addr.substring(str_addr.length() - 5);
        //System.out.println("Received message from: " + remoteAddr);
        KeyEvent keyEvent = construct_KeyEvent(Integer.valueOf(realAddress),msg);
        buffer.clear();
        screen.respondToUserInput(keyEvent);
        // Broadcast the message to all clients
        
	}

    public void broadcast(World world) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(world);
        oos.flush();
        byte[] data = bos.toByteArray();
        ByteBuffer worldBuffer = ByteBuffer.wrap(data);
        int size = data.length;
        //System.out.println("Size of the serialized World object: " + size + " bytes");
        ByteBuffer sizeBuffer = ByteBuffer.allocate(4);  // int is 4 bytes
        sizeBuffer.putInt(size);
        sizeBuffer.flip();  // flip the buffer to prepare it for reading
        
        for (SelectionKey getkey : selector.keys()) {
            Channel targetChannel = getkey.channel();
            if (targetChannel instanceof SocketChannel && getkey.isValid()) {
                //System.out.println("Here comes to broadcast");
                SocketChannel target = (SocketChannel) targetChannel;  
                target.write(sizeBuffer);
                sizeBuffer.rewind();  // rewind the buffer for the next write

                target.write(worldBuffer);
                worldBuffer.rewind();  // rewind the buffer for the next write
            }
        }
    }

    public KeyEvent construct_KeyEvent(int id ,String msg){
        KeyEvent keyEvent = new KeyEvent(new Component(){}, id, 0, 0, KeyEvent.VK_0, '\n');
        if(msg.equals("a"))keyEvent.setKeyCode(KeyEvent.VK_A);
        else if(msg.equals("w"))keyEvent.setKeyCode(KeyEvent.VK_W);
        else if(msg.equals("s"))keyEvent.setKeyCode(KeyEvent.VK_S);
        else if(msg.equals("d"))keyEvent.setKeyCode(KeyEvent.VK_D);
        return keyEvent;
    }
}
