package net.sqindia.movhaulagent.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Salman on 23-05-2017.
 */

public class DriverFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 33;
    TextView tv_activity_header;
    LinearLayout lt_service_range;
    TextInputLayout til_name,til_address,til_state,til_city,til_phone,til_email,til_bank,til_service_type,til_loc_govt;
    CountryCodePicker ccp_driver;
    Typeface tf;
    Button btn_submit;
    Snackbar snackbar;
    TextView tv_snack;
    String str_name, str_address, str_state, str_city, str_phone, str_email,str_bank,str_bank_no,str_service_type,str_service_route,str_service_range,str_govt,str_services_areas,str_driving_licence;
    EditText et_name,et_address,et_state,et_city,et_phone,et_email,et_bank,et_bank_no,et_service_type,et_coverage,et_govt;
    LinearLayout lt_state, lt_city, lt_bank, lt_service_type,lt_coverage,lt_driving_licence;

    private static final int REQUEST_CODE_DRIVING_LICENCE = 20;
    public String[] ar_banks;
    public String[] ar_state;
    public String[] ar_city;
    public String[] ar_banks_copy;
    public String[] ar_service_type;
    Config_Utils config;
    public HashMap<String, String[]> city_hash = new HashMap<>();
    ArrayList<Uri> image_uris;
    ImageView iv_driving_licence;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View get_DriverView = inflater.inflate(R.layout.driver_fragment, container, false);

        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_DriverView);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lato.ttf");

        lt_service_range = (LinearLayout) get_DriverView.findViewById(R.id.layout_service_range);
        tv_activity_header = (TextView)getActivity().findViewById(R.id.textview_header);
        tv_activity_header.setText("Driver");
        Log.e("tag","fragment_created");

        config = new Config_Utils();

        city_hash = config.getCity_hash();

        ar_banks = new String[]{" GT Bank ", " Hongkong&Sangai Bank ", " SAFC ", " Bank Of Africa ", "Federal Bank of Nigeria", " LEKIA Bank ", " Nigeria Bank ",};
        ar_state = new String[]{"Abia", "Akwa Ibom", "Benue", "Borno", "Delta", "Enugu", "Edo", "Jigawa", "Kebbi", "Lagos", "Ogun", "Oyo", "Rivers", "Yobe"};
        ar_city = new String[]{"Asaba", "Bauchi", "Dutse", "Jimeta", "Kanduna", "Lafia", "Lekki", "Oron", "Port Harcourt", "Sokoto", "Warri", "Zaria"};
        ar_service_type = new String[]{"Truck Services","Charter Bus Services","Road Side Assistance"};



        btn_submit = (Button) get_DriverView.findViewById(R.id.button_submit);
        til_name = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_username);
        til_address = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_address);
        til_state = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_state);
        til_city = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_city);
        til_phone = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_phone);
        til_email = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_email);
        til_bank = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_bank);
        til_service_type = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_type);
        til_loc_govt = (TextInputLayout) get_DriverView.findViewById(R.id.textinput_loc_govt);
        ccp_driver = (CountryCodePicker) get_DriverView.findViewById(R.id.ccp_driver);
        iv_driving_licence = (ImageView) get_DriverView.findViewById(R.id.imageview_licence);
        lt_state = (LinearLayout) get_DriverView.findViewById(R.id.layout_choose_state);
        lt_city = (LinearLayout) get_DriverView.findViewById(R.id.layout_choose_city);
        lt_bank = (LinearLayout) get_DriverView.findViewById(R.id.layout_choose_bank);
        lt_service_type = (LinearLayout) get_DriverView.findViewById(R.id.layout_choose_service_type);
        lt_coverage = (LinearLayout) get_DriverView.findViewById(R.id.layout_coverage);
        lt_driving_licence = (LinearLayout) get_DriverView.findViewById(R.id.layout_upload_licence);

        et_name = (EditText) get_DriverView.findViewById(R.id.edittext_name);
        et_address = (EditText) get_DriverView.findViewById(R.id.edittext_address);
        et_phone = (EditText) get_DriverView.findViewById(R.id.edittext_phone);
        et_email = (EditText) get_DriverView.findViewById(R.id.edittext_email);
        et_bank_no = (EditText) get_DriverView.findViewById(R.id.edittext_bank_no);
        et_bank = (EditText) get_DriverView.findViewById(R.id.edittext_bank);
        et_state = (EditText) get_DriverView.findViewById(R.id.edittext_state);
        et_city = (EditText) get_DriverView.findViewById(R.id.edittext_city);
        et_coverage = (EditText) get_DriverView.findViewById(R.id.edittext_coverage);
        et_govt = (EditText) get_DriverView.findViewById(R.id.edittext_govt);
        et_service_type = (EditText) get_DriverView.findViewById(R.id.edittext_service_type);

        til_name.setTypeface(tf);
        til_address.setTypeface(tf);
        til_state.setTypeface(tf);
        til_city.setTypeface(tf);
        til_phone.setTypeface(tf);
        til_email.setTypeface(tf);
        til_bank.setTypeface(tf);
        til_service_type.setTypeface(tf);
        til_loc_govt.setTypeface(tf);
        ccp_driver.setTypeFace(tf);

        snackbar = Snackbar
                .make(getActivity().findViewById(R.id.top), "No NetWork", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        tv_snack = (android.widget.TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv_snack.setTextColor(Color.WHITE);
        tv_snack.setTypeface(tf);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            lt_service_range.setOrientation(LinearLayout.HORIZONTAL);
        }
        else {
            lt_service_range.setOrientation(LinearLayout.VERTICAL);
        }

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

        et_bank.setText("GT Bank");
        lt_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(ar_banks, et_bank, 2);
            }
        });

        lt_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(config.states, et_state, 0);
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

        lt_service_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(ar_service_type, et_service_type, 3);
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


        lt_driving_licence.setOnClickListener(new View.OnClickListener() {
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
                    startActivityForResult(intent, REQUEST_CODE_DRIVING_LICENCE);
                }

            }
        });














        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity().getFragmentManager().popBackStack();
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);




            }
        });

        return get_DriverView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("tag","fragment_save_instant");
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
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE_DRIVING_LICENCE) {


            image_uris = data.getParcelableArrayListExtra(com.gun0912.tedpicker.ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e("tag", "12345" + image_uris);
            if (image_uris != null) {
                str_driving_licence = image_uris.get(0).toString();
                Glide.with(getActivity()).load(new File(str_driving_licence)).centerCrop().into(iv_driving_licence);
            }

        }
    }



}
