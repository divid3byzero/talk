package de.imut.oop.talk.android.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.action.ChatAction;
import de.imut.oop.talk.android.model.AbstractChatMessage;
import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk.android.view.ChatView;
import de.imut.oop.talk.android.worker.CommandProcessor;
import de.imut.oop.talk.android.worker.InCommandReader;
import de.imut.oop.talk4.server.command.set.BroadcastMessageServer;
import de.imut.oop.talk4.server.command.set.ExitCommandServer;

/**
 * 
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 * @version 2.0, Changed order of registration process
 *
 */
public class ChatActivity extends Activity implements ChatAction {

	private ChatView chatView;
	private Handler handler;
	private Talk app;
	private CommandProcessor commandProcessor;
	private InCommandReader inReader;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		chatView = (ChatView) View.inflate(this, R.layout.chat_view, null);
		chatView.setActionListener(this);
		setContentView(chatView);
		getActionBar().setDisplayShowTitleEnabled(false);
		app = (Talk) getApplication();
		
		handler = new Handler() {

			public void handleMessage(Message msg) {

				AbstractChatMessage chatMessage = (AbstractChatMessage) msg
						.getData().getSerializable("chat_message");
				if ((null != chatMessage)) {
					chatView.addMessage(chatMessage);
				}
			}
		};

		commandProcessor = new CommandProcessor(app, handler);
		inReader = new InCommandReader(app, commandProcessor);
		new Thread(inReader).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chat_view_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			showActiveUsers();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		inReader.resume();

	}

	@Override
	protected void onPause() {
		super.onPause();
		inReader.pause();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		app.putOutCommand(new ExitCommandServer());
		int backButtonCount = 0;
		if (backButtonCount >= 1) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Toast.makeText(
					this,
					"Press the back button once again to close the application.",
					Toast.LENGTH_SHORT).show();
			backButtonCount++;
		}
	}

	@Override
	public void sendMessage(AbstractChatMessage chatMessage) {

		chatView.addMessage(chatMessage);
		app.putOutCommand(new BroadcastMessageServer(chatMessage.getMessage(),
				app.getChatContext()));
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	private void showActiveUsers() {

		Intent showActiveUsersIntent = new Intent(this,
				ListActiveUserActivity.class);
		startActivity(showActiveUsersIntent);
	}

}
