package com.example.offlinedatabaseapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptar extends RecyclerView.Adapter<Adaptar.DataViewHolder> {

    private Context context;

    private ArrayList<Coustomer> coustomers;

    public Adaptar(Context context, ArrayList<Coustomer> coustomers) {
        this.context = context;
        this.coustomers = coustomers;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        Coustomer currentCoustomer = coustomers.get(position);

        holder.id.setText(currentCoustomer.getId());
        holder.name.setText(currentCoustomer.getName());
        holder.mobile.setText(currentCoustomer.getMobile());

    }

    @Override
    public int getItemCount() {
        return coustomers.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private TextView mobile;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            id= itemView.findViewById(R.id.coustomerIDTV);
            name = itemView.findViewById(R.id.coustomerNameTV);
            mobile = itemView.findViewById(R.id.coustomerMobileTV);
        }
    }

    public void addCoustomer(Coustomer coustomer) {
       coustomers.add(coustomer);
        notifyDataSetChanged();
    }
    public void reset() {
        coustomers.clear();
        notifyDataSetChanged();
    }
}
