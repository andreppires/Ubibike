package pt.ulisboa.tecnico.cmov.ubibike;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by mariana on 12/05/16.
 */
public class AlertDFragment extends DialogFragment {




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Message
                .setMessage("Delete Account?")

                // Positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteAyncAccount deletaAi = new DeleteAyncAccount(Client.getClient().getUsername());
                        deletaAi.execute();
                        login();
                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }

    class DeleteAyncAccount extends AsyncTask<Void, Void, Boolean> {

        String mEmail;

        public DeleteAyncAccount(String p){
            this.mEmail=p;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/userdelete");
            client.AddParam("username", mEmail);
            try {
                client.Execute(RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            client.getResponse();
            return null;
        }
    }

    private void login() {

        Toast toast = Toast.makeText(getContext(), "Account deleted ", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
