package com.test.fixapp.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.fixapp.R;

import java.util.ArrayList;

/**
 * Created by MSI-GL62 on 26/4/2561.
 */

public class orderlistAdapter extends ArrayAdapter<order>{
    private Context mContext;
    private int mLayoutResId;
    private ArrayList<order> orderList;

    public orderlistAdapter(@NonNull Context context, int layoutResId, @NonNull ArrayList<order> orderList) {
        super(context, layoutResId, orderList);

        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.orderList = orderList;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutResId, null);

        order item = orderList.get(position);

        ImageView picStatus_order = itemLayout.findViewById(R.id.status_order);
        TextView orderTitle = itemLayout.findViewById(R.id.orderTitle);
        TextView orderDetail = itemLayout.findViewById(R.id.orderDetail);
        TextView orderDate = itemLayout.findViewById(R.id.textDate);

        orderTitle.setText(item.title);
        orderDetail.setText(item.detail);
        orderDate.setText(item.date);
        picStatus_order.setImageResource(item.picture);

        return itemLayout;
    }
}
