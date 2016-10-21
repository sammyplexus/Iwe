package com.samuelagbede.sammybobo.iwe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements WriteNoteDialog.DialogEvents, AdapterView.OnItemClickListener,  View.OnClickListener, AdapterView.OnItemLongClickListener, Activity_Settings.DialogEvents {

    DialogFragment writeNoteDialog;
    DialogFragment dialogSettings;
    EditText editText;
    TextView textView;
    String editTextString;
    FloatingActionButton fab;
    LayoutInflater inflater;
    View view;
    SQLHandler sqlHandler;
    TextView noList;
    Notes notes;
    ListView notesList;
    ArrayList<Notes> notesListData;
    ListAdapter listAdapter;
    Menu _menu;
    CoordinatorLayout coord;
    ActionMode actionMode;
    ActionModeCallBack actionModeCallback;
    AppCompatSpinner font_size, font_name, theme_name;
    SharedPrefSingleton sharedPrefSingleton;
    public static final String keys = "itsthekey";

    int list_point = -1;
    int id = -1;

    public void showDialog()
    {

        writeNoteDialog.show(getSupportFragmentManager(), "NoticeDialog");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesListData = new ArrayList<Notes>();

        sharedPrefSingleton = SharedPrefSingleton.getInstance(this);

        noList = (TextView)findViewById(R.id.empty);
        InitViews();
        actionModeCallback = new ActionModeCallBack();
        sqlHandler = new SQLHandler(this, null, null, 1);
        inflater = getLayoutInflater();

        view = inflater.inflate(R.layout.write_new_text, null, false);
        notesListData = getNotes();

        listAdapter = new ListAdapter(this, notesListData);
        notesList.setEmptyView(noList);
        notesList.setAdapter(listAdapter);
        fab.setOnClickListener(this);
        notesList.setOnItemClickListener(this);
        notesList.setOnItemLongClickListener(this);

    }

    public static void InitTextView(Context context,TextView textView,String fonts) {
        Typeface font;
        switch (fonts) {

            case "Quicksand":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
                textView.setTypeface(font);
                break;
            case "Raleway":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
                textView.setTypeface(font);
                break;
            case "LobsterTwo":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/LobsterTwo-Regular.otf");
                textView.setTypeface(font);
                break;
            case "Default":

                break;
            default:
                break;
        }
    }

    public static void InitEditText(Context context,EditText editText, String fonts) {
        Typeface font;
        switch (fonts) {

            case "Quicksand":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
                editText.setTypeface(font);
                break;
            case "Raleway":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
                editText.setTypeface(font);
                break;
            case "LobsterTwo":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/LobsterTwo-Regular.otf");
                editText.setTypeface(font);
                break;
            case "Default":

                break;
            default:
                break;
        }
    }

    private void InitViews()
    {
        notesList = (ListView)findViewById(R.id.NotesList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView)toolbar.getChildAt(0);

        Typeface font;
        String fonts = SharedPrefSingleton.getInstance(this).preferenceGetFontString("font_name");

        InitTextView(this, textView, fonts);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        writeNoteDialog = new WriteNoteDialog();
        dialogSettings = new Activity_Settings();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coord = (CoordinatorLayout)findViewById(R.id.coord);
        notesList.setOnItemClickListener(this);

    }

    private ArrayList<Notes> getNotes()
    {
        ArrayList<Notes> notes;
        notes = sqlHandler.getAllNotes();
        return notes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        _menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                listAdapter.filter(query);
                notesList.invalidate();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {

                return true;
            }

        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                listAdapter.restore();
                //listAdapter.notifyDataSetChanged();

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings : dialogSettings.show(getSupportFragmentManager(), "Dialog");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSave(DialogFragment dialogFragment)
    {
        EditText editText =(EditText)dialogFragment.getDialog().findViewById(R.id.new_text);
        TextView textView = (TextView)dialogFragment.getDialog().findViewById(R.id.text_error);
        editTextString = editText.getText().toString();


        if (editTextString.length() < 1)
        {
            textView.setVisibility(View.VISIBLE);
        }
        else if (editTextString.length() >= 1)
        {
            Notes notes_ = new Notes();
            String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm").format(new java.util.Date());
            notes_.setDate(timeStamp);
            notes_.setTag("General");
            notes_.setNote(editTextString);
            dialogFragment.dismiss();
            sqlHandler.addNotes(notes_);

            if (notesListData.size() == 0){
                listAdapter.add(notes_);
            }
            else if (notesListData.size() > 0){
            listAdapter.insert(notes_, 0);
            listAdapter.notifyDataSetChanged();
        }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //actionMode.finish();
        list_point = i;
        id = notesListData.get(i).getId();
        Notes seriNotes;
        seriNotes = notesListData.get(i);
        switch (adapterView.getId()){
            case R.id.NotesList:
                Intent note_explore = new Intent(this, NoteExplore.class);
                note_explore.putExtra("note_explore", seriNotes.getNote());
                note_explore.putExtra("note_date", seriNotes.getDate());
                note_explore.putExtra("note_tag", seriNotes.getTag());
                note_explore.putExtra("note_id ", id);
                Bundle bundle = new Bundle();
                bundle.putSerializable(keys, seriNotes);
                note_explore.putExtras(bundle);
                note_explore.putExtra("note_in_list", i);
                startActivity(note_explore);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fab:showDialog();
                break;
            default:
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        actionMode = startSupportActionMode(actionModeCallback);
        actionMode.setTitle("Note selected");
        list_point = i;
        Toast.makeText(MainActivity.this, "Selected "+i, Toast.LENGTH_LONG).show();
        id = notesListData.get(i).getId();

        return true;
    }

    @Override
    public void onSettings(DialogFragment dialogFragment)
    {
        dialogFragment.dismiss();
        listAdapter.notifyDataSetChanged();

    }

    class ActionModeCallBack implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.listview_selected, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            final int _id = item.getItemId();


            switch (_id){
                case R.id.action_delete:
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("Are you sure you would want to delete?" );
                    alert.setTitle("Delete note");
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            actionMode.finish();
                        }
                    });
                    alert.setPositiveButton("Delete note", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Notes notes = notesListData.get(list_point);
                            sqlHandler.deleteNotes(notes.getId());
                            if (ListAdapter.holder_ == null){

                            }
                            else {
                                ListAdapter.holder_.remove(notes);
                            }

                            listAdapter.remove(notes);

                            Toast.makeText(MainActivity.this, "Note deleted" , Toast.LENGTH_SHORT).show();
                            actionMode.finish();
                        }
                    });
                    alert.show();
                    break;

                case R.id.action_share:
                    Intent share_note = new Intent(Intent.ACTION_SEND);
                    share_note.setType("text/plain");
                    share_note.putExtra(Intent.EXTRA_REFERRER_NAME, "Iwe note app");
                    share_note.putExtra(Intent.EXTRA_TEXT, notesListData.get(list_point).getNote() + "\n" + "\n"+ "Shared via Iwe notes" );
                    startActivity(Intent.createChooser(share_note, "Share your amazing note via "));
                    break;

                case R.id.action_settings:
                    Intent settings = new Intent(MainActivity.this, Activity_Settings.class);
                    startActivity(settings);
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}
