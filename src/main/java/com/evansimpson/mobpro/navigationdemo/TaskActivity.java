package com.evansimpson.mobpro.navigationdemo;

/**
 * Created by mingram on 9/11/13.
 */
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskActivity extends ListActivity {

    ArrayList<String> tasks;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        tasks = new ArrayList<String>();

        adapter = new TaskAdapter(this, R.layout.row_view, tasks);
        setListAdapter(adapter);
        registerForContextMenu(getListView());

        Button c = (Button) findViewById(R.id.addTaskButton);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText taskName  = (EditText)findViewById(R.id.taskText);
                tasks.add(taskName.getText().toString());
                taskName.setText("");
                adapter.notifyDataSetChanged();

            }
        });

        c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(TaskActivity.this, "Tap to add task to list", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        //  info.position will give the index of selected item
        final int pos=info.position;
        if(item.getTitle()=="Delete"){ // if user taps the delete button the task will be deleted
            tasks.remove(pos);
            adapter.notifyDataSetChanged();
        }
        else if(item.getTitle()=="Edit") { //if the user taps edit an alert dialog will open allowing them to edit the text.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Edit Task");

// Set up the input
            final EditText input = new EditText(this);
            InputFilter[] LengthFilter = new InputFilter[1];
            LengthFilter[0] = new InputFilter.LengthFilter(10);
            input.setFilters(LengthFilter);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            input.setText(tasks.get(pos));
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String replacementTask= input.getText().toString();
                    tasks.set(pos,replacementTask);
                    adapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        return true;
    }
}