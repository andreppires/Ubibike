package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AddAFriend extends AppCompatActivity {

    ArrayList<String> arr_friends = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_afriend);
        createListView();
    }

    public void createListView(){

        ListView myList= (ListView) findViewById(R.id.listView);

        arr_friends.add("Bernardo Esteves");
        arr_friends.add("Mariana Vargas");
        arr_friends.add("André Pires");
        arr_friends.add("Ana Malhoa");
        arr_friends.add("Tatiana Neves");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_friends);

        myList.setAdapter(adapter);

    }




}
