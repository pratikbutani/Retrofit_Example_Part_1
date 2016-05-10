package com.androidbuts.jsonparsing.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidbuts.jsonparsing.R;
import com.androidbuts.jsonparsing.adapter.MyArrayAdapter;
import com.androidbuts.jsonparsing.model.Contact;
import com.androidbuts.jsonparsing.model.Contacts;
import com.androidbuts.jsonparsing.retrofit.api.ApiService;
import com.androidbuts.jsonparsing.retrofit.api.RetroClient;
import com.androidbuts.jsonparsing.utils.InternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Contact> contactList;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Array List for Binding Data from JSON to this List
         */
        contactList = new ArrayList<>();

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(findViewById(R.id.parentLayout), contactList.get(position).getName() + " => " + contactList.get(position).getPhone().getHome(), Snackbar.LENGTH_LONG).show();
            }
        });

        /**
         * Just to know onClick and Printing Hello Toast in Center.
         */
        Toast toast = Toast.makeText(getApplicationContext(), "Click on FloatingActionButton to Load JSON", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                /**
                 * Checking Internet Connection
                 */
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    final ProgressDialog dialog;
                    /**
                     * Progress Dialog for User Interaction
                     */
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle("Hey Wait Please...");
                    dialog.setMessage("I am getting your JSON");
                    dialog.show();

                    /***
                     * Processing with Retrofit
                     */
                    Retrofit retrofit = RetroClient.getDataFromWeb();

                    //Creating an object of our api interface
                    ApiService api = retrofit.create(ApiService.class);

                    Call<Contacts> call = api.getMyJSON();

                    call.enqueue(new Callback<Contacts>() {
                        @Override
                        public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                            contactList = response.body().getContacts();

                            /**
                             * Binding that List to Adapter
                             */
                            adapter = new MyArrayAdapter(MainActivity.this, contactList);
                            listView.setAdapter(adapter);
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Contacts> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });

                } else {
                    Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}