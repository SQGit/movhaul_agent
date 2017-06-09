package net.sqindia.movhaulagent.Class;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.Fragment.CompanyFragment;
import net.sqindia.movhaulagent.Fragment.DriverFragment;
import net.sqindia.movhaulagent.Fragment.LoginFragment;
import net.sqindia.movhaulagent.Model.Config_Utils;
import net.sqindia.movhaulagent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 25-05-2017.
 */

public class Dashboard extends AppCompatActivity {

    LinearLayout lt_back;
    ImageButton ib_driver_add, ib_comp_add;

    ArrayList<String>  ar_comp_lists;

    TextView tv_header;
    ImageView img_back;
    ArrayList<String> driver_type = new ArrayList<>();
    ArrayList<String> company_lists = new ArrayList<>();
    ListAdapter adapter1, adapter2, adapter3;
    AlertDialog b;
    int sts;
    ListView lview_state, lview_district;
    FrameLayout fl_header, fl_bottom;
    boolean bl_bottom;
    Dialog dialog_yes_no;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Typeface tf;
    TextView tv_txt1, tv_txt2, tv_txt3, tv_snack2, sb_text, tv_snack_act, tv_snack;
    Button btn_yes, btn_no, btn_submit;
    LinearLayout lt_content;
    private ViewPager viewPager;
    private int[] layouts;
    String id,token;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    String str_name;
    TextView tv_header_name;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        FontsManager.initFormAssets(Dashboard.this, "fonts/lato.ttf");
        FontsManager.changeFonts(Dashboard.this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        editor = sharedPreferences.edit();

        id = sharedPreferences.getString("id","");
        token = sharedPreferences.getString("token","");


        str_name = sharedPreferences.getString("agent_name","");

        lt_back = (LinearLayout) findViewById(R.id.action_back);
        tv_header_name = (TextView) findViewById(R.id.textview_header_name);

        if(sharedPreferences.getString("login","").equals("success")){
            lt_back.setVisibility(View.GONE);
        }


        lt_content = (LinearLayout) findViewById(R.id.content);
        ib_driver_add = (ImageButton) findViewById(R.id.add_driver);
        ib_comp_add = (ImageButton) findViewById(R.id.add_company);
        fl_bottom = (FrameLayout) findViewById(R.id.bottom_layout);

        tv_header = (TextView) findViewById(R.id.textview_header);


        tv_header_name.setText("Welcome "+str_name);



        snackbar = Snackbar.make(findViewById(R.id.top), "No NetWork", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        tv_snack = (android.widget.TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv_snack.setTextColor(Color.WHITE);
        tv_snack.setTypeface(tf);

        mProgressDialog = new ProgressDialog(Dashboard.this);
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.wait));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        if (Config_Utils.isConnected(Dashboard.this)) {
            new fetch_data().execute();
        } else {
            snackbar.show();
            tv_snack.setText(R.string.network);
        }



        dialog_yes_no = new Dialog(Dashboard.this);
        dialog_yes_no.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes_no.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_yes_no.setCancelable(false);
        dialog_yes_no.setContentView(R.layout.dialog_yes_no);
        btn_yes = (Button) dialog_yes_no.findViewById(R.id.button_yes);
        btn_no = (Button) dialog_yes_no.findViewById(R.id.button_no);

        tv_txt1 = (TextView) dialog_yes_no.findViewById(R.id.textView_1);
        tv_txt2 = (TextView) dialog_yes_no.findViewById(R.id.textView_2);
        tv_txt3 = (TextView) dialog_yes_no.findViewById(R.id.textView_3);

        tv_txt1.setTypeface(tf);
        tv_txt2.setTypeface(tf);
        tv_txt3.setTypeface(tf);
        btn_yes.setTypeface(tf);
        btn_no.setTypeface(tf);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                dialog_yes_no.dismiss();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_yes_no.dismiss();
            }
        });

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ib_driver_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                driver_type.clear();
           //     company_lists.clear();



           //     company_lists.add("IBM");
            //    company_lists.add("MovHaul");
            //    company_lists.add("Opiniion");
            //    company_lists.add("Sqindia");
            //    company_lists.add("IFindCard");
            //    company_lists.add("Zoho");

                if(ar_comp_lists.size()>0) {

                    driver_type.add("Private");
                    driver_type.add("Corporate");


                    popupwithlistview();
                }
                else{

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new DriverFragment());
                    ft.addToBackStack("driver");
                    ft.commit();
                    fl_bottom.setVisibility(View.GONE);
                    tv_header.setText("Driver");
                    lt_content.setVisibility(View.GONE);

                }


            }
        });

        ib_comp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new CompanyFragment());
                ft.addToBackStack("company");
                ft.commit();
                fl_bottom.setVisibility(View.GONE);
                tv_header.setText("Company");
                lt_content.setVisibility(View.GONE);

            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                FragmentManager manager = getSupportFragmentManager();
                int index = manager.getBackStackEntryCount() - 1;
                Log.e("tag","bakstk"+index);
                if(index<0){
                    fl_bottom.setVisibility(View.VISIBLE);
                    tv_header.setText("Dashboard");
                    lt_content.setVisibility(View.VISIBLE);

                }
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bl_bottom = fl_bottom.getVisibility() == View.VISIBLE;
        outState.putBoolean("bl_layout", bl_bottom);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        bl_bottom = savedInstanceState.getBoolean("bl_layout");
        fl_bottom.setVisibility(bl_bottom ? View.VISIBLE : View.GONE);
        lt_content.setVisibility(bl_bottom ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.e("tag", "frag_count : " + getSupportFragmentManager().getBackStackEntryCount());
        Log.e("tag", "frag_countee : " + getSupportFragmentManager().findFragmentByTag("driver"));


        FragmentManager manager = getSupportFragmentManager();
        int index = manager.getBackStackEntryCount() - 1;

        if(index>=0){

            manager.popBackStackImmediate();
            fl_bottom.setVisibility(View.VISIBLE);
            tv_header.setText("Dashboard");
            lt_content.setVisibility(View.VISIBLE);

        }
        else{
            dialog_yes_no.show();
        }


        //
    }

    public void popupwithlistview() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Dashboard.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register_state, null);
        dialogBuilder.setView(dialogView);
        b = dialogBuilder.create();


        sts = 0;

        fl_header = (FrameLayout) dialogView.findViewById(R.id.framelayout);
        fl_header.setVisibility(View.GONE);
        lview_state = (ListView) dialogView.findViewById(R.id.lview);
        lview_district = (ListView) dialogView.findViewById(R.id.lview1);
        Log.e("tag", "stsiz: " + driver_type.size());
        adapter1 = new ListAdapter(getApplicationContext(), R.layout.dialog_region_txts0, driver_type);
        lview_state.setAdapter(adapter1);
        adapter2 = new ListAdapter(getApplicationContext(), R.layout.dialog_region_txts, ar_comp_lists);
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


            if (posi == data_lists.size() - 1) {
                vie_line.setVisibility(View.GONE);
            }

            label.setTypeface(tf);

            label.setText(data_lists.get(posi));

            arow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sts == 0) {

                        if (posi == 0) {
                            Log.e("tag", "clicked" + posi);
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_container, new DriverFragment());
                            ft.addToBackStack("driver");
                            ft.commit();
                            fl_bottom.setVisibility(View.GONE);
                            tv_header.setText("Driver");
                            lt_content.setVisibility(View.GONE);
                            b.dismiss();

                            editor.putString("company","movhaul");
                            editor.apply();

                        } else {
                            lview_state.setVisibility(View.GONE);
                            lview_district.setVisibility(View.VISIBLE);
                            sts = 1;
                        }
                    } else {
                        fl_header.setVisibility(View.VISIBLE);

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, new DriverFragment());
                        ft.addToBackStack("driver_corporate");
                        ft.commit();
                        b.dismiss();
                        tv_header.setText("Corporate");
                        fl_bottom.setVisibility(View.GONE);
                        lt_content.setVisibility(View.GONE);
                        sts = 0;

                        Log.e("tag","pp: "+data_lists.get(posi));
                        editor.putString("company",data_lists.get(posi));
                        editor.apply();
                    }

                }


            });


            return arow;
        }
    }


    class fetch_data extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                String virtual_url = Config_Utils.WEB_URL + "agent/getcompanydriver";
                JSONObject jsonobject = Config_Utils.getData(virtual_url, id, token);
                if (jsonobject.toString() == "sam") {
                }
                json = jsonobject.toString();
                return json;
            } catch (Exception e) {
                jsonStr = "";
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            mProgressDialog.dismiss();
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                if (status.equals("true")) {

                    ar_comp_lists = new ArrayList<String>();
                    JSONArray jsr_company = jo.getJSONArray("company");
                    JSONArray jsr_driver = jo.getJSONArray("driver");
                    if (jsr_company.length() > 0) {

                       // hash_subtype = new HashMap<String, String>();
                       // hash_subtype1 = new HashMap<String, String>();
                        for (int i = 0; i < jsr_company.length(); i++) {
                            String datas = jsr_company.getString(i);
                            JSONObject subs = new JSONObject(datas);

                            Log.e("tag","cc: :"+subs.getString("company_name"));
                            ar_comp_lists.add(subs.getString("company_name"));
                               /* ar_truck_type.add(subs.getString("vehicle_main_type"));
                                ar_truck_sstype.add(subs.getString("vehicle_sub_type"));
                                ar_truck_imgs.add(subs.getString("vehicle_image"));
                                hash_subtype.put(subs.getString("vehicle_sub_type"), subs.getString("vehicle_main_type"));
                                hash_truck_imgs.put(subs.getString("vehicle_image"), subs.getString("vehicle_main_type"));*/
                        }



                    } else {


                    }














                } else {
                }
                //  Log.e("tag", "trk_siz_img" + ar_truck_imgs.size());
                //  Log.e("tag", "bus_siz" + ar_truck_type1.size());
                //  Log.e("tag", "bus_siz_img" + ar_truck_imgs1.size());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
