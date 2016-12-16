package com.foursquare.takehome;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.R.attr.textSize;
import static com.foursquare.takehome.Util.getFormattedTime;


final public class PersonAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final Context mContext;
    private final List<Person> visitors;
    private final LayoutInflater inflater;

    private Date date;

    public PersonAdapter(Context context, List<Person> visitors) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.visitors = visitors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        PersonViewHolder viewHolder = new PersonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonViewHolder mHolder = (PersonViewHolder) holder;
        Person visitor = visitors.get(position);

        if (visitor.getName().equals(mContext.getResources().getString(R.string.GAP_NAME))) {
            mHolder.txtName.setTextColor(Color.GRAY);
            mHolder.txtTime.setTextColor(Color.GRAY);
        }

        mHolder.txtName.setText(visitor.getName());
        String arrTime = Util.getFormattedTime(visitor.getArriveTime());
        String leaveTime = Util.getFormattedTime(visitor.getLeaveTime());
        mHolder.txtTime.setText(arrTime + " - " + leaveTime);
    }

    @Override
    public int getItemCount() {
        if (visitors == null) {
            return 0;
        }
        return visitors.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtTime;

        // constructor to save views
        public PersonViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.tvPersonName);
            txtTime = (TextView) itemView.findViewById(R.id.tvTimeSlot);
        }

    }
}
