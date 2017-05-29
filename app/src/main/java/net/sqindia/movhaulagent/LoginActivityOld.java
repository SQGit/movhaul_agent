package net.sqindia.movhaulagent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.sloop.fonts.FontsManager;




public class LoginActivityOld extends AppCompatActivity {

    public static String[] ar_banks;
    public String str_state = "state", str_district = "dist";
    Typeface tf;
    TextInputLayout til_login_phone, til_reg_name, til_reg_email, til_reg_bank, til_reg_address, til_reg_phone, til_reg_region;
    TextView tv_header, tv_agent_reg, tv_admin, tv_agent_login;
    View view_horiz;
    LinearLayout lt_agent_reg, lt_agent_login, lt_chip_layout, lt_agent_reg_2, lt_agent_register, lt_choose_bank, lt_choose_region;
    EditText et_chipedit, et_bank, et_region;
    Button btn_reg_next;
    CountryCodePicker ccp_login, ccp_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        FontsManager.initFormAssets(LoginActivityOld.this, "fonts/lato.ttf");
        FontsManager.changeFonts(LoginActivityOld.this);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/lato.ttf");

        til_login_phone = (TextInputLayout) findViewById(R.id.textinputlayout_login_phone);
        tv_header = (TextView) findViewById(R.id.textview_header);
        tv_agent_reg = (TextView) findViewById(R.id.textview_register);
        tv_agent_login = (TextView) findViewById(R.id.textview_login);
        tv_admin = (TextView) findViewById(R.id.textview_admin);
        view_horiz = findViewById(R.id.view_horiz);

        lt_agent_login = (LinearLayout) findViewById(R.id.layout_login);
        lt_agent_register = (LinearLayout) findViewById(R.id.layout_register);
        lt_agent_reg = (LinearLayout) findViewById(R.id.layout_register_first);
        lt_agent_reg_2 = (LinearLayout) findViewById(R.id.layout_register_second);
        btn_reg_next = (Button) findViewById(R.id.button_register_next);
        et_chipedit = (EditText) findViewById(R.id.edittext_chiptxt);
        et_bank = (EditText) findViewById(R.id.edittext_bank);
        et_region = (EditText) findViewById(R.id.edittext_region);
        lt_chip_layout = (LinearLayout) findViewById(R.id.layout_new);
        lt_choose_bank = (LinearLayout) findViewById(R.id.layout_select_bank);
        lt_choose_region = (LinearLayout) findViewById(R.id.layout_choose_region);

        ccp_login = (CountryCodePicker) findViewById(R.id.ccp_login);
        ccp_register = (CountryCodePicker) findViewById(R.id.ccp_register);

        til_reg_name = (TextInputLayout) findViewById(R.id.textinputlayout_register_username);
        til_reg_phone = (TextInputLayout) findViewById(R.id.textinputlayout_register_phone);
        til_reg_email = (TextInputLayout) findViewById(R.id.textinputlayout_register_email);
        til_reg_bank = (TextInputLayout) findViewById(R.id.textinputlayout_register_bank);
        til_reg_address = (TextInputLayout) findViewById(R.id.textinputlayout_register_address);
        til_reg_region = (TextInputLayout) findViewById(R.id.textinputlayout_register_region);

        lt_agent_register.setVisibility(View.GONE);

        til_login_phone.setTypeface(tf);
        til_reg_name.setTypeface(tf);
        til_reg_phone.setTypeface(tf);
        til_reg_email.setTypeface(tf);
        til_reg_bank.setTypeface(tf);
        til_reg_address.setTypeface(tf);
        til_reg_region.setTypeface(tf);
        ccp_login.setTypeFace(tf);
        ccp_register.setTypeFace(tf);

        ar_banks = new String[]{" HSBC ", " London&Sangai Bank ", " SAFC ", " Bank Of Africa ", "Federal Bank of Nigeria", " LEKIA Bank ", " Nigeria Bank ",};


        et_chipedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    lt_chip_layout.setVisibility(View.VISIBLE);

                    String io = et_chipedit.getText().toString();


                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lparams.setMargins(10, 10, 10, 10);
                    TextView tv = new TextView(LoginActivityOld.this);
                    tv.setLayoutParams(lparams);

                    Drawable img = getResources().getDrawable(R.drawable.close);
                    img.setBounds(0, 0, 30, 30);
                    tv.setCompoundDrawables(null, null, img, null);

