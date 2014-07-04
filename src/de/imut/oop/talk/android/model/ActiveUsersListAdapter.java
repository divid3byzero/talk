package de.imut.oop.talk.android.model;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk4.command.RegistrableContext;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class ActiveUsersListAdapter extends ArrayAdapter<RegistrableContext> {

	private Context context;

	public ActiveUsersListAdapter(Context context, int resource, List<RegistrableContext> contextList) {
		super(context, resource, contextList);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		RowHolder rowHolder = null;
		if (null == row) {
			row = LayoutInflater.from(context).inflate(R.layout.active_user_row, parent, false);
			rowHolder = new RowHolder();
			rowHolder.textView = (TextView) row.findViewById(R.id.active_user);
			row.setTag(rowHolder);
		} else {
			rowHolder = (RowHolder) row.getTag();
		}
		
		RegistrableContext item = getItem(position);
		rowHolder.textView.setTextColor(Color.parseColor("#a9412f"));
		rowHolder.textView.setTextSize(15.0f);
		rowHolder.textView.setText(item.getNickName());
		return row;
	}
	
	
	private class RowHolder {
		TextView textView;
	}
	
	
}
