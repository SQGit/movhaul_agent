package net.sqindia.movhaulagent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

/**
 * Created by Salman on 23-05-2017.
 */

public class CompanyFragment extends Fragment {

    TextInputLayout til_phone;
    Typeface tf;
    Button btn_submit;
    TextView tv_activity_header;

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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDash  = new Intent(getActivity(),Dashboard.class);
                getActivity().startActivity(goDash);
            }
        });


        return get_CompanyView;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
