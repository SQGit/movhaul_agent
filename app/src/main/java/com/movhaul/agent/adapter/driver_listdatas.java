package com.movhaul.agent.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

import com.movhaul.agent.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Salman on 09-06-2017.
 */

public class driver_listdatas extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    Typeface tf;
    ArrayList<String> drv_ids;
    Activity act;
    LinearLayout lt_driver_details;

    HashMap<String, HashMap<String, String>> hash_datas;
    HashMap<String, String> hs_drivers_datas;
    int i=0;


    public driver_listdatas(Activity activity, ArrayList array_list, HashMap<String, HashMap<String,String>> datas) {

        this.context = activity.getApplicationContext();
        this.drv_ids = array_list;
        this.hash_datas = datas;
        inflater = LayoutInflater.from(this.context);
        act = activity;
        hs_drivers_datas = new HashMap<>();

    }

    @Override
    public int getCount() {
        return drv_ids.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final MyViewHolder mViewHolder;
        final TextView tv_drivers, tv_jobs, tv_add;
        final TextView tv_driver_id,tv_driver_name,tv_driver_mobile,tv_driver_address,tv_driver_email,tv_driver_type,tv_driver_route,tv_driver_comp;

        final LinearLayout lt_details;



        hs_drivers_datas = hash_datas.get(drv_ids.get(position));

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.driver_list_adapter, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);

            FontsManager.initFormAssets(act, "fonts/lato.ttf");       //initialization
            FontsManager.changeFonts(convertView);

            tv_drivers = (TextView) convertView.findViewById(R.id.textview_drivers);
            tv_jobs = (TextView) convertView.findViewById(R.id.textview_jobs);
            lt_details = (LinearLayout) convertView.findViewById(R.id.layout_details);

            tv_driver_id = (TextView) convertView.findViewById(R.id.driver_id);
            tv_driver_name = (TextView) convertView.findViewById(R.id.driver_name);
            tv_driver_mobile = (TextView) convertView.findViewById(R.id.driver_mobile);
            tv_driver_address = (TextView) convertView.findViewById(R.id.driver_address);
            tv_driver_email = (TextView) convertView.findViewById(R.id.driver_email);
            tv_driver_type = (TextView) convertView.findViewById(R.id.driver_type);
            tv_driver_route = (TextView) convertView.findViewById(R.id.driver_route);
            tv_driver_comp = (TextView) convertView.findViewById(R.id.driver_company);

           // lt_driver_details.setVisibility(View.GONE);


            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();

            tv_drivers = mViewHolder.tv_drivers;
            tv_jobs = mViewHolder.tv_jobs;
            lt_details = mViewHolder.lt_details;

            tv_driver_id = mViewHolder.tv_driver_id;
            tv_driver_name = mViewHolder.tv_driver_name;
            tv_driver_mobile = mViewHolder.tv_driver_mobile;
            tv_driver_address = mViewHolder.tv_driver_address;
            tv_driver_email = mViewHolder.tv_driver_email;
            tv_driver_type = mViewHolder.tv_driver_type;
            tv_driver_route = mViewHolder.tv_driver_route;
            tv_driver_comp = mViewHolder.tv_driver_comp;


        }

        tv_drivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag","clicked"+position);
                if(i ==0) {
                    lt_details.setVisibility(View.VISIBLE);
                    i=1;
                }
                else{
                    lt_details.setVisibility(View.GONE);
                    i=0;
                }
            }
        });


        tv_drivers.setText(hs_drivers_datas.get("driver_name"));
        tv_jobs.setText(hs_drivers_datas.get("driver_finished_jobs"));

        tv_driver_id.setText(hs_drivers_datas.get("driver_id"));
        tv_driver_name.setText(hs_drivers_datas.get("driver_name"));
        tv_driver_mobile.setText(hs_drivers_datas.get("driver_mobile"));
        tv_driver_address.setText(hs_drivers_datas.get("driver_address"));
        tv_driver_email.setText(hs_drivers_datas.get("driver_email"));
        tv_driver_type.setText(hs_drivers_datas.get("driver_type"));
        tv_driver_route.setText(hs_drivers_datas.get("driver_route"));
        tv_driver_comp.setText(hs_drivers_datas.get("driver_company"));



        return convertView;
    }


    private class MyViewHolder {
        TextView tv_drivers, tv_jobs;
        TextView tv_driver_id,tv_driver_name,tv_driver_mobile,tv_driver_address,tv_driver_email,tv_driver_type,tv_driver_route,tv_driver_comp;
        LinearLayout lt_details;

        public MyViewHolder(View item) {
            tv_drivers = (TextView) item.findViewById(R.id.textview_drivers);
            tv_jobs = (TextView) item.findViewById(R.id.textview_jobs);
            lt_details = (LinearLayout) item.findViewById(R.id.layout_details);

            tv_driver_id = (TextView) item.findViewById(R.id.driver_id);
            tv_driver_name = (TextView) item.findViewById(R.id.driver_name);
            tv_driver_mobile = (TextView) item.findViewById(R.id.driver_mobile);
            tv_driver_address = (TextView) item.findViewById(R.id.driver_address);
            tv_driver_email = (TextView) item.findViewById(R.id.driver_email);
            tv_driver_type = (TextView) item.findViewById(R.id.driver_type);
            tv_driver_route = (TextView) item.findViewById(R.id.driver_route);
            tv_driver_comp = (TextView) item.findViewById(R.id.driver_company);

        }
    }
}
