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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
    private static final int REQUEST_CODE =10 ;
    private static final int REQUEST_CODE_PHOTO = 20;
    ArrayList<Uri> image_uris;
    String str_id_card_photo,str_photograph;
    ImageView iv_id_card,iv_photograph;
    TextView tv_activity_header;
    LinearLayout lt_service_range;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View get_DriverView = inflater.inflate(R.layout.driver_fragment, container, false);

        FontsManager.initFormAssets(getActivity(), "fonts/lato.ttf");
        FontsManager.changeFonts(get_DriverView);

        lt_service_range = (LinearLayout) get_DriverView.findViewById(R.id.layout_service_range);
        tv_activity_header = (TextView)getActivity().findViewById(R.id.textview_header);
        tv_activity_header.setText("Driver");
        Log.e("tag","fragment_created");

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("tag","Landscape");
            lt_service_range.setOrientation(LinearLayout.HORIZONTAL);
        }
        else {
            Log.e("tag","Portrait");
            lt_service_range.setOrientation(LinearLayout.VERTICAL);
        }

        return get_DriverView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("tag","fragment_save_instant");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {


            image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e("tag", "12345" + image_uris);
            if (image_uris != null) {
                str_id_card_photo = image_uris.get(0).toString();
                Glide.with(getActivity()).load(new File(str_id_card_photo)).centerCrop().into(iv_id_card);
            }

        }

        else if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {


            image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            Log.e("tag", "12345" + image_uris);
            if (image_uris != null) {
                str_photograph = image_uris.get(0).toString();
                Glide.with(getActivity()).load(new File(str_photograph)).centerCrop().into(iv_photograph);
            }

        }

    }

}
