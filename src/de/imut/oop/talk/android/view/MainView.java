package de.imut.oop.talk.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class MainView extends LinearLayout {

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
    protected void onFinishInflate() {
		super.onFinishInflate();
    }
}
