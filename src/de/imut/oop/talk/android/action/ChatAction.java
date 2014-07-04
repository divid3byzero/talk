package de.imut.oop.talk.android.action;

import android.app.Activity;
import de.imut.oop.talk.android.model.AbstractChatMessage;

public interface ChatAction {
	
	void sendMessage(AbstractChatMessage chatMessage);
	
	Activity getActivity();
}
