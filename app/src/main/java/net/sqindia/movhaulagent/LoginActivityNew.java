package net.sqindia.movhaulagent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.sloop.fonts.FontsManager;

/**
 * Created by Salman on 23-05-2017.
 */

public class LoginActivityNew extends AppCompatActivity {

    Typeface tf;
    TextInputLayout til_login_phone, til_reg_name, til_reg_email, til_reg_bank, til_reg_address, til_reg_phone, til_reg_region;
    TextView tv_register,tv_admin;
    int admin_login_sts;
    LoginFragment loginfrag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        FontsManager.initFormAssets(LoginActivityNew.this, "fonts/lato.ttf");
        FontsManager.changeFonts(LoginActivityNew.this);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/lato.ttf");

    /*    til_login_phone = (TextInputLayout) findViewById(R.id.textinputlayout_login_phone);

        tv_register = (TextView) findViewById(R.id.textview_register);
        tv_admin = (TextView) findViewById(R.id.textview_admin);

        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_admin.getText().toString().equals(getString(R.string.chang_to_admin))){

                    tv_admin.setText(R.string.chang_to_agent);
                }
                else{
                    tv_admin.setText(getString(R.string.chang_to_admin));
                }
            }
        });


        til_login_phone.setTypeface(tf);*/




        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new LoginFragment());
        ft.commit();












    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
