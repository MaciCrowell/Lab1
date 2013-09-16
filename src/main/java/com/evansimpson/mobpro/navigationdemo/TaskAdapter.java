package com.evansimpson.mobpro.navigationdemo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mingram on 9/12/13.
 */
public class TaskAdapter extends ArrayAdapter {

    Context context;
    int layoutResourceId;
    ArrayList<String> tasks;

    public TaskAdapter(Context context, int layoutResourceId, ArrayList<String> tasks) {
        super(context, layoutResourceId, tasks);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.tasks=tasks;
        Log.w("HERE", "TaskAdapter");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;
        final int pos = position;
        Log.w("HERE", "getview");

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TaskHolder();
            holder.taskText = (TextView)row.findViewById(R.id.taskName);
            holder.deleteButton = (Button)row.findViewById(R.id.delButton);

            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }


        String task = tasks.get(position);
        holder.taskText.setText(task);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.remove(pos);
                notifyDataSetChanged();
            }
        });

        return row;
    }



    static class TaskHolder
    {
        TextView taskText;
        Button deleteButton;
    }
}

