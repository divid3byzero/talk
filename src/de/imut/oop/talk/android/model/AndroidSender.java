package de.imut.oop.talk.android.model;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import de.imut.oop.talk4.server.command.RemoteCommandServer;

public class AndroidSender implements Runnable {
	
	private Socket socket;
	private ArrayBlockingQueue<RemoteCommandServer> outQueue;

	public AndroidSender(Socket socket, ArrayBlockingQueue<RemoteCommandServer> outQueue) {
		this.socket = socket;
		this.outQueue = outQueue;
	}

	@Override
	public void run() {
		sendCommand();
	}

	private void sendCommand() {
		try {

			ObjectOutputStream commandOutStream = new ObjectOutputStream(socket.getOutputStream());
			while (!Thread.currentThread().isInterrupted()) {
				RemoteCommandServer command = outQueue.poll();
				if (command != null) {
					commandOutStream.writeObject(command);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
