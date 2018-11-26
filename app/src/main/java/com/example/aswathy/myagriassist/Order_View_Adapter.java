package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aswathy on 11/23/2018.
 */

public class Order_View_Adapter extends RecyclerView.Adapter<Order_View_Adapter.MyViewHolder> {
    Context context;
    Order_View_Bean ovb;
    List<Order_View_Bean> lovb;
    SharedPreferences o;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_order,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ovb=lovb.get(position);
        holder.name.setText(ovb.getOpname());
        holder.rate.setText(ovb.getOprate());
        String url="http://192.168.43.244:8084/AgriAssist/Farmer/image/"+ovb.getOpimg();
        Log.d("UURRLL",url);
        Picasso.with(context).load(url).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return lovb.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,rate;
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.tv_productnm);
            rate=(TextView) itemView.findViewById(R.id.tv_priceord);
            img=(ImageView) itemView.findViewById(R.id.ivord);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Order_View_Bean PV = lovb.get(getAdapterPosition());

                    String id = PV.getOpid();
                    String imagesrc=PV.getOpimg();
                    String name=PV.getOpname();
                    String rate=PV.getOprate();
                    String qty=PV.getOprate();
                    String exp=PV.getOdate();



                    o=context.getSharedPreferences("",context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=o.edit();
                    editor.putString("ord_id",id);
                    editor.commit();



                    //Toast.makeText(context,"userid"+user_id, Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(context,Product_Buy.class);
                    intent.putExtra("Imagesrc",imagesrc);
                    intent.putExtra("name",name);
                    intent.putExtra("price",rate);
                    intent.putExtra("qty",qty);
                    intent.putExtra("exp",exp);


                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


        }
    }
}
