package com.samuelagbede.sammybobo.iwe;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Agbede on 31/08/2016.
 */

public class ListAdapter extends ArrayAdapter<Notes> {
    Context context;
    ArrayList<Notes> notes;
    String timeStamp;
    static ArrayList<Notes> holder_;
    Typeface font;

    public ListAdapter(Context context, ArrayList<Notes> notes) {
        super(context, 0, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        if (notes == null)
        return 0;
        else {
            return notes.size();
        }
    }

    @Override
    public Notes getItem(int position) {
        return notes.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.individual_notelist, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.notes_);
            TextView notes_date = (TextView)convertView.findViewById(R.id.notes_date);

            String fonts = SharedPrefSingleton.getInstance(getContext()).preferenceGetFontString("font_name");
            MainActivity.InitTextView(getContext(), textView, fonts);
            MainActivity.InitTextView(getContext(), notes_date, fonts);


        int fonts_size = SharedPrefSingleton.getInstance(getContext()).preferenceGetInteger("font_size");

            String note_ = notes.get(position).getNote();
            String note_date = notes.get(position).getDate();
            timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm").format(new java.util.Date());

            String[] lines;
            String delimiter = "\n";
            lines = note_.split(delimiter);


                if (note_.length() < 35)
                {
                    textView.setText(lines[0]);
                }
                else
                {
                    textView.setText(note_.substring(0, Math.min(note_.length(), 35)) + "...");

                 }

                if (isdayToday(note_date)) {
                    notes_date.setText("Today : "+ note_date.substring(10));
                }
                else {
                    notes_date.setText(note_date);
                }

            return convertView;
        }
    public void filter(String searchQuery)
    {
        holder_ = new ArrayList<>();
        holder_.addAll(notes);

        if (searchQuery.length() == 0){
            Toast.makeText(getContext(), "Enter valid search parameter", Toast.LENGTH_LONG).show();
        }
        else if (searchQuery.length() > 0)
        {
            notes.clear();
            for (Notes note : holder_)
            {
                if (note.getNote().toLowerCase().contains(searchQuery.toLowerCase()))
                {
                    notes.add(note);
                }
            }
        }
        else
        {
            Toast.makeText(getContext(), "No notes match text", Toast.LENGTH_LONG).show();
        }
        notifyDataSetChanged();
    }

    public void restore()
    {
        if (notes.isEmpty())
        {
            notes.addAll(holder_);
        }
        else
        {
            notes.clear();
            notes.addAll(holder_);
        }
        notifyDataSetChanged();
    }

    private boolean isdayToday(String note_date)
    {
        timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm").format(new java.util.Date());
        int present_year, entered_year, present_month, entered_month, present_day, entered_day;

        present_year = Integer.parseInt(timeStamp.substring(0,4));
        entered_year = Integer.parseInt(note_date.substring(0,4));

        present_month = Integer.parseInt(timeStamp.substring(5,7));
        entered_month = Integer.parseInt(note_date.substring(5,7));

        present_day = Integer.parseInt(timeStamp.substring(8,10));
        entered_day = Integer.parseInt(note_date.substring(8,10));


        if (present_year == entered_year && present_month == entered_month && present_day == entered_day) {
            return true;
        }
        else {
            return false;
        }

    }

    private boolean isdayYesterday(String date_time){
        String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm").format(new java.util.Date());

        return true;
    }

}

