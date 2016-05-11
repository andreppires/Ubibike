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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendsActivity extends Activity {

    ArrayList<String> arr_friends = new ArrayList<String>();
    GetFriends getfr=null;

    ImageButton addFriendButton;
    ListView myList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        myList= (ListView) findViewById(R.id.listView);


        /* Setting the ListView of Riders to add as friends */

        arr_friends = Client.getClient().getFriends();

        if (arr_friends.size()==0)
            getFriendsList();

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
        addFriend();
    }
    @Override
    public void onResume() {
        super .onResume();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_friends);

        myList.setAdapter(adapter);


    }

    /*Adicionar um novo amigo */
    public void addFriend() {
        addFriendButton = (ImageButton) findViewById(R.id.addFriendsButton);

        addFriendButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(FriendsActivity.this, AddAFriend.class);
                        startActivity(intent);
                    }
                });
        }

    public void getFriendsList() {

        getfr= new GetFriends("admin");
        getfr.execute();
    }

    /* Ligação ao server
     */

    class GetFriends extends AsyncTask<Void, Void, Boolean> {

        private String username=null;
        public GetFriends(String e){
            this.username=e;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/myfriends");
            client.AddParam("username", username);
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();

            System.out.println(response);

            if (response.contains(",")) {

                String[] aux= response.split(",");

                for (int i=0; i < aux.length ; i++ ) {
                    String[] st = aux[i].split("\"");
                    arr_friends.add(i, st[3]);

                }
                return true;
            } else if (response.contains("{")) {
                String[] st = response.split("\"");
                arr_friends.add(st[3]);
                return true;
            } else
                return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendsActivity.this, android.R.layout.simple_list_item_1, arr_friends);

                System.out.println(arr_friends);
                myList.setAdapter(adapter);
                getfr=null;

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            getfr = null;
        }
    }
    }

