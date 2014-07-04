package de.imut.oop.talk.android.worker;

import java.io.IOException;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import de.imut.oop.talk.android.controller.ChatActivity;
import de.imut.oop.talk.android.model.Talk;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class ServerConnector extends AsyncTask<Void, Void, Void> {

	private Activity activity;
    public ServerConnector(Activity activity) {
		this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Socket socket = null;
        try {
            socket =
                new Socket("10.20.12.154", 2048); // 10.20.12.154 192.168.178.47 Please enter apropriate IP address here
        } catch (IOException e) {
            // TODO: Show server offline activity
        }

        if (socket != null) {
            Talk talkApp = (Talk) activity.getApplication();
            talkApp.initCommunication(socket);
            activity.startActivity(new Intent(activity, ChatActivity.class));
            activity.finish();
        }
        return null;
    }
}
