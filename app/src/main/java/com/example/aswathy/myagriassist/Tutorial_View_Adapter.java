package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aswathy on 11/19/2018.
 */

public class Tutorial_View_Adapter extends RecyclerView.Adapter<Tutorial_View_Adapter.MyViewHolder> {
    Context context;
    Tutorial_View_Bean tvb;
    List<Tutorial_View_Bean> ltvb;


    public Tutorial_View_Adapter(List<Tutorial_View_Bean> tvb, Context applicationContext) {
        this.context=applicationContext;
        this.ltvb=tvb;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_tut,parent,false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        tvb=ltvb.get(position);
        String url="http://192.168.43.244:8084/AgriAssist/Admin/Tutorials/"+tvb.getTimg();
        Log.d("UURRLL",url);
        Picasso.with(context).load(url).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return ltvb.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.ivtut);
        }
    }
}
