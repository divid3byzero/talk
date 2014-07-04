package de.imut.oop.talk.android.action;

import de.imut.oop.talk4.command.RegistrableContext;
import android.app.Activity;

public interface ActiveUserAction {
	
	Activity getActivity();

	void startPrivateChat(RegistrableContext registrableContext);
	
}
