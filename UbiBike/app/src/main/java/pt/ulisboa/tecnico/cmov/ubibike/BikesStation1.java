package pt.ulisboa.tecnico.cmov.ubibike;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BikesStation1 extends AppCompatActivity {

    GetBikes vaiLaBuscar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes_station1);

        getBikesList();
    }

    public void getBikesList() {
        vaiLaBuscar= new GetBikes("Store1");
        vaiLaBuscar.execute();
        System.out.println("executei!");
    }

    /* Ligação ao server
     */

    class GetBikes extends AsyncTask<Void, Void, Boolean> {

        private String stationid=null;
        public GetBikes(String e){
            this.stationid=e;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            System.out.println("esotu aquiiii!");
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/bike");
            client.AddParam("stationid", stationid);
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String response = client.getResponse();
            System.out.println(response);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                vaiLaBuscar=null;
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            vaiLaBuscar = null;
        }
    }


}
