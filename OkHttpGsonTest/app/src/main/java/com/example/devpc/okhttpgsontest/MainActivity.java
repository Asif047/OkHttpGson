package com.example.devpc.okhttpgsontest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String path = "https://rokom.herokuapp.com/api/orders/2";
    OkHttpClient client;
    String response;
    private TextView tvOrderId,tvRecipient,tvOrderType,tvArea,tvDistrict;
    private ResponseApi responseApis;


    private Button btnOk,btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_item);


        tvOrderId=findViewById(R.id.textview_order_id);
        tvRecipient= (TextView) findViewById(R.id.textview_recipient);
        tvOrderType=findViewById(R.id.textview_order_type);
        tvArea=findViewById(R.id.textview_area);
        tvDistrict=findViewById(R.id.textview_district);


        btnOk=findViewById(R.id.button_ok);
        btnClear=findViewById(R.id.button_clear);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetDataFromServer().execute();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvOrderId.setText("");
                tvRecipient.setText("");
                tvOrderType.setText("");
                tvArea.setText("");
                tvDistrict.setText("");
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
                Type type = new TypeToken<ResponseApi>() {}.getType();

//                Collection<ResponseApi> enums = gson.fromJson(response, type);
//                responseApis = enums.toArray(new ResponseApi[enums.size()]);

                responseApis=gson.fromJson(response,type);



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
        }
    }
}
