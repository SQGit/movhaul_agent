package com.movhaul.agent.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.sloop.fonts.FontsManager;

import com.movhaul.agent.activity.LoginOtpActivity;
import com.movhaul.agent.Model.Config_Utils;
import com.movhaul.agent.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Salman on 23-05-2017.
 */

public class LoginFragment extends Fragment {

    TextView tv_header_txt, tv_register, tv_admin, tv_snack;
    TextInputLayout til_phone, til_user_name, til_password;
    Typeface tf;
    LinearLayout lt_agent, lt_admin, lt_bottom;
    Button btn_submit;
    boolean bl_admin;
    EditText et_phone, et_user_name, et_password;
    String str_phone, str_username, str_password, str_mobile_prefix;
    Snackbar snackbar;

    ProgressDialog mProgressDialog;
    CountryCodePicker ccp_login;
    Dialog dialog_verify;
    Button btn_ok, d2_btn_ok;
    TextView tv_dialog1, tv_dialog2, tv_dialog3, tv_dialog4, d2_tv_dialog1, d2_tv_dialog2, d2_tv_dialog3, d2_tv_dialog4;
    ImageView btn_close, iv_driver_lic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View get_LoginView = inflater.inflate(R.layout.login_fragment, container, false);
        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_LoginView);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lato.ttf");

        Log.e("tag", "login_fragment");
        tv_header_txt = (TextView) getActivity().findViewById(R.id.textview_header);
        tv_header_txt.setText(getString(R.string.login));

        lt_agent = (LinearLayout) get_LoginView.findViewById(R.id.agent_layout);
        lt_admin = (LinearLayout) get_LoginView.findViewById(R.id.admin_layout);
        til_phone = (TextInputLayout) get_LoginView.findViewById(R.id.textinput_number);
        til_user_name = (TextInputLayout) get_LoginView.findViewById(R.id.textinput_admin_username);
        til_password = (TextInputLayout) get_LoginView.findViewById(R.id.textinput_admin_password);
        tv_register = (TextView) get_LoginView.findViewById(R.id.textview_register);
        btn_submit = (Button) get_LoginView.findViewById(R.id.button_submit);

        tv_admin = (TextView) get_LoginView.findViewById(R.id.textview_admin);
        lt_bottom = (LinearLayout) get_LoginView.findViewById(R.id.bottom);

        et_phone = (EditText) get_LoginView.findViewById(R.id.edittext_login_phone);
        et_user_name = (EditText) get_LoginView.findViewById(R.id.edittext_username);
        et_password = (EditText) get_LoginView.findViewById(R.id.edittext_password);

        ccp_login = (CountryCodePicker) get_LoginView.findViewById(R.id.ccp_login);

        snackbar = Snackbar
                .make(getActivity().findViewById(R.id.top), "No NetWork", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        tv_snack = (android.widget.TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv_snack.setTextColor(Color.WHITE);
        tv_snack.setTypeface(tf);


        til_phone.setTypeface(tf);
        til_user_name.setTypeface(tf);
        til_password.setTypeface(tf);

        str_mobile_prefix = "+234";


        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.wait));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        ccp_login.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                str_mobile_prefix = ccp_login.getSelectedCountryCodeWithPlus();
                Log.e("tag", "flg_ccp" + ccp_login.getSelectedCountryCodeWithPlus());
            }
        });


        dialog_verify = new Dialog(getActivity());
        dialog_verify.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_verify.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_verify.setCancelable(false);
        dialog_verify.setContentView(R.layout.dialog_confirm);
        d2_btn_ok = (Button) dialog_verify.findViewById(R.id.button_ok);
        btn_close = (ImageView) dialog_verify.findViewById(R.id.button_close);
        d2_tv_dialog1 = (TextView) dialog_verify.findViewById(R.id.textView_1);
        d2_tv_dialog2 = (TextView) dialog_verify.findViewById(R.id.textView_2);
        d2_tv_dialog3 = (TextView) dialog_verify.findViewById(R.id.textView_3);
        d2_tv_dialog4 = (TextView) dialog_verify.findViewById(R.id.textView_4);

        d2_tv_dialog1.setTypeface(tf);
        d2_tv_dialog2.setTypeface(tf);
        d2_tv_dialog3.setTypeface(tf);
        d2_tv_dialog4.setTypeface(tf);
        d2_btn_ok.setTypeface(tf);

        d2_tv_dialog1.setText(R.string.ad);
        d2_tv_dialog2.setText(R.string.asdf);
        d2_tv_dialog3.setText(R.string.cea);
        d2_tv_dialog4.setVisibility(View.GONE);
        btn_close.setVisibility(View.GONE);

        d2_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_verify.dismiss();
            }
        });


  /*      if(savedInstanceState != null){

            Log.e("tag","bool "+savedInstanceState.getBoolean("admin",false));
            bl_admin = savedInstanceState.getBoolean("admin",false);
            if(bl_admin){
                lt_agent.setVisibility(View.GONE);
                lt_admin.setVisibility(View.VISIBLE);
                tv_register.setVisibility(View.GONE);
                Log.e("tag","admin_portal");
                tv_admin.setText("Change to Agent");
            }
            else{
                lt_agent.setVisibility(View.VISIBLE);
                lt_admin.setVisibility(View.GONE);
                tv_register.setVisibility(View.VISIBLE);
                Log.e("tag","agent_portal");
            }
            //  lt_admin.setVisibility(bl_admin?View.VISIBLE:View.GONE);
        }*/


        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Log.e("tag","login_Landscape");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // or wrap_content
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            lt_bottom.setLayoutParams(layoutParams);
        } else {
            //Log.e("tag","login_Portrait");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT); // or wrap_content
            layoutParams.addRule(RelativeLayout.BELOW, R.id.layout_login);
            lt_bottom.setLayoutParams(layoutParams);
        }

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new RegisterFragment(), "register");
                ft.addToBackStack("register");
                ft.commit();
                tv_header_txt.setText(getString(R.string.register));
            }
        });

        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_admin.getText().toString().equals("Change to Admin")) {
                    tv_admin.setText("Change to Agent");
                    lt_agent.setVisibility(View.GONE);
                    lt_admin.setVisibility(View.VISIBLE);
                    tv_register.setVisibility(View.GONE);
                    bl_admin = true;

                } else {
                    tv_admin.setText("Change to Admin");
                    lt_agent.setVisibility(View.VISIBLE);
                    lt_admin.setVisibility(View.GONE);
                    tv_register.setVisibility(View.VISIBLE);
                    bl_admin = false;
                }
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lt_admin.getVisibility() == View.GONE) {

                    str_phone = et_phone.getText().toString().trim();

                    if (!str_phone.isEmpty()) {
                        if (str_phone.length() > 9) {

                            if (Config_Utils.isConnected(getActivity())) {
                                new login_agent().execute();
                            } else {
                                snackbar.show();
                                tv_snack.setText(R.string.network);
                            }

                        } else {
                            snackbar.show();
                            tv_snack.setText("Enter Valid Mobile Number.");
                        }
                    } else {
                        snackbar.show();
                        tv_snack.setText("Enter  Mobile Number.");
                    }
                } else {
                    str_username = et_user_name.getText().toString().trim();
                    str_password = et_password.getText().toString().trim();

                    if (!str_username.isEmpty()) {
                        if (str_username.length() > 4) {
                            if (!str_password.isEmpty()) {
                                if (str_password.length() > 4) {


                                } else {
                                    snackbar.show();
                                    tv_snack.setText("Password Should be more than 4 digits.");
                                }
                            } else {
                                snackbar.show();
                                tv_snack.setText("Enter Password");
                            }
                        } else {
                            snackbar.show();
                            tv_snack.setText("UserName Should be more than 4 digits.");
                        }
                    } else {
                        snackbar.show();
                        tv_snack.setText("Enter User Name.");
                    }
                }


            }
        });


        return get_LoginView;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("admin", bl_admin);
        Log.e("tag", "outstate_login");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            Log.e("tag", "bool " + savedInstanceState.getBoolean("admin", false));
            bl_admin = savedInstanceState.getBoolean("admin", false);
            if (bl_admin) {
                lt_agent.setVisibility(View.GONE);
                lt_admin.setVisibility(View.VISIBLE);
                tv_register.setVisibility(View.GONE);
                tv_admin.setText("Change to Agent");

                Log.e("tag", "admin_portal");
            } else {
                lt_agent.setVisibility(View.VISIBLE);
                lt_admin.setVisibility(View.GONE);
                tv_register.setVisibility(View.VISIBLE);
                tv_admin.setText("Change to Admin");
                Log.e("tag", "agent_portal");
            }
            //  lt_admin.setVisibility(bl_admin?View.VISIBLE:View.GONE);
        }

    }


    public class login_agent extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("tag", "reg_preexe");
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String json = "", jsonStr = "";
            Log.e("tag", "web: " + Config_Utils.WEB_URL + "agentotp");

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("agent_mobile", str_mobile_prefix + str_phone);

                json = jsonObject.toString();
                return jsonStr = Config_Utils.makeRequest(Config_Utils.WEB_URL + "agentotp", json);

            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());

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
                    String msg = "msg";
                    if (jo.has("message")) {
                        msg = jo.getString("message");
                    }
                    Log.e("tag", "<-----Status----->" + status);
                    if (status.equals("true")) {

                        String type = jo.getString("vehicle_type");


                        // String sus_txt = "Thank you for Signing Up MoveHaul.";

                        //Toast.makeText(getApplicationContext(),sus_txt,Toast.LENGTH_LONG).show();

                        /*Intent i = new Intent(LoginActivity.this, LoginOtpActivity.class);
                        i.putExtra("for","phone");
                        i.putExtra("vec_type",type);
                        i.putExtra("data",str_mobile_prefix+str_mobile);
                        i.putExtra("prefix",str_mobile_prefix);
                        startActivity(i);
                        finish();*/


                    } else if (status.equals("false")) {

                        if (jo.has("message")) {
                            msg = jo.getString("message");
                        }
                        //msg = jo.getString("message");
                        Log.e("tag", "ms:" + msg);

                        if (msg.contains("Register with Movhaul first to generate OTP")) {

                            // Toast.makeText(getApplicationContext(),"Mobile Number Not Registered",Toast.LENGTH_LONG).show();

                            tv_snack.setText(msg);
                            snackbar.show();


                        } else if (msg.contains("Error Occured[object Object]")) {

                            //  String type = jo.getString("vehicle_type");

                            Intent i = new Intent(getActivity(), LoginOtpActivity.class);
                            i.putExtra("for", "phone");
                            i.putExtra("data", str_mobile_prefix + str_phone);
                            i.putExtra("prefix", str_mobile_prefix);
                            startActivity(i);
                            getActivity().finish();
                        } else if (msg.contains("{\"agent_verification\":")) {
//                            Log.e("tag","ds: "+jo.getString("agent_verification"));
                            //  Log.e("tag","da: "+jo.getString("account_status"));
                            dialog_verify.show();
                        } else if (jo.has("agent_verification")) {
                            //  Log.e("tag","ds: "+jo.getString("agent_verification"));
                            //   Log.e("tag","da: "+jo.getString("account_status"));
                            dialog_verify.show();
                        } else if (msg.contains("{\"agent_verification\":\"pending\",\"account_status\":\"active\"}")) {
                            dialog_verify.show();
                        } else {

                            //Toast.makeText(getApplicationContext(),"Please Try Again Later",Toast.LENGTH_LONG).show();
                            snackbar.show();
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("tag", "nt" + e.toString());
                    snackbar.show();
                }
            } else {
                snackbar.show();
            }

        }

    }


}
