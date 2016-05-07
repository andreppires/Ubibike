package pt.ulisboa.tecnico.cmov.ubibike;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendsActivity extends Activity {

    ArrayList<String> arr_friends = new ArrayList<String>();
    //GetFriends getfr=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        final ListView myList= (ListView) findViewById(R.id.listView);

        /* Setting the ListView of Riders to add as friends */

        arr_friends = Client.getClient().getFriends();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_friends);

        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String chosenFriend = myList.getAdapter().getItem(position).toString();
                Intent intent = new Intent(view.getContext(), FriendProfile.class);

                intent.putExtra("STRING_I_NEED", chosenFriend);
                startActivity(intent);
            }
        } );

    }

    /*Adicionar um novo amigo */
    public void addFriend(View view) {
        Intent intent = new Intent(this, AddAFriend.class);
        startActivity(intent);
        }


    }

