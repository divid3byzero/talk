package de.imut.oop.talk.android.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.action.ActiveUserAction;
import de.imut.oop.talk.android.model.ActiveUsersListAdapter;
import de.imut.oop.talk4.command.RegistrableContext;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class ListActiveUsersView extends LinearLayout {

	private Context context;
	private ListView activeUsers;
	private ActiveUserAction actionListener;

	public ListActiveUsersView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		activeUsers = (ListView) findViewById(R.id.active_user_list);
		activeUsers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				actionListener.startPrivateChat((RegistrableContext) parent.getItemAtPosition(position));
			}
		});
	}
	
	public void setActionListener(ActiveUserAction activeUserAction) {
		actionListener = activeUserAction;
	}

	public void addUsers(List<RegistrableContext> userContexts) {
		
		List<RegistrableContext> contextsForAdapter = new ArrayList<RegistrableContext>();
		contextsForAdapter.addAll(userContexts);
		ActiveUsersListAdapter activeUsersAdapter = new ActiveUsersListAdapter(
				context, R.layout.active_user_row, contextsForAdapter);
		activeUsers.setAdapter(activeUsersAdapter);
	}

}
