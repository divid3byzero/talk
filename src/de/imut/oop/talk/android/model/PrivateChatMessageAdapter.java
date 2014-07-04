package de.imut.oop.talk.android.model;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
import android.content.Context;
import android.widget.LinearLayout;

public class PrivateChatMessageAdapter extends AbstractChatMessageAdapter {

    public PrivateChatMessageAdapter(Context context, int resource) {

        super(context, resource);
    }

    @Override
    protected void setMessageToRow(AbstractChatMessage chatMessage, RowHolder rowHolder) {

        LinearLayout.LayoutParams layoutParams =
            (android.widget.LinearLayout.LayoutParams) rowHolder.messageView.getLayoutParams();

        layoutParams.gravity = chatMessage.getGravity();
        rowHolder.messageView.setBackgroundResource(chatMessage.getBackgroundDrawable());
        rowHolder.messageView.setTextColor(chatMessage.getTextColor());
        rowHolder.messageView.setLayoutParams(layoutParams);

    }

}
