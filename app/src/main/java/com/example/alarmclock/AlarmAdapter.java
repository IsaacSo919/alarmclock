package com.example.alarmclock;

import static com.example.alarmclock.MainActivity.alarmAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.MyViewHolder> {
    List<Alarm> alarms;

    private MainActivity mainActivity;


    public AlarmAdapter(List<Alarm> alarms) {
        this.alarms = alarms;
    }
    private void updateList(ArrayList<Alarm> alarmslist){
        this.alarms = alarmslist;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AlarmAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout giving the look of each of the rows
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view,alarmAdapter);
    }

    @Override                                                               //(inner class)
    public void onBindViewHolder(@NonNull AlarmAdapter.MyViewHolder holder, int position) {
        /**   Reference the alarm item that data will be binded too
         *
         */
        Alarm alarm = alarms.get(position);
        holder.bind(alarm);

    }

    @Override
    public int getItemCount() {
        /** getter for items in total**/
        return alarms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //inserting the views from the layout file :recycler_view_row.xml
        TextView hour;
        TextView minute;

        ImageView deletebutton;
        private AlarmAdapter alarmAdapter;
        public MyViewHolder(@NonNull View itemView, AlarmAdapter alarmAdaptor) {
            super(itemView);
            this.alarmAdapter = alarmAdaptor;
            hour = itemView.findViewById(R.id.Hour);
            minute = itemView.findViewById(R.id.Minute);
            deletebutton = itemView.findViewById(R.id.deletealarm);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && alarmAdapter != null){
                        Intent intent = new Intent(v.getContext(),AlarmsDetails.class);
                        intent.putExtra("alarm_position",position);
                        v.getContext().startActivity(intent);
                    }
                }
            });
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        showDeleteConfirmationDialog(position,itemView.getContext());
                    }
                }
            });

        }

        public void deleteAlarm(int position) {
            if (position >= 0 && position < alarms.size()) {
                // Remove the note from the list
                alarms.remove(position);
                // Notify the adapter about the removal
                notifyItemRemoved(position);
                alarmAdapter.notifyDataSetChanged();
            }
        }

        private void showDeleteConfirmationDialog(final int position, Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirm deletion");
            builder.setMessage("Confirm deletion of this alarm");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Call deleteAlarm method on the MyViewHolder instance
                    if (MyViewHolder.this != null) {
                        MyViewHolder.this.deleteAlarm(position);
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }

        public void bind(Alarm alarm){
            hour.setText(alarm.getHour());
            minute.setText(alarm.getMinute());
        }

    }
}
