package com.ellenluo.minimaList;

/*
 * TimePickerFragment
 * Created by Ellen Luo
 * DialogFragment that displays a time picker.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    OnTimeSetListener listener;

    // interface
    public interface OnTimeSetListener extends TimePickerDialog.OnTimeSetListener {
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // set current time
        final Calendar c = Calendar.getInstance();
        int initHour = c.get(Calendar.HOUR_OF_DAY);
        int initMin = c.get(Calendar.MINUTE);

        // get notification settings from preferences
        SharedPreferences prefSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean militaryTime = prefSettings.getBoolean("24h", false);

        return new TimePickerDialog(getActivity(), listener, initHour, initMin, militaryTime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnTimeSetListener) activity;
    }

}

