package net.sqindia.movhaulagent.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.Class.Dashboard;
import net.sqindia.movhaulagent.R;

/**
 * Created by Salman on 23-05-2017.
 */

public class CompanyFragment extends Fragment {

    TextInputLayout til_company,til_person,til_phone,til_email,til_reg_id,til_address;
    Typeface tf;
    Button btn_submit;
    TextView tv_activity_header;
    LinearLayout lt_bottom;
    ScrollView scr_top;
    CountryCodePicker ccp_company;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View get_CompanyView = inflater.inflate(R.layout.company_fragment, container, false);
        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_CompanyView);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lato.ttf");

        btn_submit = (Button) get_CompanyView.findViewById(R.id.button_submit);
        tv_activity_header = (TextView)getActivity().findViewById(R.id.textview_header);
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

        til_company.setTypeface(tf);
        til_phone.setTypeface(tf);
        til_person.setTypeface(tf);
        til_email.setTypeface(tf);
        til_address.setTypeface(tf);
        til_reg_id.setTypeface(tf);
        ccp_company.setTypeFace(tf);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Log.e("tag","login_Landscape");
         /*   RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // or wrap_content
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // or wrap_content
            layoutParams1.addRule(RelativeLayout.ABOVE,R.id.bottom);
            lt_bottom.setLayoutParams(layoutParams);
            scr_top.setLayoutParams(layoutParams1);*/
        }
        else {
            //Log.e("tag","login_Portrait");
            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT); // or wrap_content
            //layoutParams.addRule(RelativeLayout.BELOW,R.id.layout_company);
           // lt_bottom.setLayoutParams(layoutParams);
           // lt_bottom.setPadding(0,0,0,30);

        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getActivity().getFragmentManager().popBackStack();
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        return get_CompanyView;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
