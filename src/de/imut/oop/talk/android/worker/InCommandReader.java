package de.imut.oop.talk.android.worker;

import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk4.client.command.RemoteCommandClient;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class InCommandReader implements Runnable {

	private Object semaphore;
	private boolean paused;
	private boolean finished;
	private Talk app;
	private CommandProcessor commandProcessor;

	public InCommandReader(Talk app, CommandProcessor commandProcessor) {
		this.app = app;
		this.commandProcessor = commandProcessor;
		semaphore = new Object();
		paused = false;
		finished = false;
	}

	@Override
	public void run() {

		while (!finished) {
			RemoteCommandClient nextCommand = app.getNextCommand();

			if (nextCommand != null) {
				nextCommand.execute(commandProcessor, app.getChatContext());
			}

			synchronized (semaphore) {
				while (paused) {
					try {
						semaphore.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

	public void pause() {
		synchronized (semaphore) {
			paused = true;
		}
	}

	public void resume() {
		synchronized (semaphore) {
			paused = false;
			semaphore.notifyAll();
		}
	}

}
