package com.deliman_poc.rokomari.deliman_rokomari_poc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deliman_poc.rokomari.deliman_rokomari_poc.details.DetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String path = "https://rokom.herokuapp.com/api/orders";
    OkHttpClient client;
    String response;
    private TextView tvOrderId,tvRecipient,tvOrderType,tvArea,tvDistrict;
    private Button btnOk,btnClear;
    private ResponseApi[] responseApis;


    private int flag_list=0;


    List<ResponseApi> data = new ArrayList<>();
    private ListView listView;

    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new starts
        listView= (ListView) findViewById(R.id.listview);


        //new ends

        tvOrderId= (TextView) findViewById(R.id.textview_order_id);
        tvRecipient= (TextView) findViewById(R.id.textview_recipient);
        tvOrderType= (TextView) findViewById(R.id.textview_order_type);
        tvArea= (TextView) findViewById(R.id.textview_area);
        tvDistrict= (TextView) findViewById(R.id.textview_district);

        btnOk=(Button) findViewById(R.id.button_ok);
        btnClear=(Button)findViewById(R.id.button_clear);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //new starts (network connection checking)

                if(!isConnected(MainActivity.this))buildDialog(MainActivity.this).show();
                else
                {

                }

                //new ends

                new GetDataFromServer().execute();
                listView.setVisibility(View.VISIBLE);
                flag_list=1;





            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listView.setVisibility(View.INVISIBLE);

            }
        });


    }

    private class GetDataFromServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                client = new OkHttpClient();

                response = ApiCall.GET(client, path);

                Gson gson = new Gson();
                Type type = new TypeToken<Collection<ResponseApi>>() {}.getType();
                Collection<ResponseApi> enums = gson.fromJson(response, type);
                responseApis = enums.toArray(new ResponseApi[enums.size()]);


                for(int i=0;i<enums.size();i++)
                data.add(responseApis[i]);







            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();


            //new starts
            listAdapter = new ListAdapter(MainActivity.this,data);
            listView.setAdapter(listAdapter);

            if(flag_list==1)
            {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(MainActivity.this,data.get(i).getOrderId().toString(),Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("order_id",data.get(i).getOrderId().toString());

                        startActivity(intent);
                    }
                });
            }


            //new ends

        }
    }






    //new starts
    public boolean isConnected(Context context)
    {
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=cm.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnectedOrConnecting())
        {
            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile!=null && mobile.isConnectedOrConnecting())||(wifi!=null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;

        }
        else
            return false;
    }




    public AlertDialog.Builder buildDialog(Context c)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have mobile data or wifi to access this.Press ok to exit");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder;
    }


    //new ends




}