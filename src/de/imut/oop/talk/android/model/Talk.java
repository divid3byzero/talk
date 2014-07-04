package de.imut.oop.talk.android.model;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import android.app.Application;
import de.imut.oop.talk4.client.command.RemoteCommandClient;
import de.imut.oop.talk4.command.Context;
import de.imut.oop.talk4.command.RegistrableContext;
import de.imut.oop.talk4.server.command.RemoteCommandServer;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class Talk extends Application {

	private String userName = "";

	private ArrayBlockingQueue<RemoteCommandServer> outQueue = new ArrayBlockingQueue<RemoteCommandServer>(
			50);
	private ArrayBlockingQueue<RemoteCommandClient> inQueue = new ArrayBlockingQueue<RemoteCommandClient>(
			50);

	private RegistrableContext chatContext;

	@Override
	public void onCreate() {
		super.onCreate();
		chatContext = new Context(0);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public RegistrableContext getChatContext() {
		return chatContext;
	}

	public void setChatContext(RegistrableContext chatContext) {
		this.chatContext = chatContext;
	}

	public synchronized RemoteCommandClient getNextCommand() {
		return inQueue.poll();
	}

	public void putOutCommand(RemoteCommandServer outCommand) {
		outQueue.add(outCommand);
	}

	public void initCommunication(Socket socket) {

		AndroidReceiver receiver = new AndroidReceiver(socket, inQueue);
		AndroidSender sender = new AndroidSender(socket, outQueue);
		ThreadGroup threadGroup = new ThreadGroup("android_communication");
		new Thread(threadGroup, sender).start();
		new Thread(threadGroup, receiver).start();
	}
}
