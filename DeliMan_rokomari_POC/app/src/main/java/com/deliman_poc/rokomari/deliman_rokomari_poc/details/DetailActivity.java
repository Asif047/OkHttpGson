package com.deliman_poc.rokomari.deliman_rokomari_poc.details;

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

import com.deliman_poc.rokomari.deliman_rokomari_poc.ApiCall;
import com.deliman_poc.rokomari.deliman_rokomari_poc.ListAdapter;
import com.deliman_poc.rokomari.deliman_rokomari_poc.MainActivity;
import com.deliman_poc.rokomari.deliman_rokomari_poc.R;
import com.deliman_poc.rokomari.deliman_rokomari_poc.ResponseApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;

public class DetailActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String path = "https://rokom.herokuapp.com/api/orders";
    OkHttpClient client;
    String response;
    private TextView tvOrderId,tvRecipient,tvOrderType,tvArea,tvDistrict;

    private ResponseApi responseApis;


    private int orderId;



    List<ResponseApi> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        orderId=Integer.parseInt(intent.getStringExtra("order_id"));
        //Toast.makeText(this,orderId+"",Toast.LENGTH_LONG).show();



        tvOrderId= (TextView) findViewById(R.id.textview_order_id);
        tvRecipient= (TextView) findViewById(R.id.textview_recipient);
        tvOrderType= (TextView) findViewById(R.id.textview_order_type);
        tvArea= (TextView) findViewById(R.id.textview_area);
        tvDistrict= (TextView) findViewById(R.id.textview_district);


        new PostDataTOServer().execute();





    }

    private class PostDataTOServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(DetailActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                client = new OkHttpClient();

                response = ApiCall.GET(client, path+"/"+orderId);

                Gson gson = new Gson();
//                Type type = new TypeToken<Collection<ResponseApi>>() {}.getType();
//                Collection<ResponseApi> enums = gson.fromJson(response, type);
                //responseApis = enums.toArray(new ResponseApi[enums.size()]);


                responseApis=gson.fromJson(response,ResponseApi.class);





//                for(int i=0;i<enums.size();i++)
//                    data.add(responseApis[i]);
//
//





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


            tvOrderId.setText(responseApis.getOrderId().toString());
            tvRecipient.setText(responseApis.getRecipient().toString());
            tvOrderType.setText(responseApis.getOrderType().toString());
            tvArea.setText(responseApis.getArea().toString());
            tvDistrict.setText(responseApis.getDistrict().toString());





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
