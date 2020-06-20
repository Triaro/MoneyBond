package com.example.moneybond40.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.moneybond40.MainActivity;
import com.example.moneybond40.R;
import com.example.moneybond40.data.MyDBHandler;
import com.example.moneybond40.model.LentName;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<LentName> nameList;

    public RecyclerViewAdapter(MainActivity name, List<LentName> nameList) {
        this.context = name;
        this.nameList = nameList;



    }


    // where to get the single card as ViewHolder object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,
                false);

        return new ViewHolder(view);
    }



    // what will happen after we create the viewHolder object
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {

        final LentName name= nameList.get(position);

        holder.lentName.setText(name.getLentName());
        holder.lentMoney.setText(name.getLentMoney());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked item label
                LentName name = nameList.get(position);

                // Remove the item on remove/button click
                nameList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,nameList.size()-position);


                MyDBHandler db = new MyDBHandler(context);
                db.deleteName(name.getId());
                // Show the removed item label
                Toast.makeText(context,"Name Removed with position= "+position + " and" +
                        " id= " +name.getId() ,Toast.LENGTH_SHORT).show();


            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int money2=Integer.parseInt(holder.money1.getText().toString());
                final String prevMoney1= holder.lentMoney.getText().toString();
                int money = money2;

                int prevMoney= Integer.parseInt(prevMoney1);
                int finalMoney= money + prevMoney;
                String fM= String.valueOf(finalMoney);

                holder.lentMoney.setText(fM);
                name.setLentMoney(fM);
                MyDBHandler db = new MyDBHandler(context);
                db.updateName(name);

                notifyItemChanged(position);
                Log.d("add","Success");
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int money2=Integer.parseInt(holder.money1.getText().toString());
                final String prevMoney1= holder.lentMoney.getText().toString();
                int money = money2;

                int prevMoney= Integer.parseInt(prevMoney1);

                if((prevMoney-money)<0)
                    Toast.makeText(context,"Please enter a valid amount",Toast.LENGTH_SHORT).show();
                else {
                    int finalMoney= prevMoney-money;
                    String fM = String.valueOf(finalMoney);

                    holder.lentMoney.setText(fM);
                    name.setLentMoney(fM);
                    MyDBHandler db = new MyDBHandler(context);
                    db.updateName(name);
                    notifyItemChanged(position);
                    Log.d("sub", "Success");
                }
            }
        });



    }
    // How many Items
    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView lentName;
        public TextView lentMoney;
        public EditText money1;
        public ImageView iconButton ;
        public ImageButton del;
        public Button add;
        public Button sub;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            lentName = itemView.findViewById(R.id.lentName);
            lentMoney = itemView.findViewById(R.id.lentMoney);
            iconButton = itemView.findViewById(R.id.icon_button);
            del = itemView.findViewById(R.id.del);
            add= itemView.findViewById(R.id.lentAddButton);
            sub= itemView.findViewById(R.id.lentSubButton);
            money1=itemView.findViewById(R.id.lentAdd);

            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//            Toast toast = Toast.makeText(name.getApplicationContext(),
//                    "Clicked",
//                    Toast.LENGTH_SHORT);
//
//            toast.show();

//            int position = this.getAdapterPosition();
//            LentName name= nameList.get(position);
//            String lentName= name.getLentName();
//            String lentMoney= name.getLentMoney();
//            Toast.makeText(context, "Clicked position and id is "+ position + " " + name.getId(), Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(context, Displayname.class);
//
//
//            intent.putExtra("RName", name);
//            intent.putExtra("RPhone", phone);
//            context.startActivity(intent);
        }




    }


}
