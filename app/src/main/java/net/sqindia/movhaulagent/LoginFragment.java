package net.sqindia.movhaulagent;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

/**
 * Created by Salman on 23-05-2017.
 */

public class LoginFragment extends Fragment {

    TextView tv_header_txt,tv_register,tv_admin;
    TextInputLayout til_phone,til_user_name,til_password;
    Typeface tf;
    LinearLayout lt_agent,lt_admin,lt_bottom;
    Button btn_submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View get_LoginView = inflater.inflate(R.layout.login_fragment, container, false);
        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_LoginView);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/lato.ttf");


        tv_header_txt = (TextView)getActivity().findViewById(R.id.textview_header);

        lt_agent = (LinearLayout) get_LoginView.findViewById(R.id.agent_layout);
        lt_admin = (LinearLayout) get_LoginView.findViewById(R.id.admin_layout);
        til_phone = (TextInputLayout) get_LoginView.findViewById(R.id.textinput_number);
        til_user_name = (TextInputLayout) get_LoginView.findViewById(R.id.textinput_username);
        til_password = (TextInputLayout) get_LoginView.findViewById(R.id.textinput_password);
        tv_register = (TextView) get_LoginView.findViewById(R.id.textview_register);
        btn_submit = (Button) get_LoginView.findViewById(R.id.button_submit);

        tv_admin = (TextView) get_LoginView.findViewById(R.id.textview_admin);
        lt_bottom = (LinearLayout) get_LoginView.findViewById(R.id.bottom);


        til_phone.setTypeface(tf);





        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("tag","Landscape");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT); // or wrap_content
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            lt_bottom.setLayoutParams(layoutParams);
        }
        else {
            Log.e("tag","Portrait");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT); // or wrap_content
            layoutParams.addRule(RelativeLayout.BELOW,R.id.layout_login);
            lt_bottom.setLayoutParams(layoutParams);
        }

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new RegisterFragment());
                ft.commit();
                tv_header_txt.setText(getString(R.string.register));
            }
        });

        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_admin.getText().toString().equals("Change to Admin")){
                    tv_admin.setText("Change to Agent");
                    lt_agent.setVisibility(View.GONE);
                    lt_admin.setVisibility(View.VISIBLE);
                    tv_register.setVisibility(View.GONE);
                }
                else{
                    tv_admin.setText("Change to Admin");
                    lt_agent.setVisibility(View.VISIBLE);
                    lt_admin.setVisibility(View.GONE);
                    tv_register.setVisibility(View.VISIBLE);
                }
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDash  = new Intent(getActivity(),Dashboard.class);
                getActivity().startActivity(goDash);
            }
        });


        return get_LoginView;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
