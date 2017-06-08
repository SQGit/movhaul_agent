package net.sqindia.movhaulagent.Class;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.sloop.fonts.FontsManager;

import net.sqindia.movhaulagent.Fragment.LoginFragment;
import net.sqindia.movhaulagent.R;

/**
 * Created by Salman on 23-05-2017.
 */

public class LoginRegister extends AppCompatActivity {

    boolean bl_fragment;
    LinearLayout lt_action_back;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity);

        FontsManager.initFormAssets(LoginRegister.this, "fonts/lato.ttf");
        FontsManager.changeFonts(LoginRegister.this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginRegister.this);
        editor = sharedPreferences.edit();

        lt_action_back  = (LinearLayout) findViewById(R.id.action_back);
        lt_action_back.setVisibility(View.GONE);

        if(sharedPreferences.getString("login","").equals("success")){
            Intent i = new Intent(LoginRegister.this, Dashboard.class);
            startActivity(i);
            finish();
        }







        FragmentManager manager = getSupportFragmentManager();
        int index = manager.getBackStackEntryCount() - 1;

      //  FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        //String tag = backEntry.getName();
        Log.e("tag","activity "+index);
        if(index < 0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new LoginFragment(), "login");
            ft.addToBackStack("login");
            Log.e("tag","activity_stack"+getSupportFragmentManager().getBackStackEntryCount());
            ft.commit();
        }


        lt_action_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new LoginFragment(),"login");
                ft.addToBackStack("login");
                ft.commit();
                lt_action_back.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }






    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("tag","save_Instant");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("tag","activity_restore_instant");

   /*     if(getSupportFragmentManager().getBackStackEntryCount()>0) {
            if (getSupportFragmentManager().findFragmentByTag("login").isVisible())
                lt_action_back.setVisibility(View.GONE);
            else
                lt_action_back.setVisibility(View.VISIBLE);
        }*/

        FragmentManager manager = getSupportFragmentManager();
        int index = manager.getBackStackEntryCount() - 1;

        if(index>0){
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();
            if(tag.equals("login")){
                lt_action_back.setVisibility(View.GONE);
            }
            else{
                lt_action_back.setVisibility(View.VISIBLE);
            }
        }


    /*    FragmentManager manager = getSupportFragmentManager();
        Log.e("tag","acts: "+manager.getBackStackEntryCount());
        Log.e("tag","acts33: "+manager.getFragments());
        Log.e("tag","acts77: "+savedInstanceState.toString());

        int index = manager.getBackStackEntryCount() - 1;
        Log.e("tag","fragment_index: "+index);
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();
        Log.e("tag","fragment: "+tag);
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);*/

      /*  FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate ("register", 0);



        Log.e("tag","acts: "+manager.getBackStackEntryCount());

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fragment_container, new LoginFragment(),"login");
            ft.addToBackStack("login");
            ft.commit();
           // lt_action_back.setVisibility(View.GONE);
        }*/
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
