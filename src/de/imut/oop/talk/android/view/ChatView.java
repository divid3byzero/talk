package de.imut.oop.talk.android.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.immut.oop.talk.android.R;
import de.imut.oop.talk.android.action.ChatAction;
import de.imut.oop.talk.android.model.AbstractChatMessage;
import de.imut.oop.talk.android.model.ChatMessageAdapter;
import de.imut.oop.talk.android.model.Talk;
import de.imut.oop.talk.android.model.UserChatMessage;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class ChatView extends LinearLayout {

    private EditText textInput;
    private ChatAction chatAction;
    private ChatMessageAdapter messageAdapter;
    private final Talk app;

    public ChatView(Context context, AttributeSet attrs) {

        super(context, attrs);
        Activity chatActivity = (Activity) context;
        messageAdapter = new ChatMessageAdapter(chatActivity, R.layout.chat_row);
        app = (Talk) chatActivity.getApplication();
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        textInput = (EditText) findViewById(R.id.text_input);

        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!"".equals(textInput.getText().toString())) {
                    UserChatMessage chatMessage =
                        new UserChatMessage(textInput.getText().toString(), app.getChatContext().getNickName());
                    chatAction.sendMessage(chatMessage);
                }
            }
        });

        ListView chatHistory = (ListView) findViewById(R.id.chat_history);
        chatHistory.setAdapter(messageAdapter);
        chatHistory.setDivider(null);
        chatHistory.setDividerHeight(15);
        chatHistory.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    public void updateChatHistory() {

        messageAdapter.notifyDataSetChanged();
    }

    public void setActionListener(ChatAction actionListener) {

        chatAction = actionListener;
    }

    public void addMessage(AbstractChatMessage chatMessage) {

        messageAdapter.add(chatMessage);
        textInput.setText("");
    }
}
