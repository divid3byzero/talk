package de.imut.oop.talk.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.action.UserNameAction;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class UserNameView extends LinearLayout {

    private UserNameAction actionListener;

    public UserNameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setActionListener(UserNameAction actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        final TextView userNameBox = (TextView) findViewById(R.id.user_name_box);
        Button userNameSendButton = (Button) findViewById(R.id.send_user_name);

        userNameSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.registerUser(userNameBox);
            }
        });
    }
}
