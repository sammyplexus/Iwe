package com.samuelagbede.sammybobo.iwe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by Agbede on 01/09/2016.
 */

public class NoteExplore extends AppCompatActivity
{
    EditText editText;
    SQLHandler sqlHandler;
    int note_id_;
    int node_point;
    Notes notes;
    Typeface font;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_expanded);
        ///actionModeCallBack = new ActionModeCallBack();
        int fontSize = SharedPrefSingleton.getInstance(this).preferenceGetInteger("font_size");
        sqlHandler = new SQLHandler(this, null, null, 1);
        editText = (EditText)findViewById(R.id.edittext_notes_expanded);

        String fonts = SharedPrefSingleton.getInstance(this).preferenceGetFontString("font_name");
        int fonts_size = SharedPrefSingleton.getInstance(this).preferenceGetInteger("font_size");

        MainActivity.InitEditText(this, editText, fonts);

        editText.setTextSize(fonts_size);

        Intent a = getIntent();
        notes = new Notes();
        notes = (Notes)a.getSerializableExtra(MainActivity.keys);
        note_id_ = notes.getId();
        editText.setTextSize(fontSize);
        editText.setText(a.getStringExtra("note_explore"));
        node_point = a.getIntExtra("note_in_list",-1);

        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                menu.clear();
                actionMode.getMenuInflater().inflate(R.menu.font_edit, menu);
                /*menu.removeItem(android.R.id.selectAll);
                menu.removeItem(android.R.id.selectTextMode);
                menu.removeItem(android.R.id.paste);
                menu.removeItem(android.R.id.pasteAsPlainText);
                menu.removeItem(android.R.id.copy);
                menu.removeItem(android.R.id.cut);*/
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
            {
                CharacterStyle characterStyle;
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(editText.getText());
                int start = editText.getSelectionStart();
                int end = editText.getSelectionEnd();

                int selectionStart = editText.getSelectionStart();
                int selectionEnd = editText.getSelectionEnd();

                if (selectionStart > selectionEnd)
                {
                    int temp = selectionEnd;
                    selectionEnd = selectionStart;
                    selectionStart = temp;
                }


                if (selectionEnd > selectionStart) {
                    Spannable str = editText.getText();
                    boolean exists = false;
                    StyleSpan[] styleSpans;


                    switch (menuItem.getItemId()) {
                        case R.id.bold:
                            Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
                            Typeface lobster_bold = Typeface.createFromAsset(getAssets(),"fonts/LobsterTwo-Bold.otf");
                            Typeface raleway_bold = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Bold.ttf");


                            spannableStringBuilder.setSpan(new CustomFonts("",lobster_bold),selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            

                            styleSpans = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);

                            // If the selected text-part already has BOLD style on it, then
                            // we need to disable it
                            /*for (int i = 0; i < styleSpans.length; i++) {


                                if (styleSpans[i].getStyle() == android.graphics.Typeface.BOLD) {
                                    str.removeSpan(styleSpans[i]);
                                    exists = true;
                                }
                            }

                            // Else we set BOLD style on it
                            if (!exists) {
                                str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), selectionStart, selectionEnd,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }*/
                            editText.setText(spannableStringBuilder);
                            /*editText.setTypeface(null,Typeface.BOLD);*/


                            return true;

                        case R.id.italics:
                            Typeface quicksand_italic = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Italic.otf");
                            Typeface lobster_italic = Typeface.createFromAsset(getAssets(),"fonts/LobsterTwo-Italic.otf");
                            Typeface raleway_italic = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Italic.ttf");


                            characterStyle = new StyleSpan(Typeface.ITALIC);
                            spannableStringBuilder.setSpan(characterStyle, start, end, 1);
                            editText.setText(spannableStringBuilder);
                            return true;

                        case R.id.underline:
                            characterStyle = new UnderlineSpan();
                            spannableStringBuilder.setSpan(characterStyle, start, end, 1);
                            editText.setText(spannableStringBuilder);
                            return true;
                    }
                }
                    return false;
                }


            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_expanded, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.action_delete:
                AlertDialog.Builder alert = new AlertDialog.Builder(NoteExplore.this);
                alert.setMessage("Are you sure you would want to delete?");
                alert.setTitle("Delete note");
                alert.setNegativeButton("Cancel", null);
                alert.setPositiveButton("Delete note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int a = sqlHandler.deleteNotes(note_id_);
                        Log.d("Value of lists", node_point + " ");
                        //notesListData.remove(notesListData.get(node_point));
                        //MainActivity.listAdapter.notifyDataSetChanged();
                        startActivity(new Intent(NoteExplore.this, MainActivity.class));
                        Toast.makeText(NoteExplore.this, "Note deleted", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();
                break;
            case R.id.action_update:
                notes.setNote(editText.getText().toString());
                Log.d("Hello",notes.getNote());
                String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm").format(new java.util.Date());
                notes.setDate(timeStamp);
                ///Log.d("Size of list", notesListData.size()+"");
                //notesListData.remove(notesListData.get(node_point));
                //Log.d("Size of list after", notesListData.size()+"");
                sqlHandler.update(notes);
                startActivity(new Intent(NoteExplore.this, MainActivity.class));
                Toast.makeText(NoteExplore.this, "Note saved", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_share:
                Intent share_note = new Intent(Intent.ACTION_SEND);
                share_note.setType("text/plain");
                share_note.putExtra(Intent.EXTRA_REFERRER_NAME, "Iwe note app");
                share_note.putExtra(Intent.EXTRA_TEXT, notes.getNote() + "\n" + "Shared via Iwe" );
                startActivity(Intent.createChooser(share_note, "Share your amazing note via "));




        }
        return super.onOptionsItemSelected(item);
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.




}
