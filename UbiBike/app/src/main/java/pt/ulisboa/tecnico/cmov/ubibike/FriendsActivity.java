package pt.ulisboa.tecnico.cmov.ubibike;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import java.util.ArrayList;

public class FriendsActivity extends Activity {

    ArrayList<String> arr_friends = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ListView myList= (ListView) findViewById(R.id.listView);

        /* Setting the ListView of Riders to add as friends */
        arr_friends.add("Bernardo");
        arr_friends.add("Mariana");
        arr_friends.add("Andre");
        arr_friends.add("Anti");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_friends);

        myList.setAdapter(adapter);

        myList.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), FriendProfile.class);
                startActivity(intent);
                return true;
            }
        } );
    }
        //myList.setLongClickable(true)

    /* Ao pressionar prolongadamente sobre o nome do amigo direcciona p o perfil do amigo */



    /*Adicionar um novo amigo */
    public void addFriend(View view) {
        Intent intent = new Intent(this, AddAFriend.class);
        startActivity(intent);
        }
    }

