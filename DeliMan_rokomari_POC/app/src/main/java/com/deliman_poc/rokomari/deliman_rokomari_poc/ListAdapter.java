package com.deliman_poc.rokomari.deliman_rokomari_poc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DEVPC on 05/11/2017.
 */

public class ListAdapter extends ArrayAdapter<ResponseApi> {
    private Context context;
    private List<ResponseApi> list;



    TextView tvOrderId,tvRecipient,tvOrderType,tvArea,tvDistrict;


    public ListAdapter(Context context, List<ResponseApi> list) {
        super(context,R.layout.row_item,list);

        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_item,parent,false);




        tvOrderId=(TextView) convertView.findViewById(R.id.textview_order_id);
        tvRecipient=(TextView) convertView.findViewById(R.id.textview_recipient);
        tvOrderType=(TextView) convertView.findViewById(R.id.textview_order_type);
        tvArea=(TextView) convertView.findViewById(R.id.textview_area);
        tvDistrict=(TextView) convertView.findViewById(R.id.textview_district);


        tvOrderId.setText(list.get(position).getOrderId().toString());
        tvRecipient.setText(list.get(position).getRecipient().toString());
        tvOrderType.setText(list.get(position).getOrderType().toString());
        tvArea.setText(list.get(position).getArea().toString());
        tvDistrict.setText(list.get(position).getDistrict().toString());


        return convertView;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public ResponseApi getItem(int position) {
        return list.get(position);
    }





}
