package com.movhaul.agent.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.movhaul.agent.Model.Config_Utils;
import com.movhaul.agent.R;

import org.jsoup.Jsoup;

/**
 * Created by Salman on 04-07-2017.
 */

public class Splash extends Activity {


    String currentVersion, playstoreVersion;
    Dialog dg_show_update;
    TextView tv_dg_txt, tv_dg_txt2;
    Button btn_dg_download;
    Typeface tf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        tf = Typeface.createFromAsset(getAssets(), "fonts/lato.ttf");

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.e("Tag", "version:" + currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e("Tag", "err:" + e.toString());
        }

        if (!Config_Utils.isConnected(Splash.this)) {
            Log.e("Tag", "err:" + "No network");
            Toast.makeText(getApplicationContext(),"No network found,Please try again later",Toast.LENGTH_SHORT).show();
        } else {
            //new GetVersionCode().execute();
        }

        dg_show_update = new Dialog(Splash.this);
        dg_show_update.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dg_show_update.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dg_show_update.setCancelable(false);
        dg_show_update.setContentView(R.layout.dialog_road_confirm);

        btn_dg_download = (Button) dg_show_update.findViewById(R.id.button_yes);
        tv_dg_txt = (android.widget.TextView) dg_show_update.findViewById(R.id.textView_1);
        tv_dg_txt2 = (android.widget.TextView) dg_show_update.findViewById(R.id.textView_2);

        tv_dg_txt.setText("Hooray...!!!");
        tv_dg_txt2.setText("New Update available on PlayStore");
        btn_dg_download.setText("Download");

        tv_dg_txt.setTypeface(tf);
        tv_dg_txt2.setTypeface(tf);
        btn_dg_download.setTypeface(tf);

        btn_dg_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dg_show_update.dismiss();

                final String appPackageName = Splash.this.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

            }
        });


    }


    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {

                Log.e("tag", "https://play.google.com/store/apps/details?id=" + Splash.this.getPackageName() + "&hl=it");

                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + Splash.this.getPackageName() + "&hl=it").timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();

                Log.e("Tag", "new: " + newVersion);

                return newVersion;

            } catch (Exception e) {

                Log.e("Tag", "dreerr: " + e.toString());
                return newVersion;

            }

        }


        @Override

        protected void onPostExecute(String onlineVersion) {


            Log.e("Tag", "OUTPUT: " + onlineVersion);

            super.onPostExecute(onlineVersion);

            playstoreVersion = onlineVersion;

            if (playstoreVersion != null && !playstoreVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(playstoreVersion)) {

                    //show dialog
                    dg_show_update.show();

                }
                else{
                    Intent goDash = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(goDash);
                    finish();
                }

            }

            Log.e("update", "Current version " + currentVersion + "playstore version " + playstoreVersion);

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!Config_Utils.isConnected(Splash.this)) {
            Log.e("Tag", "err:" + "No network");
            Toast.makeText(getApplicationContext(),"No network found,Please try again later",Toast.LENGTH_SHORT).show();
        } else {
            new GetVersionCode().execute();
        }

    }
}
