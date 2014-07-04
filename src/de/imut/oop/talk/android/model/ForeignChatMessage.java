package de.imut.oop.talk.android.model;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
import android.graphics.Color;
import android.view.Gravity;
import de.immut.oop.talk.android.R;

public class ForeignChatMessage extends AbstractChatMessage {

	private static final long serialVersionUID = 1L;

	public ForeignChatMessage(String message, String userName) {
        super(message, userName);
    }

    @Override
    protected String getDisplayableText() {
        return String.format("%s: %s", userName, message);
    }

    @Override protected int getGravity() {
        return Gravity.LEFT;
    }

    @Override
    protected int getTextColor() {
        return Color.WHITE;
    }

    @Override
    protected int getBackgroundDrawable() {
        return R.drawable.talk_bubble_other;
    }
}
