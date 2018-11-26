package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aswathy on 11/6/2018.
 */

public class Product_View_Adapter extends RecyclerView.Adapter<Product_View_Adapter.MyViewHolder> {


    Context context;
    Product_View_Bean pvb;
    List<Product_View_Bean>lpvb;
    SharedPreferences shpr;
    String shpr_name;

    public Product_View_Adapter(List<Product_View_Bean> pvb, Context applicationContext) {
        this.context=applicationContext;
        this.lpvb=pvb;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_product,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        pvb=lpvb.get(position);
        holder.name.setText(pvb.getPname());
        holder.rate.setText(pvb.getPrate());
        String url="http://192.168.43.244:8084/AgriAssist/Farmer/image/"+pvb.getPimg();
        Log.d("UURRLL",url);
        Picasso.with(context).load(url).into(holder.img);




    }

    @Override
    public int getItemCount() {
        return lpvb.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,rate;
        ImageView img;


        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.tv_productname);
            rate=(TextView) itemView.findViewById(R.id.tv_productrate);
            img=(ImageView) itemView.findViewById(R.id.ivpdt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Product_View_Bean PV = lpvb.get(getAdapterPosition());

                    String id = PV.getPid();
                    String imagesrc=PV.getPimg();
                    String name=PV.getPname();
                    String rate=PV.getPrate();
                    String qty=PV.getPqty();
                    String exp=PV.getEdate();
                    String man=PV.getMdate();
                    String desc=PV.getPdesc();


                    shpr=context.getSharedPreferences(shpr_name,context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=shpr.edit();
                    editor.putString("product_id",id);
                    editor.commit();



                    //Toast.makeText(context,"userid"+user_id, Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(context,Product_Buy.class);
                    intent.putExtra("Imagesrc",imagesrc);
                    intent.putExtra("name",name);
                    intent.putExtra("price",rate);
                    intent.putExtra("qty",qty);
                    intent.putExtra("exp",exp);
                    intent.putExtra("man",man);
                    intent.putExtra("desc",desc);
                    intent.putExtra("id",id);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });



        }
    }
}
