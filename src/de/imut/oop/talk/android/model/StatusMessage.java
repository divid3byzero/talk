package de.imut.oop.talk.android.model;

import android.graphics.Color;
import android.view.Gravity;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class StatusMessage extends AbstractChatMessage {

	private static final long serialVersionUID = 5597853461256361465L;

	public StatusMessage(String message) {
        super(message, null);
    }

    @Override protected String getDisplayableText() {
        return message;
    }

    @Override protected int getGravity() {
        return Gravity.LEFT;
    }

    @Override protected int getTextColor() {
        return Color.BLACK;
    }

    @Override protected int getBackgroundDrawable() {
        return 0;
    }
}