                    //tv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.close, 0);
                    //  tv.setCompoundDrawables(null,null,getResources().getDrawable(R.mipmap.close),null);
                    tv.setCompoundDrawablePadding(10);
                    tv.setBackground(getResources().getDrawable(R.drawable.chips_edittext_gb));
                    tv.setTextColor(getResources().getColor(R.color.colorAccent));
                    tv.setPadding(10, 10, 10, 10);

                    tv.setText(io);
                    lt_chip_layout.addView(tv);

                    et_chipedit.setText("");

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lt_chip_layout.removeView(v);
                            if (lt_chip_layout.getChildCount() == 0) {
                                lt_chip_layout.setVisibility(View.GONE);
                            }
                        }
                    });

                    Log.e("tag", "count: " + lt_chip_layout.getChildCount());


                }


                return false;

            }
        });


        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_admin.getText().toString().equals("Admin Module")) {
                    tv_header.setText("Admin Login");
                    tv_agent_reg.setVisibility(View.GONE);
                    view_horiz.setVisibility(View.GONE);
                    tv_admin.setText("Agent Module");
                } else {
                    tv_header.setText("Agent Login");
                    tv_agent_reg.setVisibility(View.VISIBLE);
                    view_horiz.setVisibility(View.VISIBLE);
                    tv_admin.setText("Admin Module");
                }
            }
        });

        tv_agent_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lt_agent_login.setVisibility(View.GONE);
                lt_agent_register.setVisibility(View.VISIBLE);
                lt_agent_reg.setVisibility(View.VISIBLE);
                lt_agent_reg_2.setVisibility(View.GONE);
                btn_reg_next.setText("Next");

            }
        });

        tv_agent_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lt_agent_login.setVisibility(View.VISIBLE);
                lt_agent_register.setVisibility(View.GONE);

            }
        });

        btn_reg_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_reg_next.getText().toString().equals("Next")) {
                    lt_agent_reg_2.setVisibility(View.VISIBLE);
                    lt_agent_reg.setVisibility(View.GONE);
                    btn_reg_next.setText("Submit");
                } else {

                }
            }
        });

        lt_choose_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivityOld.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_choose_bank, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog b = dialogBuilder.create();
                LinearLayout myRoot = (LinearLayout) dialogView.findViewById(R.id.layout_top);
                LinearLayout a = null;

                for (int i = 0; i < ar_banks.length; i++) {

                    a = new LinearLayout(LoginActivityOld.this);
                    LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    para.setMargins(5, 20, 5, 20);
                    a.setOrientation(LinearLayout.HORIZONTAL);
                    a.setLayoutParams(para);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
                    params.gravity = Gravity.CENTER;
                    ImageView imageView = new ImageView(LoginActivityOld.this);
                    imageView.setImageResource(R.drawable.button_change);
                    imageView.setLayoutParams(params);
                    TextView tss = new TextView(LoginActivityOld.this);
                    tss.setText(ar_banks[i]);
                    LinearLayout.LayoutParams paramsQ = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    paramsQ.gravity = Gravity.CENTER;
                    tss.setLayoutParams(paramsQ);
                    tss.setTextSize(16);
                    tss.setTextColor(getResources().getColor(R.color.textColor));
                    tss.setTypeface(tf);

                    View vres = new View(LoginActivityOld.this);
                    LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    vres.setLayoutParams(paramss);
                    vres.setBackgroundColor(getResources().getColor(R.color.viewColor));

                    a.addView(imageView);
                    a.addView(tss);
                    myRoot.addView(a);
                    myRoot.addView(vres);

                    final int k = i;

                    a.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("tag", "a:" + ar_banks[k]);
                            b.dismiss();
                            et_bank.setText(ar_banks[k]);
                        }
                    });
                }
                b.show();


            }
        });

        lt_choose_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("tag", "click workde");

         /*       Dialog_Region dialog_region = new Dialog_Region(LoginActivityOld.this);
                dialog_region.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                dialog_region.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_region.show();
                //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                dialog_region.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        Log.e("tag", "zz0 " + str_state + str_district);

                        et_region.setText(str_state + " - " + str_district);


                    }
                });*/


            }
        });

    }


    @Override
    public void onBackPressed() {

        if (lt_agent_register.getVisibility() == View.VISIBLE) {
            if (lt_agent_reg_2.getVisibility() == View.VISIBLE) {
                lt_agent_reg.setVisibility(View.VISIBLE);
                lt_agent_reg_2.setVisibility(View.GONE);
            } else {
                lt_agent_login.setVisibility(View.VISIBLE);
                lt_agent_register.setVisibility(View.GONE);
            }
        } else {
            super.onBackPressed();
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
}
