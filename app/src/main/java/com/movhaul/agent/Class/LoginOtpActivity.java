package com.movhaul.agent.Class;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

import com.movhaul.agent.Model.Config_Utils;
import com.movhaul.agent.R;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Salman on 08-06-2017.
 */

public class LoginOtpActivity extends Activity implements TextWatcher {


    static EditText et_otp1, et_otp2, et_otp3, et_otp4;
    private static LoginOtpActivity inst;
    LinearLayout btn_back;
    String str_phone;
    Button btn_submit;
    TextView tv_resendotp, tv_snack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Snackbar snackbar;
    Config_Utils config;
    ProgressDialog mProgressDialog;
    Typeface tf;
    String str_otppin, str_for, str_data, str_prefix;
    private View view;
    public InputMethodManager imml;

    int a,b,c,d;
    InputMethodManager inputMethodManager;
    LinearLayout lt_msg;

   // ReceiveSmsBroadcastReceiver receiver;

    private LoginOtpActivity(View view) {
        this.view = view;
    }

    public LoginOtpActivity() {

    }

    public static LoginOtpActivity instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_otp_screen);
        FontsManager.initFormAssets(this, "fonts/lato.ttf");       //initialization
        FontsManager.changeFonts(this);
        config = new Config_Utils();

        Intent getIntent = getIntent();

        str_for = getIntent.getStringExtra("for");
        str_data = getIntent.getStringExtra("data");
        str_prefix = getIntent.getStringExtra("prefix");

        lt_msg = (LinearLayout) findViewById(R.id.msg_layout);


        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            lt_msg.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            lt_msg.setOrientation(LinearLayout.VERTICAL);
        }


        Log.e("tag", "data:" + str_for);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginOtpActivity.this);
        editor = sharedPreferences.edit();
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/lato.ttf");


        mProgressDialog = new ProgressDialog(LoginOtpActivity.this);
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.wait));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);


        snackbar = Snackbar
                .make(findViewById(R.id.top), "Network Error! Please Try Again Later.", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        tv_snack = (android.widget.TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv_snack.setTextColor(Color.WHITE);
        tv_snack.setTypeface(tf);

        if (!config.isConnected(LoginOtpActivity.this)) {
            snackbar.show();
            tv_snack.setText("Please Connect Internet and Try again");
        }


        btn_back = (LinearLayout) findViewById(R.id.action_back);

        et_otp1 = (EditText) findViewById(R.id.editext_otp1);
        et_otp2 = (EditText) findViewById(R.id.editext_otp2);
        et_otp3 = (EditText) findViewById(R.id.editext_otp3);
        et_otp4 = (EditText) findViewById(R.id.editext_otp4);

        btn_submit = (Button) findViewById(R.id.button_submit);
        tv_resendotp = (TextView) findViewById(R.id.textview_resendotp);


        /*IntentFilter filter = new IntentFilter();
        filter.addAction("SOME_ACTION");
        filter.addAction("SOME_OTHER_ACTION");
        receiver = new ReceiveSmsBroadcastReceiver();

        registerReceiver(receiver,filter);*/
/*
        ReceiveSmsBroadcastReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.e("Text",messageText);

                if(messageText.contains("Your MoveHaul OTP")) {
                    String[] parts = messageText.trim().split("is");
                    Log.e("tag", "part1: " + parts);
                    String part1 = parts[0]; // 004
                    String part2 = parts[1]; // 034556
                    Log.e("tag", "part1: " + part1);
                    Log.e("tag", "part2: " + part2);
                    part2 = part2.trim();
                    // cArray = part2.toCharArray();
                    Log.e("tag", "partqq: " + part2);
                    char[] cArray = part2.toCharArray();
                    et_otp1.setText(String.valueOf(cArray[0]));
                    et_otp2.setText(String.valueOf(cArray[1]));
                    et_otp3.setText(String.valueOf(cArray[2]));
                    et_otp4.setText(String.valueOf(cArray[3]));
                }

                //Toast.makeText(LoginOtpActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();
            }
        });*/


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginOtpActivity.this, LoginRegister.class);
                startActivity(i);
                finish();
            }
        });


        imml = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        et_otp1.addTextChangedListener(new LoginOtpActivity(et_otp1));
        et_otp2.addTextChangedListener(new LoginOtpActivity(et_otp2));
        et_otp3.addTextChangedListener(new LoginOtpActivity(et_otp3));
        et_otp4.addTextChangedListener(new LoginOtpActivity(et_otp4));


        et_otp4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.e("tag", " code: " + keyCode + " event: " + event.getAction());

                if (keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_DEL) {

                    if (et_otp4.getText().toString().trim().length() == 1) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, 0);
                        Log.e("tag", " above");
                    }
                }
                else{
                    Log.e("tag","a"+(++d));
                    if(d>3){
                        et_otp3.requestFocus();
                        d=0;
                    }
                }
                return false;
            }
        });


        et_otp3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.e("tag", " code: " + keyCode + " event: " + event.getAction());

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    Log.e("tag","a"+(++c));
                    if(c>3){
                        et_otp2.requestFocus();
                        c=0;
                    }

                }
                return false;
            }
        });

        et_otp2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.e("tag", " code: " + keyCode + " event: " + event.getAction());

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    Log.e("tag","a"+(++b));
                    if(b>3){
                        et_otp1.requestFocus();
                        b=0;
                    }

                }
                return false;
            }
        });

        tv_resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_resendotp.setVisibility(View.GONE);
                //new resend_otp().execute();

                //Toast.makeText(getApplicationContext(), "Otp Send to " + str_phone, Toast.LENGTH_LONG).show();
            }
        });


        inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp1.getText().toString().isEmpty()) {
                    et_otp1.requestFocus();


                    inputMethodManager.toggleSoftInputFromWindow(
                            et_otp1.getApplicationWindowToken(),
                            InputMethodManager.SHOW_FORCED, 0);

                } else {
                    if (et_otp2.getText().toString().isEmpty()) {
                        et_otp2.requestFocus();
                        inputMethodManager.toggleSoftInputFromWindow(
                                et_otp1.getApplicationWindowToken(),
                                InputMethodManager.SHOW_FORCED, 0);
                    } else {
                        if (et_otp3.getText().toString().isEmpty()) {
                            et_otp3.requestFocus();
                            inputMethodManager.toggleSoftInputFromWindow(
                                    et_otp1.getApplicationWindowToken(),
                                    InputMethodManager.SHOW_FORCED, 0);
                        } else {
                            if (et_otp4.getText().toString().isEmpty()) {
                                et_otp4.requestFocus();
                                inputMethodManager.toggleSoftInputFromWindow(
                                        et_otp1.getApplicationWindowToken(),
                                        InputMethodManager.SHOW_FORCED, 0);
                            } else {
                                str_otppin = et_otp1.getText().toString() + et_otp2.getText().toString() + et_otp3.getText().toString() + et_otp4.getText().toString();
                                Log.e("tag", "pin:" + str_otppin);

                                Log.e("tag", "for" + str_for + str_data);

                                new otp_verify().execute();
                            }
                        }
                    }
                }
            }
        });


    }



    public void recivedSms(String message) {
        try {
            Log.e("tag", "asd: " + message);
            char[] cArray = new char[0];
            //"Your MoveHaul OTP is 4770"

            try {
                String[] parts = message.trim().split("is");
                Log.e("tag", "part1: " + parts);
                String part1 = parts[0]; // 004
                String part2 = parts[1]; // 034556
                Log.e("tag", "part1: " + part1);
                Log.e("tag", "part2: " + part2);
                part2 = part2.trim();
                cArray = part2.toCharArray();
                Log.e("tag", "partqq: " + part2);
            }
            catch (Exception e){
                Log.e("tag", "err: " + e.toString());
            }



            et_otp1.setText(String.valueOf(cArray[cArray.length - 4]));
            et_otp2.setText(String.valueOf(cArray[cArray.length - 3]));
            et_otp3.setText(String.valueOf(cArray[cArray.length - 2]));
            et_otp4.setText(String.valueOf(cArray[cArray.length - 1]));


        } catch (Exception e) {
            Log.e("tag", "Err: " + e.toString());
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Log.e("tag", "before " + charSequence + " i " + i + " i1 " + i1 + " i2 " + i2);
    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Log.e("tag", "ontext " + charSequence + " i " + i + " i1 " + i1 + " i2 " + i2);
    }
    @Override
    public void afterTextChanged(Editable editable) {

        Log.e("tag", "editable " + String.valueOf(editable.toString()));


        switch (view.getId()) {
            case R.id.editext_otp1:
                Log.e("tag", "editable_01 " + String.valueOf(editable.toString()));
                /*if (editable.length() == 0) {
                    et_otp1.requestFocus();
                } else*/ if (editable.length() == 1) {
                et_otp2.requestFocus();
            }

                break;
            case R.id.editext_otp2:
                Log.e("tag", "editable_02 " + String.valueOf(editable.toString()));
                /*if (editable.length() == 0) {
                    et_otp1.requestFocus();
                } else*/ if (editable.length() == 1) {
                et_otp3.requestFocus();
            }

                break;
            case R.id.editext_otp3:
                Log.e("tag", "editable_03 " + String.valueOf(editable.toString()));
                /*if (editable.length() == 0) {
                    et_otp2.requestFocus();
                } else */if (editable.length() == 1) {
                et_otp4.requestFocus();
            }
                break;
            case R.id.editext_otp4:
                Log.e("tag", "editable_04 " + String.valueOf(editable.toString()));
               /* if (editable.length() == 0) {
                    et_otp4.requestFocus();
                }*//* else  {
                    et_otp3.requestFocus();
                }*/

                break;
        }


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(LoginOtpActivity.this, LoginRegister.class);
        startActivity(i);
        finish();
    }
    public class otp_verify extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("tag", "reg_preexe");
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();


                Log.e("tag", str_for + " dd: " + str_data + str_otppin);

                if (str_for.equals("phone")) {

                    jsonObject.accumulate("agent_mobile",str_data);
                    jsonObject.accumulate("agent_otp", str_otppin);
                } else {

                    jsonObject.accumulate("agent_email", str_data);
                    jsonObject.accumulate("agent_otp", str_otppin);
                }


                json = jsonObject.toString();
                return jsonStr = Config_Utils.makeRequest(Config_Utils.WEB_URL + "agent/login", json);

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
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("true")) {

                        String name = jo.getString("agent_name");
                        String address = jo.getString("agent_address");
                        String state = jo.getString("agent_state");
                        String city = jo.getString("agent_city");
                        String phone = jo.getString("agent_mobile");

                        String email = jo.getString("agent_email");
                        String coverage_area = jo.getString("agent_coverage_area");
                        String bank = jo.getString("agent_bank");
                        String bank_no = jo.getString("agent_account_no");
                        String id_photo = jo.getString("agent_identity");
                        String profile_photo = jo.getString("agent_image");

                        editor.putString("id", jo.getString("id"));
                        editor.putString("token", jo.getString("token"));
                        editor.putString("agent_name", name);
                        editor.putString("agent_address", address);
                        editor.putString("agent_state", state);
                        editor.putString("agent_city", city);
                        editor.putString("agent_mobile", phone);
                        editor.putString("agent_email", email);
                        editor.putString("agent_bank", bank);
                        editor.putString("agent_bankno", bank_no);
                        editor.putString("agent_coverage", coverage_area);
                        editor.putString("agent_image", profile_photo);
                        editor.putString("agent_identity", id_photo);
                        editor.putString("login", "success");
                        editor.commit();

                        Intent i = new Intent(LoginOtpActivity.this, Dashboard.class);
                        startActivity(i);
                        finish();


                    } else if (status.equals("false")) {


                        if (msg.contains("Authentication failed.Wrong Password")) {

                            tv_snack.setText("Otp Failed. Try Again Later");
                            snackbar.show();


                        } else {

                            tv_snack.setText("Network Error Please Try again Later.");
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
    public class resend_otp extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Log.e("tag", "reg_preexe");
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String json = "", jsonStr = "";

            try {
                JSONObject jsonObject = new JSONObject();


                if (str_for.equals("phone")) {

                    jsonObject.accumulate("agent_mobile", str_data);
                    jsonObject.accumulate("agent_otp", str_otppin);
                } else {

                    jsonObject.accumulate("agent_email", str_data);
                    jsonObject.accumulate("agent_otp", str_otppin);
                }

                json = jsonObject.toString();
                return jsonStr = Config_Utils.makeRequest(Config_Utils.WEB_URL + "agent/login", json);

            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());
                mProgressDialog.dismiss();
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
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("true")) {


                        // String sus_txt = "Thank you for Signing Up MoveHaul.";

                        //Toast.makeText(getApplicationContext(),sus_txt,Toast.LENGTH_LONG).show();
                        tv_snack.setText("Otp Send to " + str_data);
                        snackbar.show();


                    } else if (status.equals("false")) {


                        if (msg.contains("Error Occured[object Object]")) {


                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("tag", "nt" + e.toString());
                    // Toast.makeText(getApplicationContext(),"Network Errror0",Toast.LENGTH_LONG).show();
                    snackbar.show();
                }
            } else {
                // Toast.makeText(getApplicationContext(),"Network Errror1",Toast.LENGTH_LONG).show();
                snackbar.show();
            }

        }

    }

}
