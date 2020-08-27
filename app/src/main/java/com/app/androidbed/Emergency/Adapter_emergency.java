package com.app.androidbed.Emergency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.androidbed.R;

import java.util.List;

public class Adapter_emergency extends RecyclerView.Adapter<Adapter_emergency.Viewholder_emergency> {
        private Context mcomtext;
        private List<Model_Hospital_Details> mlist;
        private Onitemclicklistener listener;

    public Adapter_emergency(Context mcomtext, List<Model_Hospital_Details> mlist) {
        this.mcomtext = mcomtext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public Viewholder_emergency onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcomtext).inflate(R.layout.hospital_layout,parent,false);

        return new Viewholder_emergency(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder_emergency holder, int position) {
        Model_Hospital_Details mhd=mlist.get(position);

        holder.tvhospital.setText(mhd.getHospital_name());
        holder.tvaddress.setText(mhd.getAddress());
        holder.tvphone.setText(mhd.getHospital_number());


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class Viewholder_emergency extends RecyclerView.ViewHolder {

        private TextView tvhospital,tvaddress,tvphone;
        private Button btncall;

        public Viewholder_emergency(@NonNull View itemView) {
            super(itemView);

            tvhospital=itemView.findViewById(R.id.textView11);
            tvaddress=itemView.findViewById(R.id.textView12);
            tvphone=itemView.findViewById(R.id.textview13);

            btncall=itemView.findViewById(R.id.btncall);

            btncall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.call_hospital(position);
                        }

                    }
                }
            });

        }
    }

    public interface Onitemclicklistener
    {
        void call_hospital(int position);

    }

    public void setOnitemclicklistener(Onitemclicklistener mlistener)
    {
        listener=mlistener;
    }
}
