package net.sqindia.movhaulagent;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 25-05-2017.
 */

public class Dashboard extends AppCompatActivity{

    LinearLayout lt_back;
    ImageButton ib_driver_add,ib_comp_add;

    List<String> ar_comp_lists = new ArrayList<String>();

    TextView tv_header;
    ImageView img_back;
    private ViewPager viewPager;
    private int[] layouts;
    ArrayList<String> state_lists = new ArrayList<>();
    ArrayList<String> district_lists = new ArrayList<>();
    ListAdapter adapter1, adapter2, adapter3;
    AlertDialog b;
    int sts;
    ListView lview_state,lview_district;
    FrameLayout fl_header,fl_bottom;
    boolean bl_bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        FontsManager.initFormAssets(Dashboard.this, "fonts/lato.ttf");
        FontsManager.changeFonts(Dashboard.this);


        lt_back = (LinearLayout) findViewById(R.id.action_back);
        ib_driver_add = (ImageButton) findViewById(R.id.add_driver);
        ib_comp_add = (ImageButton) findViewById(R.id.add_company);
        fl_bottom = (FrameLayout) findViewById(R.id.bottom_layout);

        tv_header = (TextView) findViewById(R.id.textview_header);

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ib_driver_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                popupwithlistview();

            }
        });

        ib_comp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new CompanyFragment());
                ft.addToBackStack("driver");
                ft.commit();
                fl_bottom.setVisibility(View.GONE);
                tv_header.setText("Company");

            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bl_bottom = fl_bottom.getVisibility()== View.VISIBLE ;
        outState.putBoolean("bl_layout", bl_bottom);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        bl_bottom = savedInstanceState.getBoolean("bl_layout");
        fl_bottom.setVisibility(bl_bottom ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.e("tag","frag_count : "+getSupportFragmentManager().getBackStackEntryCount());
        Log.e("tag","frag_countee : "+getSupportFragmentManager().findFragmentByTag("driver"));
        Log.e("tag","frags : "+getSupportFragmentManager().getFragments());
    }

    public void popupwithlistview(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Dashboard.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register_state, null);
        dialogBuilder.setView(dialogView);
        b = dialogBuilder.create();



        state_lists.clear();
        district_lists.clear();

        state_lists.add("Private");
        state_lists.add("Corporate");


        district_lists.add("IBM");
        district_lists.add("MovHaul");
        district_lists.add("Opiniion");
        district_lists.add("Sqindia");
        district_lists.add("IFindCard");
        district_lists.add("Zoho");

        sts =0;

        fl_header = (FrameLayout) dialogView.findViewById(R.id.framelayout);
        fl_header.setVisibility(View.GONE);
        lview_state = (ListView) dialogView.findViewById(R.id.lview);
        lview_district = (ListView) dialogView.findViewById(R.id.lview1);
        Log.e("tag","stsiz: "+state_lists.size());
        adapter1 = new ListAdapter(getApplicationContext(), R.layout.dialog_region_txts0, state_lists);
        lview_state.setAdapter(adapter1);
        adapter2 = new ListAdapter(getApplicationContext(), R.layout.dialog_region_txts, district_lists);
        lview_district.setAdapter(adapter2);




        b.show();




    }


    public class ListAdapter extends ArrayAdapter<String> {

        Context cc;
        ArrayList<String> data_lists;
        int resourceid;
        TextView tvview;

        public ListAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.cc = context;
            this.data_lists = objects;
            this.resourceid = textViewResourceId;
        }

        @Override
        public View getDropDownView(int posi, View convertView, ViewGroup parent) {
            return getCustomView(posi, convertView, parent);
        }

        @Override
        public View getView(int posi, View convertView, ViewGroup parent) {
            return getCustomView(posi, convertView, parent);
        }


        public View getCustomView(final int posi, View row, ViewGroup parent) {

            Typeface tf = Typeface.createFromAsset(cc.getAssets(), "fonts/lato.ttf");

            LayoutInflater inflater = (LayoutInflater) cc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View arow = inflater.inflate(resourceid, parent, false);

            TextView label = (TextView) arow.findViewById(R.id.textview_header);

            final View vie_line = arow.findViewById(R.id.viewcolor);


            if(posi == data_lists.size()-1){
                vie_line.setVisibility(View.GONE);
            }

            label.setTypeface(tf);

            label.setText(data_lists.get(posi));

            arow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sts ==0) {

                        if(posi == 0){
                            Log.e("tag", "clicked" + posi);
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_container, new DriverFragment());
                            ft.commit();
                            fl_bottom.setVisibility(View.GONE);
                            tv_header.setText("Driver");
                            b.dismiss();

                        }
                        else{
                            lview_state.setVisibility(View.GONE);
                            lview_district.setVisibility(View.VISIBLE);
                            sts = 1;
                        }
                    }
                    else{
                        fl_header.setVisibility(View.VISIBLE);

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, new DriverFragment());
                        ft.commit();
                        b.dismiss();
                        tv_header.setText("Corporate");
                        fl_bottom.setVisibility(View.GONE);
                        sts = 0;
                    }

                }


            });


            return arow;
        }
    }
}
