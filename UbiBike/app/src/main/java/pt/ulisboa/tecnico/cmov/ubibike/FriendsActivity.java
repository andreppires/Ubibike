package pt.ulisboa.tecnico.cmov.ubibike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        /* Setting the ListView of Riders to add as friends */

        String[] myStringArray={"Mariana","André"};

        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myStringArray);

        ListView myList=
                (ListView) findViewById(R.id.listView);
        myList.setAdapter(myAdapter);

        }
    }

