package com.samuelagbede.sammybobo.iwe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


/**
 * Created by Agbede on 14/10/2016.
 */

public class Activity_Settings extends DialogFragment {

    AppCompatSpinner font_size, font_name, theme_name;
    SharedPrefSingleton sharedPrefSingleton;
    View view;

    public interface DialogEvents
    {
        public void onSettings(DialogFragment dialogFragment);
    }

    DialogEvents dialogEvents;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            dialogEvents = (DialogEvents)context;
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        LayoutInflater inflater = getActivity().getLayoutInflater();
        sharedPrefSingleton = SharedPrefSingleton.getInstance(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.settings, null, false);
        builder.setView(view);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("Cancel", null);

        font_name = (AppCompatSpinner)view.findViewById(R.id.font_name);
        font_size = (AppCompatSpinner)view.findViewById(R.id.font_size);

        final String [] font_names = {"Quicksand", "Raleway", "LobsterTwo", "Default"};
        final String [] font_sizes = { "18","20","22", "24", "26"};

        ArrayAdapter fontAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, font_names );
        ArrayAdapter fontSizeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, font_sizes);
        font_name.setAdapter(fontAdapter);
        font_size.setAdapter(fontSizeAdapter);

        String name_font = sharedPrefSingleton.preferenceGetFontString("font_name");
        int name_font_position = fontAdapter.getPosition(name_font);
        font_name.setSelection(name_font_position);

        String fontsize = sharedPrefSingleton.preferenceGetInteger("font_size")+"";
        int size_font_position = fontSizeAdapter.getPosition(fontsize);
        font_size.setSelection(size_font_position);


        font_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sharedPrefSingleton.preferencePutString("font_name", font_names[i]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        font_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                sharedPrefSingleton.preferencePutInteger("font_size", Integer.parseInt(font_sizes[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEvents.onSettings(Activity_Settings.this);
            }});

        return alertDialog;
    }
}
