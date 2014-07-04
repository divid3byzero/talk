package de.imut.oop.talk.android.model;

import java.io.Serializable;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public abstract class AbstractChatMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String userName;
    protected String message;

    public AbstractChatMessage(String message, String userName) {

        this.userName = userName;
        this.message = message;
    }

    public String getMessage() {

        return message;
    }

    public String getUserName() {

        return userName;
    }

    protected abstract String getDisplayableText();

    protected abstract int getGravity();

    protected abstract int getTextColor();

    protected abstract int getBackgroundDrawable();

}
