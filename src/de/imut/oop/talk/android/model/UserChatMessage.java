package de.imut.oop.talk.android.model;

import android.graphics.Color;
import android.view.Gravity;
import de.immut.oop.talk.android.R;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class UserChatMessage extends AbstractChatMessage {

	private static final long serialVersionUID = 6223434076639318182L;

	public UserChatMessage(String message, String userName) {

        super(message, userName);
    }

    @Override protected String getDisplayableText() {

        return message;
    }

    @Override protected int getGravity() {

        return Gravity.RIGHT;
    }

    @Override protected int getTextColor() {

        return Color.WHITE;
    }

    @Override protected int getBackgroundDrawable() {

        return R.drawable.talk_bubble_self;
    }
}
