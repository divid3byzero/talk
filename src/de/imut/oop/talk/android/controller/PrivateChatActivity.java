package de.imut.oop.talk.android.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.action.ChatAction;
import de.imut.oop.talk.android.model.AbstractChatMessage;
import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk.android.view.ChatView;
import de.imut.oop.talk.android.worker.CommandProcessor;
import de.imut.oop.talk.android.worker.InCommandReader;
import de.imut.oop.talk4.command.RegistrableContext;
import de.imut.oop.talk4.server.command.set.PrivateMessageCommandServer;
import de.imut.oop.talk4.server.command.set.SyncActiveUserCommandServer;

/**
 * 
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class PrivateChatActivity extends Activity implements ChatAction {

	private ChatView privateChatView;
	private Handler handler;
	private Talk app;
	private InCommandReader inReader;
	private RegistrableContext userContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		privateChatView = (ChatView) View.inflate(this, R.layout.chat_view,
				null);

		privateChatView.setActionListener(this);
		setContentView(privateChatView);

		Intent callingIntent = getIntent();
		userContext = (RegistrableContext) callingIntent
				.getSerializableExtra("user_context");
		handler = new Handler() {

			public void handleMessage(Message msg) {

				AbstractChatMessage chatMessage = (AbstractChatMessage) msg
						.getData().getSerializable("chat_message");
				if ((null != chatMessage)) {
					privateChatView.addMessage(chatMessage);
				}
			}
		};

		app = (Talk) getApplication();
		inReader = new InCommandReader(app, new CommandProcessor(app, handler));
		new Thread(inReader).start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		inReader.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		app.putOutCommand(new SyncActiveUserCommandServer());
		inReader.resume();
	}

	@Override
	public void sendMessage(AbstractChatMessage chatMessage) {
		privateChatView.addMessage(chatMessage);
		app.putOutCommand(new PrivateMessageCommandServer(chatMessage
				.getMessage(), userContext));
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
