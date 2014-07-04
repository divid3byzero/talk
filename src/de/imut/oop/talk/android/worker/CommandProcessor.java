package de.imut.oop.talk.android.worker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import de.imut.oop.talk.android.model.AbstractChatMessage;
import de.imut.oop.talk.android.model.ForeignChatMessage;
import de.imut.oop.talk.android.model.StatusMessage;
import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk4.client.command.RemoteCommandExecutorClient;
import de.imut.oop.talk4.command.Context;
import de.imut.oop.talk4.command.RegistrableContext;
import de.imut.oop.talk4.command.RemoteCommand;
import de.imut.oop.talk4.server.command.set.SetContextCommandServer;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class CommandProcessor implements RemoteCommandExecutorClient {

	private Handler handler;
	private Talk app;

	public CommandProcessor(Talk app) {
		this.app = app;
	}

	public CommandProcessor(Talk app, Handler handler) {
		this.app = app;
		this.handler = handler;
	}

	@Override
	public void executeCommand(RemoteCommand arg0) {
	}

	@Override
	public void exit() {
	}

	@Override
	public void setUserContext(RegistrableContext userContext,
			RegistrableContext serverContext) {
		app.setChatContext(serverContext);
	}

	@Override
	public void showMessage(String message, RegistrableContext messageContext,
			RegistrableContext userContext) {

		String userName = null;
		if (null != (userName = app.getUserName())) {
			AbstractChatMessage chatMessage = messageContext.getNickName()
					.equals(userName) ? new StatusMessage(message)
					: new ForeignChatMessage(message,
							messageContext.getNickName());

			Bundle bundle = new Bundle();
			bundle.putSerializable("chat_message", chatMessage);
			Message handlerMessage = new Message();
			handlerMessage.setData(bundle);
			handler.sendMessage(handlerMessage);
		}
	}

	@Override
	public void showPrivateMessage(String message,
			RegistrableContext messageContext, RegistrableContext userMessage) {

		ForeignChatMessage foreignChatMessage = new ForeignChatMessage(message,
				messageContext.getNickName());

		Bundle bundle = new Bundle();
		bundle.putSerializable("chat_message", foreignChatMessage);
		Message handlerMessage = new Message();
		handlerMessage.setData(bundle);
		handler.sendMessage(handlerMessage);
	}

	@Override
	public void cancelPrivateMessage(String s,
			RegistrableContext messageContext, RegistrableContext userContext) {
		// TODO Allow user to cancel private message.
	}

	@Override
	public void setAcceptContext(RegistrableContext messageContext,
			RegistrableContext userContext) {
		Context contextFromServer = (Context) userContext;
		contextFromServer.setNickName(app.getUserName());
		contextFromServer.setRegistrationKey(RegKeyGenerator.generate());
		app.putOutCommand(new SetContextCommandServer(contextFromServer));
	}

	@Override
	public void syncActiveUser(
			BlockingQueue<RegistrableContext> userContextQueue,
			RegistrableContext arg1) {
		List<RegistrableContext> userContextList = new ArrayList<RegistrableContext>();
		RegistrableContext ctx = null;
		while ((ctx = userContextQueue.poll()) != null) {
			userContextList.add(ctx);
		}

		Bundle bundle = new Bundle();
		bundle.putSerializable("active_users", (Serializable) userContextList);
		Message handlerMessage = new Message();
		handlerMessage.setData(bundle);

		if (null != handler) {
			handler.sendMessage(handlerMessage);
		}
	}
}
