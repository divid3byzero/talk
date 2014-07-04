package de.imut.oop.talk.android.controller;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.action.ActiveUserAction;
import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk.android.view.ListActiveUsersView;
import de.imut.oop.talk.android.worker.CommandProcessor;
import de.imut.oop.talk.android.worker.InCommandReader;
import de.imut.oop.talk4.command.RegistrableContext;
import de.imut.oop.talk4.server.command.set.SyncActiveUserCommandServer;

/**
 * 
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class ListActiveUserActivity extends Activity implements
		ActiveUserAction {

	ListActiveUsersView activeUsersView;
	private Handler handler;
	private Talk app;
	Object semaphore = new Object();
	private InCommandReader inReader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activeUsersView = (ListActiveUsersView) View.inflate(this,
				R.layout.list_active_users_view, null);
		activeUsersView.setActionListener(this);
		setContentView(activeUsersView);

		handler = new Handler() {

			@SuppressWarnings("unchecked")
			public void handleMessage(Message msg) {

				List<RegistrableContext> userContexts = (List<RegistrableContext>) msg
						.getData().getSerializable("active_users");
				if ((null != userContexts)) {
					activeUsersView.addUsers(userContexts);
				}
			}
		};

		app = (Talk) getApplication();
		app.putOutCommand(new SyncActiveUserCommandServer());
		inReader = new InCommandReader(app, new CommandProcessor(app, handler));
		new Thread(inReader).start();;
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
	public Activity getActivity() {
		return this;
	}

	@Override
	public void startPrivateChat(RegistrableContext registrableContext) {
		Intent privateChatIntent = new Intent(this, PrivateChatActivity.class);
		privateChatIntent.putExtra("user_context", (Serializable) registrableContext);
		startActivity(privateChatIntent);
	}
	
}
