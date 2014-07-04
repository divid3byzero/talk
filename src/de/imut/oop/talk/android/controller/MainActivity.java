package de.imut.oop.talk.android.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.model.AndroidCommon;
import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk.android.view.MainView;
import de.imut.oop.talk.android.worker.ServerConnector;

/**
 * 
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 * @version 2.0, Changed order of registration process
 *
 */
public class MainActivity extends Activity {

	private MainView mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainView = (MainView) View.inflate(this, R.layout.main_view, null);
		setContentView(mainView);

		SharedPreferences sharedPreferences = getSharedPreferences(
				AndroidCommon.CONTEXT_FILE, 0);
		String userName = "";
		if (null != (userName = sharedPreferences.getString(
				AndroidCommon.NICK_NAME, "")) && !"".equals(userName)) {

			Talk app = (Talk) getApplication();
			app.setUserName(userName);
			new ServerConnector(this).execute();

		} else {
			startActivity(new Intent(this, UserNameActivity.class));
		}
		finish();
	}
}
