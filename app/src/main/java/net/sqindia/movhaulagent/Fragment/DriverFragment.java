package net.sqindia.movhaulagent.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.hbb20.CountryCodePicker;
import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman on 23-05-2017.
 */

public class DriverFragment extends Fragment {
    TextView tv_activity_header;
    LinearLayout lt_service_range;
    TextInputLayout til_name,til_address,til_state,til_city,til_phone,til_email,til_bank,til_service_type,til_loc_govt;
    CountryCodePicker ccp_driver;
    Typeface tf;
    Button btn_submit;

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




        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("tag","Landscape");
            lt_service_range.setOrientation(LinearLayout.HORIZONTAL);
        }
        else {
            Log.e("tag","Portrait");
            lt_service_range.setOrientation(LinearLayout.VERTICAL);
        }

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



}
