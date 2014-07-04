package de.imut.oop.talk.android.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.action.UserNameAction;
import de.imut.oop.talk.android.model.AndroidCommon;
import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk.android.view.UserNameView;
import de.imut.oop.talk.android.worker.ServerConnector;
import de.imut.oop.talk4.command.Context;


/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class UserNameActivity extends Activity implements UserNameAction {

	private UserNameView userNameView;
	private Talk app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		app = (Talk) getApplication();
		userNameView = (UserNameView) View.inflate(this,
				R.layout.register_view, null);
		userNameView.setActionListener(this);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(userNameView);
	}

	@Override
	public void registerUser(TextView view) {

		String userName = view.getText().toString();
		Context androidContext = new Context(app.getChatContext().getID());
		androidContext.setNickName(userName);
		app.setUserName(userName);
		persistNickName(androidContext);
		startChat();
	}

	private void startChat() {

		new ServerConnector(this).execute();
	}

	private void persistNickName(Context androidContext) {

		SharedPreferences sharedPreferences = getSharedPreferences(
				AndroidCommon.CONTEXT_FILE, MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString(AndroidCommon.NICK_NAME, androidContext.getNickName());
		edit.commit();
	}
}
