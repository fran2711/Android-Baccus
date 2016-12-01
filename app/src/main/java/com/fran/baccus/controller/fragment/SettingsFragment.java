package com.fran.baccus.controller.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.fran.baccus.R;
import com.fran.baccus.controller.activity.SettingsActivity;


public class SettingsFragment extends DialogFragment implements View.OnClickListener {

    public static final String ARG_WINE_IMAGE_SCALE_TYPE = "com.fran.baccus.controller.fragment.SettingsActivity.ARG_WINE_IMAGE_SCALE_TYPE";
    public static final String PREF_IMAGE_SCALE_TYPE = "imageScaleType";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.action_settings);
        return dialog;
    }

    // Vistas
    private RadioGroup mRadioGroup = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        mRadioGroup = (RadioGroup) root.findViewById(R.id.scale_type_radios);

        if (getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ImageView.ScaleType.FIT_XY)) {
            mRadioGroup.check(R.id.fit_radio);
        }
        else if (getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ImageView.ScaleType.FIT_CENTER)) {
            mRadioGroup.check(R.id.center_radio);
        }

        Button cancelButton = (Button) root.findViewById(R.id.cancel_button);
        Button saveButton = (Button) root.findViewById(R.id.save_button);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        return root;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                cancelSettings();
                break;
            case R.id.save_button:
                saveSettings();
                break;
        }
    }

    private void saveSettings() {
        ImageView.ScaleType selectedScaleType = null;

        Intent config = new Intent();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();

        if (mRadioGroup.getCheckedRadioButtonId() == R.id.fit_radio) {
            selectedScaleType = ImageView.ScaleType.FIT_XY;
        } else if (mRadioGroup.getCheckedRadioButtonId() == R.id.center_radio) {
            selectedScaleType = ImageView.ScaleType.FIT_CENTER;
        }

        config.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, selectedScaleType);
        editor.putString(PREF_IMAGE_SCALE_TYPE, selectedScaleType.toString());

        editor.commit();

        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, config);
            dismiss();
        } else {
            getActivity().setResult(Activity.RESULT_OK, config);
            getActivity().finish();
        }
    }

    private void cancelSettings() {

        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
            dismiss();
        }
        else {
            getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }
    }
}
