package de.imut.oop.talk.android.model;

import java.util.ArrayList;
import java.util.List;

import de.immut.oop.talk.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public abstract class AbstractChatMessageAdapter extends ArrayAdapter<AbstractChatMessage> {

    private Context context;
    private List<AbstractChatMessage> chatMessages = new ArrayList<AbstractChatMessage>();

    public AbstractChatMessageAdapter(Context context, int resource) {

        super(context, resource);
        this.context = context;
    }

    protected abstract void setMessageToRow(AbstractChatMessage chatMessage, RowHolder rowHolder);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RowHolder rowHolder = null;
        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.chat_row, null, false);
            rowHolder = new RowHolder();
            rowHolder.messageView = (TextView) row.findViewById(R.id.user_message_display);
            row.setTag(rowHolder);
        } else {
            rowHolder = (RowHolder) row.getTag();
        }

        AbstractChatMessage chatMessage = getItem(position);
        setMessageToRow(chatMessage, rowHolder);

        return row;
    }

    @Override
    public void add(AbstractChatMessage chatMessage) {

        chatMessages.add(chatMessage);
        super.add(chatMessage);
        super.notifyDataSetChanged();
    }

    protected class RowHolder {
        TextView messageView;
    }



}
