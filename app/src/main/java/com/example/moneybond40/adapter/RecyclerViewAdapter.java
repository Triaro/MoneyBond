package com.example.moneybond40.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


import com.example.moneybond40.CustomerDetails;
import com.example.moneybond40.MainActivity;
import com.example.moneybond40.R;
import com.example.moneybond40.data.MyDBHandler;
import com.example.moneybond40.model.Name;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Name> nameList;

    public RecyclerViewAdapter(MainActivity name, List<Name> nameList) {
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

        final Name name= nameList.get(position);

        holder.Name.setText(name.getName());
        holder.Money.setText(name.getMoney());
        byte[] image =name.getImage();
        if(image!=null)
            holder.iconButton.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        else
            holder.iconButton.setImageResource(R.drawable.abstractuser);
        if(name.getMoney().equals("0"))
        {
            holder.Money.setTextColor(context.getResources().getColor(R.color.divider));
            holder.rupee1.setTextColor(context.getResources().getColor(R.color.divider));
        }
        else if(name.getColorStatus()==0){
            holder.rupee1.setTextColor(context.getResources().getColor(R.color.green));
            holder.Money.setTextColor(context.getResources().getColor(R.color.green));
        }
        else{
            holder.rupee1.setTextColor(context.getResources().getColor(R.color.red));
            holder.Money.setTextColor(context.getResources().getColor(R.color.red));
        }
//        holder.del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the clicked item label
//                Name name = nameList.get(position);
//
//                // Remove the item on remove/button click
//                nameList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position,nameList.size()-position);
//
//
//                MyDBHandler db = new MyDBHandler(context);
//                db.deleteName(name.getId());
//
//                // Show the removed item label
//                Toast.makeText(context,"Name Removed with position= "+position + " and" +
//                        " id= " +name.getId() ,Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int money2=Integer.parseInt(holder.money1.getText().toString());
//                final String prevMoney1= holder.Money.getText().toString();
//                int money = money2;
//
//                int prevMoney= Integer.parseInt(prevMoney1);
//                int finalMoney= money + prevMoney;
//                String fM= String.valueOf(finalMoney);
//
//                holder.Money.setText(fM);
//                name.setMoney(fM);
//                MyDBHandler db = new MyDBHandler(context);
//                db.updateName(name);
//
//                notifyItemChanged(position);
//                Log.d("add","Success");
//            }
//        });
//        holder.sub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int money2=Integer.parseInt(holder.money1.getText().toString());
//                final String prevMoney1= holder.Money.getText().toString();
//                int money = money2;
//
//                int prevMoney= Integer.parseInt(prevMoney1);
//
//                if((prevMoney-money)<0)
//                    Toast.makeText(context,"Please enter a valid amount",Toast.LENGTH_SHORT).show();
//                else {
//                    int finalMoney= prevMoney-money;
//                    String fM = String.valueOf(finalMoney);
//
//                    holder.Money.setText(fM);
//                    name.setMoney(fM);
//                    MyDBHandler db = new MyDBHandler(context);
//                    db.updateName(name);
//                    notifyItemChanged(position);
//                    Log.d("sub", "Success");
//                }
//            }
//        });



    }
    // How many Items
    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Name;
        public TextView Money;
        public TextView rupee1;
        public EditText money1;
        public ImageView iconButton ;
        public ImageButton del;
        public Button add;
        public Button sub;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            Name = itemView.findViewById(R.id.Name);
            Money = itemView.findViewById(R.id.Money);
            rupee1 = itemView.findViewById(R.id.rupee1);
            iconButton = itemView.findViewById(R.id.icon_button);
            del = itemView.findViewById(R.id.del);
            add= itemView.findViewById(R.id.lentButton);
            sub= itemView.findViewById(R.id.borrowedButton);
            //money1=itemView.findViewById(R.id.lentAdd);

            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {



            int position = this.getAdapterPosition();
            Name name= nameList.get(position);
            Integer customerId=name.getId();
            String customerName= name.getName();
            String customerMoney= name.getMoney();
            String customerNumber= name.getNumber();
            byte[] customerImage= name.getImage();

            //Toast.makeText(context,"the id is "+ name.getId()+" Amount is "+name.getMoney()+" Position is "+position,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, CustomerDetails.class);
            intent.putExtra("RId",customerId);
            intent.putExtra("RPosition",position);
            intent.putExtra("RName", customerName);
            intent.putExtra("RMoney", customerMoney);
            intent.putExtra("RNumber", customerNumber);
            intent.putExtra("RImage", customerImage);
            context.startActivity(intent);
        }




    }


}
