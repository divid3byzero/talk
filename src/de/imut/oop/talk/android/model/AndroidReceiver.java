package de.imut.oop.talk.android.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import de.imut.oop.talk4.client.command.RemoteCommandClient;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class AndroidReceiver implements Runnable {

    private ArrayBlockingQueue<RemoteCommandClient> inQueue;
    private Socket socket;

    public AndroidReceiver(Socket socket,
        ArrayBlockingQueue<RemoteCommandClient> inQueue) {

        this.socket = socket;
        this.inQueue = inQueue;
    }

    @Override
    public void run() {

        read();
    }

    private void read() {

        ObjectInputStream clientIn;
        try {
            clientIn = new ObjectInputStream(socket.getInputStream());
            while (!Thread.currentThread().isInterrupted()) {
                RemoteCommandClient remoteCommand = (RemoteCommandClient) clientIn
                    .readObject();
                inQueue.add(remoteCommand);
            }

        } catch (IOException | ClassNotFoundException e1) {
            // TODO Error activity
        }
    }

}
