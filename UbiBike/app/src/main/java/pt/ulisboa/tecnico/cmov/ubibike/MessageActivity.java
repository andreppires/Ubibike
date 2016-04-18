package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MessageActivity extends AppCompatActivity {

    private EditText me;
    private ListView myList;
    private Button b;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> arr_messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initControls();
    }

    private void initControls() {

        me = (EditText) findViewById(R.id.messageEdit);
        myList= (ListView) findViewById(R.id.messagesContainer);
        b = (Button)findViewById(R.id.chatSendButton);

        TextView mySelf = (TextView) findViewById(R.id.meLbl);
        TextView friend = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        friend.setText("My Buddy");// Hard Coded
        loadDummyHistory();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = me.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                }
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                me.setText("");

                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        myList.setSelection(myList.getCount() - 1);
    }



    private void loadDummyHistory() {
        arr_messages = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        arr_messages.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        arr_messages.add(msg1);

        adapter = new ChatAdapter(MessageActivity.this, new ArrayList<ChatMessage>());
        myList.setAdapter(adapter);

        for(int i=0; i<arr_messages.size(); i++) {
            ChatMessage message = arr_messages.get(i);
            displayMessage(message);
        }

    }

}
