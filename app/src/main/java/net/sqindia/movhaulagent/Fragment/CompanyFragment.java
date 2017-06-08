package net.sqindia.movhaulagent.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.Class.Dashboard;
import net.sqindia.movhaulagent.Model.Config_Utils;
import net.sqindia.movhaulagent.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Salman on 23-05-2017.
 **/

public class CompanyFragment extends Fragment {

    TextInputLayout til_company, til_person, til_phone, til_email, til_reg_id, til_address;
    Typeface tf;
    Button btn_submit;
    TextView tv_activity_header;
    LinearLayout lt_bottom;
    ScrollView scr_top;
    CountryCodePicker ccp_company;
    Snackbar snackbar;
    TextView tv_snack;
    String str_comp_name, str_contact_name, str_phone, str_email, str_corporate_id, str_address,str_mobile_prefix;
    EditText et_comp_name, et_contact_name, et_phone, et_email, et_corporate_id, et_address;
    ProgressDialog mProgressDialog;
    String id,token;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View get_CompanyView = inflater.inflate(R.layout.company_fragment, container, false);
        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_CompanyView);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lato.ttf");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        id = sharedPreferences.getString("id","");
        token = sharedPreferences.getString("token","");

        btn_submit = (Button) get_CompanyView.findViewById(R.id.button_submit);
        tv_activity_header = (TextView) getActivity().findViewById(R.id.textview_header);
        tv_activity_header.setText("Company");
        lt_bottom = (LinearLayout) get_CompanyView.findViewById(R.id.bottom);
        scr_top = (ScrollView) get_CompanyView.findViewById(R.id.layout_company);

        til_company = (TextInputLayout) get_CompanyView.findViewById(R.id.textinput_company);
        til_person = (TextInputLayout) get_CompanyView.findViewById(R.id.textinput_username);
        til_phone = (TextInputLayout) get_CompanyView.findViewById(R.id.textinput_number);
        til_email = (TextInputLayout) get_CompanyView.findViewById(R.id.textinput_email);
        til_reg_id = (TextInputLayout) get_CompanyView.findViewById(R.id.textinput_Corporate_id);
        til_address = (TextInputLayout) get_CompanyView.findViewById(R.id.textinput_address);
        ccp_company = (CountryCodePicker) get_CompanyView.findViewById(R.id.ccp_company);


        et_comp_name = (EditText) get_CompanyView.findViewById(R.id.edittext_company);
        et_contact_name = (EditText) get_CompanyView.findViewById(R.id.edittext_contact_name);
        et_phone = (EditText) get_CompanyView.findViewById(R.id.edittext_phone);
        et_email = (EditText) get_CompanyView.findViewById(R.id.edittext_email);
        et_corporate_id = (EditText) get_CompanyView.findViewById(R.id.edittext_corporate_id);
        et_address = (EditText) get_CompanyView.findViewById(R.id.edittext_address);

        til_company.setTypeface(tf);
        til_phone.setTypeface(tf);
        til_person.setTypeface(tf);
        til_email.setTypeface(tf);
        til_address.setTypeface(tf);
        til_reg_id.setTypeface(tf);
        ccp_company.setTypeFace(tf);

        snackbar = Snackbar
                .make(getActivity().findViewById(R.id.top), "No NetWork", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        tv_snack = (android.widget.TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv_snack.setTextColor(Color.WHITE);
        tv_snack.setTypeface(tf);


        str_mobile_prefix = "+234";


        ccp_company.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                str_mobile_prefix = ccp_company.getSelectedCountryCodeWithPlus();
                Log.e("tag", "flg_ccp" + ccp_company.getSelectedCountryCodeWithPlus());
            }
        });


        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.wait));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Log.e("tag","login_Landscape");
         /*   RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // or wrap_content
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // or wrap_content
            layoutParams1.addRule(RelativeLayout.ABOVE,R.id.bottom);
            lt_bottom.setLayoutParams(layoutParams);
            scr_top.setLayoutParams(layoutParams1);*/
        } else {
            //Log.e("tag","login_Portrait");
            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT); // or wrap_content
            //layoutParams.addRule(RelativeLayout.BELOW,R.id.layout_company);
            // lt_bottom.setLayoutParams(layoutParams);
            // lt_bottom.setPadding(0,0,0,30);

        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                str_comp_name = et_comp_name.getText().toString().trim();
                str_contact_name = et_contact_name.getText().toString().trim();
                str_phone = et_phone.getText().toString().trim();
                str_email = et_email.getText().toString().trim();
                str_corporate_id = et_corporate_id.getText().toString().trim();
                str_address = et_address.getText().toString().trim();

                if (!str_comp_name.isEmpty() && str_comp_name.length() > 4) {
                    if (!str_contact_name.isEmpty() && str_contact_name.length() > 4) {
                        if (!str_phone.isEmpty() && str_phone.length() > 9) {
                            if (!str_email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                                if (!str_corporate_id.isEmpty() && str_corporate_id.length() > 9) {
                                    if (!str_address.isEmpty() && str_address.length() > 4) {

                                        new add_company().execute();

                                    } else {
                                        snackbar.show();
                                        tv_snack.setText("Enter Valid Address");
                                        et_address.requestFocus();
                                    }
                                } else {
                                    snackbar.show();
                                    tv_snack.setText("Enter Valid Corporate ID");
                                    et_corporate_id.requestFocus();
                                }
                            } else {
                                snackbar.show();
                                tv_snack.setText("Enter Valid Email");
                                et_email.requestFocus();
                            }
                        } else {
                            snackbar.show();
                            tv_snack.setText("Enter Valid Phone Number");
                            et_phone.requestFocus();
                        }

                    } else {
                        snackbar.show();
                        tv_snack.setText("Enter Valid Contact Person.");
                        et_contact_name.requestFocus();
                    }
                } else {
                    snackbar.show();
                    tv_snack.setText("Enter Valid Company Name.");
                    et_comp_name.requestFocus();
                }


            }
        });


        return get_CompanyView;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public class add_company extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("tag", "booking_task");
            mProgressDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {
            String json = "", jsonStr = "";

            Log.e("tag", "no poto");

            String s = "";
            JSONObject jsonObject = new JSONObject();
            try {


                jsonObject.put("company_name", str_comp_name);
                jsonObject.put("drop_location", str_contact_name);
                jsonObject.put("vehicle_type", str_mobile_prefix+str_phone);
                jsonObject.put("vehicle_main_type", str_email);
                jsonObject.put("vehicle_sub_type", str_corporate_id);
                jsonObject.put("vehicle_sub_type", str_address);


                json = jsonObject.toString();

                return s = Config_Utils.makeRequest1(Config_Utils.WEB_URL + "customer/booking", json, id, token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("tag", "tag" + s);
            mProgressDialog.dismiss();

            if (s != null) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    String bookingid = jo.getString("booking_id");
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("true")) {
                        Log.e("tag", "Location Updated");


                    } else if (status.equals("false")) {

                        Log.e("tag", "Location not updated");
                        //has to check internet and location...

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("tag", "nt" + e.toString());
                }
            } else {
                // Toast.makeText(getApplicationContext(),"Network Errror1",Toast.LENGTH_LONG).show();
            }

        }

    }

}
