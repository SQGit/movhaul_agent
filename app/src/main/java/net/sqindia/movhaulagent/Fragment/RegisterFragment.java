package net.sqindia.movhaulagent.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.hbb20.CountryCodePicker;
import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.Model.Config_Utils;
import net.sqindia.movhaulagent.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Salman on 23-05-2017.
 */

public class RegisterFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 234;
    private static final int REQUEST_CODE = 10;
    private static final int REQUEST_CODE_PHOTO = 20;
    public String[] ar_banks;
    public String[] ar_state;
    public String[] ar_city;
    public String[] ar_banks_copy;
    public HashMap<String, String[]> city_hash = new HashMap<>();
    TextView tv_activity_header, tv_login;
    TextInputLayout til_name, til_address, til_state, til_city, til_phone, til_email, til_bank;
    EditText et_name, et_address, et_state, et_city, et_phone, et_email, et_bank, et_coverage, et_bank_no;
    LinearLayout lt_state, lt_city, lt_bank, lt_coverage, lt_id_card, lt_photo;
    Typeface tf;
    CountryCodePicker ccp_register;
    ArrayList<Uri> image_uris;
    String str_id_card_photo, str_photograph;
    ImageView iv_id_card, iv_photograph;
    LinearLayout lt_action_back;
    Button btn_submit;
    Snackbar snackbar;
    TextView tv_snack;
    CheckBox cb_terms;
    String str_name, str_address, str_state, str_city, str_phone, str_email, str_coverage, str_bank, str_bank_no,str_mobile_prefix;
    Config_Utils config;
    Dialog dialog_success;
    ProgressDialog mProgressDialog;

    Button d2_btn_ok;
    TextView d2_tv_dialog1, d2_tv_dialog2, d2_tv_dialog3, d2_tv_dialog4;
    android.widget.ImageView btn_close;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View get_RegisterView = inflater.inflate(R.layout.register_fragment, container, false);

        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_RegisterView);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lato.ttf");

        snackbar = Snackbar
                .make(getActivity().findViewById(R.id.top), "No NetWork", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        tv_snack = (android.widget.TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv_snack.setTextColor(Color.WHITE);
        tv_snack.setTypeface(tf);

        config = new Config_Utils();

        city_hash = config.getCity_hash();

        ar_banks = new String[]{" GT Bank ", " Hongkong&Sangai Bank ", " SAFC ", " Bank Of Africa ", "Federal Bank of Nigeria", " LEKIA Bank ", " Nigeria Bank ",};

        ar_state = new String[]{"Abia", "Akwa Ibom", "Benue", "Borno", "Delta", "Enugu", "Edo", "Jigawa", "Kebbi", "Lagos", "Ogun", "Oyo", "Rivers", "Yobe"};

        ar_city = new String[]{"Asaba", "Bauchi", "Dutse", "Jimeta", "Kanduna", "Lafia", "Lekki", "Oron", "Port Harcourt", "Sokoto", "Warri", "Zaria"};
        str_mobile_prefix = "+234";

        tv_login = (TextView) get_RegisterView.findViewById(R.id.textview_login);
        tv_activity_header = (TextView) getActivity().findViewById(R.id.textview_header);
        tv_activity_header.setText(getString(R.string.register));
        Log.e("tag", "register");

        lt_action_back = (LinearLayout) getActivity().findViewById(R.id.action_back);
        lt_action_back.setVisibility(View.VISIBLE);

        til_name = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_username);
        til_address = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_address);
        til_state = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_state);
        til_city = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_city);
        til_phone = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_phone);
        til_email = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_email);
        til_bank = (TextInputLayout) get_RegisterView.findViewById(R.id.textinput_bank);
        lt_bank = (LinearLayout) get_RegisterView.findViewById(R.id.layout_choose_bank);
        lt_state = (LinearLayout) get_RegisterView.findViewById(R.id.layout_choose_state);
        lt_city = (LinearLayout) get_RegisterView.findViewById(R.id.layout_choose_city);
        lt_coverage = (LinearLayout) get_RegisterView.findViewById(R.id.layout_coverage);
        lt_id_card = (LinearLayout) get_RegisterView.findViewById(R.id.layout_id_card);
        lt_photo = (LinearLayout) get_RegisterView.findViewById(R.id.layout_photograph);
        btn_submit = (Button) get_RegisterView.findViewById(R.id.button_submit);

        iv_id_card = (ImageView) get_RegisterView.findViewById(R.id.imageview_idcard);
        iv_photograph = (ImageView) get_RegisterView.findViewById(R.id.imageview_photograph);

        et_name = (EditText) get_RegisterView.findViewById(R.id.edittext_user_name);
        et_address = (EditText) get_RegisterView.findViewById(R.id.edittext_address);
        et_phone = (EditText) get_RegisterView.findViewById(R.id.edittext_phone);
        et_email = (EditText) get_RegisterView.findViewById(R.id.edittext_email);
        et_bank_no = (EditText) get_RegisterView.findViewById(R.id.edittext_bank_no);
        et_bank = (EditText) get_RegisterView.findViewById(R.id.edittext_bank);
        et_state = (EditText) get_RegisterView.findViewById(R.id.edittext_state);
        et_city = (EditText) get_RegisterView.findViewById(R.id.edittext_city);
        et_coverage = (EditText) get_RegisterView.findViewById(R.id.edittext_coverage);
        cb_terms = (CheckBox) get_RegisterView.findViewById(R.id.check_box);


        ccp_register = (CountryCodePicker) get_RegisterView.findViewById(R.id.ccp_register);

        til_name.setTypeface(tf);
        til_address.setTypeface(tf);
        til_state.setTypeface(tf);
        til_city.setTypeface(tf);
        til_phone.setTypeface(tf);
        til_email.setTypeface(tf);
        til_bank.setTypeface(tf);
        ccp_register.setTypeFace(tf);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.wait));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        dialog_success = new Dialog(getActivity());
        dialog_success.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_success.setCancelable(false);
        dialog_success.setContentView(R.layout.dialog_confirm);
        d2_btn_ok = (Button) dialog_success.findViewById(R.id.button_ok);
        btn_close = (android.widget.ImageView) dialog_success.findViewById(R.id.button_close);
        d2_tv_dialog1 = (TextView) dialog_success.findViewById(R.id.textView_1);
        d2_tv_dialog2 = (TextView) dialog_success.findViewById(R.id.textView_2);
        d2_tv_dialog3 = (TextView) dialog_success.findViewById(R.id.textView_3);
        d2_tv_dialog4 = (TextView) dialog_success.findViewById(R.id.textView_4);

        d2_tv_dialog1.setTypeface(tf);
        d2_tv_dialog2.setTypeface(tf);
        d2_tv_dialog3.setTypeface(tf);
        d2_tv_dialog4.setTypeface(tf);
        d2_btn_ok.setTypeface(tf);

        d2_tv_dialog1.setText(R.string.success);
        d2_tv_dialog2.setText(R.string.thanks);
        d2_tv_dialog3.setText(R.string.verf);
        d2_tv_dialog4.setVisibility(View.GONE);
        btn_close.setVisibility(View.GONE);

        d2_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_success.dismiss();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new LoginFragment());
                ft.commit();
                tv_activity_header.setText(getString(R.string.login));
                lt_action_back.setVisibility(View.GONE);

            }
        });



        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new LoginFragment());
                ft.commit();
                tv_activity_header.setText(getString(R.string.login));
                lt_action_back.setVisibility(View.GONE);
            }
        });

        et_address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    et_phone.requestFocus();
                    return true;
                }
                return false;
            }
        });

        ccp_register.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                str_mobile_prefix = ccp_register.getSelectedCountryCodeWithPlus();
                Log.e("tag", "flg_ccp" + ccp_register.getSelectedCountryCodeWithPlus());
            }
        });

        et_bank.setText("GT Bank");
       /* lt_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(ar_banks, et_bank, 2);
            }
        });*/

        lt_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(config.states, et_state, 0);
                et_city.setText("");
            }
        });

        lt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_state.getText().toString().isEmpty()) {
                    ar_city = city_hash.get(et_state.getText().toString());
                    popup(ar_city, et_city, 1);
                } else {
                    snackbar.show();
                    tv_snack.setText("Choose State First.");
                }


            }
        });


        et_coverage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (et_coverage.getText().toString().trim().length() > 0) {

                        lt_coverage.setVisibility(View.VISIBLE);
                        String io = et_coverage.getText().toString();
                        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lparams.setMargins(5, 5, 5, 5);
                        TextView tv = new TextView(getActivity());
                        tv.setLayoutParams(lparams);
                        tv.setGravity(Gravity.LEFT);
                        Drawable img = getResources().getDrawable(R.drawable.del_icon);
                        img.setBounds(0, 0, 20, 20);
                        tv.setCompoundDrawables(null, null, img, null);
                        tv.setCompoundDrawablePadding(5);
                        tv.setBackground(getResources().getDrawable(R.drawable.chips_edittext_gb));
                        tv.setTextColor(getResources().getColor(R.color.textColor));
                        tv.setPadding(15, 10, 10, 5);
                        tv.setGravity(Gravity.CENTER);

                        tv.setText(io);
                        lt_coverage.addView(tv);

                        et_coverage.setText("");

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lt_coverage.removeView(v);
                                if (lt_coverage.getChildCount() == 0) {
                                    lt_coverage.setVisibility(View.GONE);
                                }
                            }
                        });

                        Log.e("tag", "count: " + lt_coverage.getChildCount());

                    }
                }


                return false;

            }
        });


        lt_id_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("tag", "permission Not granted");


                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else {
                    com.gun0912.tedpicker.Config config = new com.gun0912.tedpicker.Config();
                    config.setSelectionMin(1);
                    config.setSelectionLimit(1);
                    config.setCameraHeight(R.dimen.app_camera_height);
                    config.setCameraBtnBackground(R.drawable.round_rd);
                    config.setToolbarTitleRes(R.string.img_vec_lic);
                    config.setSelectedBottomHeight(R.dimen.bottom_height);
                    ImagePickerActivity.setConfig(config);
                    Intent intent = new Intent(getActivity(), com.gun0912.tedpicker.ImagePickerActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }

            }
        });


        lt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("tag", "permission Not granted");


                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else {
                    com.gun0912.tedpicker.Config config = new com.gun0912.tedpicker.Config();
                    config.setSelectionMin(1);
                    config.setSelectionLimit(1);
                    config.setCameraHeight(R.dimen.app_camera_height);
                    config.setCameraBtnBackground(R.drawable.round_rd);
                    config.setToolbarTitleRes(R.string.img_vec_lic_photo);
                    config.setSelectedBottomHeight(R.dimen.bottom_height);
                    ImagePickerActivity.setConfig(config);
                    Intent intent = new Intent(getActivity(), com.gun0912.tedpicker.ImagePickerActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PHOTO);
                }


            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = et_name.getText().toString().trim();
                str_address = et_address.getText().toString().trim();
                str_state = et_state.getText().toString().trim();
                str_city = et_city.getText().toString().trim();
                str_phone = et_phone.getText().toString().trim();
                str_email = et_email.getText().toString().trim();
                str_coverage = et_coverage.getText().toString().trim();
                str_bank = et_bank.getText().toString().trim();
                str_bank_no = et_bank_no.getText().toString().trim();
                str_coverage = et_coverage.getText().toString();


                if (!str_name.isEmpty() && str_name.length() > 4) {
                    if (!str_address.isEmpty() && str_address.length() > 4) {
                        if (!str_state.isEmpty()) {
                            if (!str_city.isEmpty()) {
                                if (!str_phone.isEmpty() && str_phone.length() > 9) {
                                    if (!str_email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                                        if (!str_bank_no.isEmpty() && str_bank_no.length() > 9) {
                                            if (str_id_card_photo != null) {
                                                if (str_photograph != null) {
                                                    if (cb_terms.isChecked()) {

                                                        new register_agent().execute();

                                                    } else {
                                                        snackbar.show();
                                                        tv_snack.setText("Please Read & Agree the Terms and Conditions");

                                                    }

                                                } else {
                                                    snackbar.show();
                                                    tv_snack.setText("Upload Passport size photograph");
                                                }
                                            } else {
                                                snackbar.show();
                                                tv_snack.setText("Upload State Issued ID Card");
                                            }
                                        } else {
                                            snackbar.show();
                                            tv_snack.setText("Enter Valid Account Number");
                                            et_bank_no.requestFocus();
                                        }
                                    } else {
                                        snackbar.show();
                                        tv_snack.setText("Enter Valid Email");
                                        et_email.requestFocus();
                                    }
                                } else {
                                    snackbar.show();
                                    tv_snack.setText("Enter Valid Phone Number");
                                }
                            } else {
                                snackbar.show();
                                tv_snack.setText("Choose City");
                            }

                        } else {
                            snackbar.show();
                            tv_snack.setText("Choose State");
                        }
                    } else {
                        snackbar.show();
                        tv_snack.setText("Enter Valid Address.");
                        et_address.requestFocus();
                    }
                } else {
                    snackbar.show();
                    tv_snack.setText("Enter Valid Name.");
                    et_name.requestFocus();
                }
            }
        });


        return get_RegisterView;
    }


    public void popup(final String[] ar_bank, final EditText et_data, final int type) {

        ar_banks_copy = ar_bank;


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_choose_bank, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        LinearLayout myRoot = (LinearLayout) dialogView.findViewById(R.id.layout_top);
        LinearLayout a = null;

        for (int i = 0; i < ar_banks_copy.length; i++) {

            a = new LinearLayout(getActivity());
            LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            para.setMargins(5, 5, 5, 5);
            a.setOrientation(LinearLayout.HORIZONTAL);
            a.setLayoutParams(para);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
            params.gravity = Gravity.CENTER;
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.button_change);
            imageView.setLayoutParams(params);
            TextView tss = new TextView(getActivity());
            tss.setText(ar_banks_copy[i]);
            LinearLayout.LayoutParams paramsQ = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsQ.gravity = Gravity.CENTER;
            tss.setLayoutParams(paramsQ);
            tss.setTextSize(18);
            tss.setTextColor(getResources().getColor(R.color.textColor));
            FontsManager.changeFonts(tss);

            View vres = new View(getActivity());
            LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            vres.setLayoutParams(paramss);
            vres.setBackgroundColor(getResources().getColor(R.color.viewColor));

            a.addView(imageView);
            a.addView(tss);
            myRoot.addView(a);
            if (i != ar_banks_copy.length - 1)
                myRoot.addView(vres);

            final int k = i;

            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("tag", "a:" + ar_banks_copy[k]);
                    b.dismiss();
                    String state = ar_banks_copy[k];
                    et_data.setText(state);
                }
            });
        }
        b.show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {


            image_uris = data.getParcelableArrayListExtra(com.gun0912.tedpicker.ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e("tag", "12345" + image_uris);
            if (image_uris != null) {
                str_id_card_photo = image_uris.get(0).toString();
                Glide.with(getActivity()).load(new File(str_id_card_photo)).centerCrop().into(iv_id_card);
            }

        } else if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {


            image_uris = data.getParcelableArrayListExtra(com.gun0912.tedpicker.ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e("tag", "12345" + image_uris);
            if (image_uris != null) {
                str_photograph = image_uris.get(0).toString();
                Glide.with(getActivity()).load(new File(str_photograph)).centerCrop().into(iv_photograph);
            }

        }

    }

//
    public class register_agent extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("tag", "driver_register");
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String json = "", jsonStr = "";

            try {

                String responseString = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(Config_Utils.WEB_URL + "agentsignup");

                httppost.setHeader("agent_name", str_name);
                httppost.setHeader("agent_mobile", str_mobile_prefix + str_phone);
                httppost.setHeader("agent_email",str_email);
                httppost.setHeader("agent_address", str_address);
                httppost.setHeader("agent_state", str_state);
                httppost.setHeader("agent_city", str_city);
                httppost.setHeader("agent_region", str_coverage);
                httppost.setHeader("agent_bank", str_bank);
                httppost.setHeader("agent_account_no", str_bank_no);



                try {

                    MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                    File sourceFile = new File(str_id_card_photo);
                    Log.e("tagtag3", "" + sourceFile);
                    entity.addPart("agentimage", new FileBody(sourceFile, "image/jpeg"));
                    entity.addPart("agentidentity", new FileBody(sourceFile, "image/jpeg"));
                    httppost.setEntity(entity);
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();
                    //Log.e("tagurl", "ur:" + Config.WEB_URL + "driversignup");
                    Log.e("tag", "headers:" + httppost.getAllHeaders().toString());
                    int statusCode = response.getStatusLine().getStatusCode();
                    Log.e("tagtag", response.getStatusLine().toString());
                    if (statusCode == 200) {
                        responseString = EntityUtils.toString(r_entity);
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }
                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                    Log.e("tagerr0: ", e.toString());
                } catch (IOException e) {
                    responseString = e.toString();
                    Log.e("tagerr1: ", e.toString());
                }
                return responseString;


            } catch (Exception e) {
                Log.e("tagerr2:", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("tag", "tagtag" + s);

           mProgressDialog.dismiss();

            if (s != null) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);

                    if (status.equals("true")) {
                        dialog_success.show();
                    } else {
                        if (msg.contains("Error OccuredError: ER_DUP_ENTRY: Duplicate entry")) {
                            snackbar.show();
                            tv_snack.setText("User Already Registerd");
                        } else {
                            snackbar.show();
                            tv_snack.setText("No network found");
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("tag", "tagnt: " + e.toString());

                    snackbar.show();
                    tv_snack.setText("No network found");
                }
            } else {
                snackbar.show();
                tv_snack.setText("No network found");
            }
        }
    }

}
