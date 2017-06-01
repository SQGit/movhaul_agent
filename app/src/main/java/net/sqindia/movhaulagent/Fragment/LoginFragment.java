package net.sqindia.movhaulagent.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.Class.Dashboard;
import net.sqindia.movhaulagent.R;

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
    String str_phone, str_username, str_password;
    Snackbar snackbar;


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

        snackbar = Snackbar
                .make(getActivity().findViewById(R.id.top), "No NetWork", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        tv_snack = (android.widget.TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv_snack.setTextColor(Color.WHITE);
        tv_snack.setTypeface(tf);


        til_phone.setTypeface(tf);
        til_user_name.setTypeface(tf);
        til_password.setTypeface(tf);

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
                            Intent goDash = new Intent(getActivity(), Dashboard.class);
                            getActivity().startActivity(goDash);
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
                                    snackbar.show();
                                    tv_snack.setText("Login Success");
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
}
