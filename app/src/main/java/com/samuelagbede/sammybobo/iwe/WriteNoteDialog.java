package com.samuelagbede.sammybobo.iwe;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.samuelagbede.sammybobo.iwe.MainActivity.InitEditText;
import static com.samuelagbede.sammybobo.iwe.MainActivity.InitTextView;

/**
 * Created by Agbede on 26/08/2016.
 */

public class WriteNoteDialog extends DialogFragment {

    public interface DialogEvents{
        public void onSave(DialogFragment dialogFragment);
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
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.write_new_text, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancel", null).create();

        builder.setView(view);




        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //View view = getLayoutInflater().inflate(R.layout.settings, null, false);
        EditText editText =(EditText)view.findViewById(R.id.new_text);
        TextView textView = (TextView)view.findViewById(R.id.text_error);

        String fonts = SharedPrefSingleton.getInstance(getContext()).preferenceGetFontString("font_name");
        InitTextView(getContext(), textView, fonts);
        InitEditText(getActivity().getApplicationContext(), editText, fonts);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEvents.onSave(WriteNoteDialog.this);
            }
        });

        return alertDialog;
    }


   // return super.onCreateDialog(savedInstanceState);
    }

