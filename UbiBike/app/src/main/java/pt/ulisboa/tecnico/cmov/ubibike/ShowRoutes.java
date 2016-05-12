package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowRoutes extends AppCompatActivity {
    GetRoutes connectServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_routes);


        getRoutesList(Client.getClient().getUsername());
    }

    private void getRoutesList(String user) {
        //chama asynctask com
        connectServer = new GetRoutes(Client.getClient().getUsername());
        connectServer.execute();
    }



    /* Ligação ao server
     */

    class GetRoutes extends AsyncTask<Void, Void, Boolean> {

        private String username=null;

        private String response;
        public GetRoutes(String e){
            this.username=e;
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/getRoutesFromUser");
            client.AddParam("username", username);
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("A STATION TEM AS SEGUINTES BIKES");
            System.out.println(client.getResponse());
            response = client.getResponse();

            if (response.contains("\"")) {
                return true;
            }else return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                parseAndSet(response);

            }
            }
        }

    private void parseAndSet(String response) {

        ArrayList<String> listRoutes = new ArrayList<String>();
        String[] aux= response.split(",");
        String coisinhoNovo;

        for (int i=0; i < aux.length ; i++ ) {
            String[] st = aux[i].split(":");
            coisinhoNovo=st[1].replace("}","");
            if(coisinhoNovo.contains("]"))
                listRoutes.add(st[1].replace("}]",""));
            else listRoutes.add(coisinhoNovo);

        }

        final ListView listBikesStation1= (ListView) findViewById(R.id.listOfRoutes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowRoutes.this, android.R.layout.simple_list_item_1, listRoutes);
        listBikesStation1.setAdapter(adapter);

        listBikesStation1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String bikeIP = ((TextView)view).getText().toString();
                System.out.println("o escolhido:"+ bikeIP);
                Intent intent = new Intent(ShowRoutes.this, ShowThatRoute.class);
                intent.putExtra("KEY_EXTRA", bikeIP);
                startActivity(intent);
            }
        });
    }
}
