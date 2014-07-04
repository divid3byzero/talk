package de.imut.oop.talk.android.model;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
import android.content.Context;
import android.widget.LinearLayout;

public class ChatMessageAdapter extends AbstractChatMessageAdapter {

    public ChatMessageAdapter(Context context, int resId) {

        super(context, resId);
    }


    @Override
    protected void setMessageToRow(AbstractChatMessage chatMessage, RowHolder rowHolder) {

        LinearLayout.LayoutParams layoutParams =
            (android.widget.LinearLayout.LayoutParams) rowHolder.messageView.getLayoutParams();
        layoutParams.gravity = chatMessage.getGravity();
        rowHolder.messageView.setText(chatMessage.getDisplayableText());
        rowHolder.messageView.setTextColor(chatMessage.getTextColor());
        rowHolder.messageView.setBackgroundResource(chatMessage.getBackgroundDrawable());
        rowHolder.messageView.setLayoutParams(layoutParams);
    }
}
