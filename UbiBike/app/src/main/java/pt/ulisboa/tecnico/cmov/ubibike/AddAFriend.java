package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddAFriend extends AppCompatActivity {

    ArrayList<String> arr_users = new ArrayList<String>();

    Client client =  Client.getClient();

    String me = client.getUsername();

    ArrayList<String> arr_friends = client.getFriends();

    GetUsersList getUs;

    String chosenFriend;

    PostNewFriend postfr;

    ListView myList;

    ArrayList<String> arg;

    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_afriend);

        myList = (ListView) findViewById(R.id.allUsersListView);
        getUsersList();

    }


    public ArrayList<String> cleanList(ArrayList<String> listToClean) {
        ArrayList<String> aux = new ArrayList<String>(listToClean);
        aux.remove(me);
        /*for (int i = 0; i < aux.size(); i++) {
            if (arr_friends.contains(aux.get(i))) {
                String toRemove = aux.get(i);
                aux.remove(toRemove);
            }
        }*/
        for (String p: arr_friends){
            for(String i: aux){
                if(p.contains(i)){
                    aux.remove(i);
                }
            }

        }
        return aux;
    }

    public void createListView(final ArrayList<String> arg){


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arg);

        adapter.notifyDataSetChanged();

        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosenFriend = myList.getAdapter().getItem(position).toString();
                PostNewFriend(me, chosenFriend);
                arr_friends.add(chosenFriend);
                arg.remove(chosenFriend);
                ArrayAdapter adapter2 = new ArrayAdapter<String>(AddAFriend.this, android.R.layout.simple_list_item_1, arg);
                myList.setAdapter(adapter2);
            }
        });
    }


    public void getUsersList() {

        getUs= new GetUsersList(me);
        getUs.execute();
    }

    /* Ligação ao server
     */

    class GetUsersList extends AsyncTask<Void, Void, Boolean> {

        private String users=null;
        public GetUsersList(String e){
            this.users=e;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/getUsers");

            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            if (response.contains(",")) {
                String[] aux= response.split(",");
                for (int i=0; i < aux.length ; i++ ) {
                    String[] st = aux[i].split("\"");
                    arr_users.add(i, st[3]);
                }

                return true;
            } else if (response.contains("{")) {
                String[] st = response.split("\"");
                arr_users.add(st[3]);
                return true;
            } else
                return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                getUs=null;
                arg = cleanList(arr_users);
                createListView(arg);
            } else {
                System.out.println("fódeu!");
            }
        }

        @Override
        protected void onCancelled() {
            getUs = null;
        }
    }

    public void PostNewFriend(String me, String friend) {
        postfr= new PostNewFriend(me, friend);
        postfr.execute();
    }

    class PostNewFriend extends AsyncTask<Void, Void, Boolean> {
        private String me=null;
        String friend=null;

        public PostNewFriend(String e1, String e2){
            this.me=e1; this.friend=e2;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/addfriend");
            client.AddParam("username1", me);
            client.AddParam("username2", friend);
            try {
                client.Execute(RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            if(response.contains("OK")){
                return true;
            } else return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            postfr = null;

            if (success) {
                Toast toast = Toast.makeText(getApplicationContext(), friend + " é agora seu amigo", Toast.LENGTH_SHORT);
                toast.show();
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            postfr = null;
        }
    }
}
