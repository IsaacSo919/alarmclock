package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Alarm_RecyclerViewAdaptor extends RecyclerView.Adapter<Alarm_RecyclerViewAdaptor.MyViewHolder> {
    List<Alarm> alarms;

    private MainActivity mainActivity;


    public Alarm_RecyclerViewAdaptor(List<Alarm> alarms) {
        this.alarms = alarms;
    }
    private void updateList(ArrayList<Alarm> alarmslist){
        this.alarms = alarmslist;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Alarm_RecyclerViewAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout giving the look of each of the rows
        Alarm_RecyclerViewAdaptor alarmAdaptor = new Alarm_RecyclerViewAdaptor(alarms);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view,alarmAdaptor);
    }

    @Override                                                               //(inner class)
    public void onBindViewHolder(@NonNull Alarm_RecyclerViewAdaptor.MyViewHolder holder, int position) {
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
        private Alarm_RecyclerViewAdaptor alarmAdaptor;
        public MyViewHolder(@NonNull View itemView, Alarm_RecyclerViewAdaptor alarmAdaptor) {
            super(itemView);
            this.alarmAdaptor = alarmAdaptor;
            hour = itemView.findViewById(R.id.Hour);
            minute = itemView.findViewById(R.id.Minute);
        }
        public void bind(Alarm alarm){
            hour.setText(alarm.getHour());
            minute.setText(alarm.getMinute());
        }
    }
}
