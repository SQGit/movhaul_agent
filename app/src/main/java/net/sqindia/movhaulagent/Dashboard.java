package net.sqindia.movhaulagent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ib_driver_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

   /*             FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new RegisterFragment());
                ft.commit();*/
   //popup();

                //popupwithpager();


/*
                Dialog_Region dialog_region = new Dialog_Region(Dashboard.this);
                dialog_region.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                dialog_region.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_region.show();
                //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                dialog_region.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
*/

                popupwithlistview();

            }
        });

        ib_comp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new CompanyFragment());
                ft.commit();
                fl_bottom.setVisibility(View.GONE);

            }
        });
    }

    public void popup(){



        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Dashboard.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_choose_driver, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner);

        ar_comp_lists.add("Choose Company");
        ar_comp_lists.add("IBM");
        ar_comp_lists.add("MovHaul");
        ar_comp_lists.add("IfindCard");
        ar_comp_lists.add("Opiniion");
        ar_comp_lists.add("Sqindia");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,R.id.text1, ar_comp_lists);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        b.show();

//

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

  /*  public void popupwithpager(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Dashboard.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_region, null);
        dialogBuilder.setView(dialogView);
         b = dialogBuilder.create();
        tv_header = (TextView) dialogView.findViewById(R.id.textview_header);
        viewPager = (ViewPager) dialogView.findViewById(R.id.view_pager);
        img_back = (ImageView) dialogView.findViewById(R.id.img_back);


        layouts = new int[]{
                R.layout.register_state,
                R.layout.register_state };

        state_lists.add("Private Driver");
        state_lists.add("Company Driver");


        district_lists.add("IBM");
        district_lists.add("MovHaul");
        district_lists.add("Opiniion");
        district_lists.add("Sqindia");
        district_lists.add("IFindCard");
        district_lists.add("Zoho");

        img_back.setVisibility(View.GONE);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        b.show();




    }*/

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
