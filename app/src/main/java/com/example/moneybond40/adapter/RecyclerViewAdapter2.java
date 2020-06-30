//package com.example.moneybond40.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.moneybond40.CustomerDetails;
//import com.example.moneybond40.MainActivity;
//import com.example.moneybond40.R;
//import com.example.moneybond40.model.Name;
//
//import java.util.List;
//
//public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
//    private Context context;
//    private List<Name> historyList;
//
//    public RecyclerViewAdapter2(CustomerDetails name, List<Name>historyList) {
//        this.context = name;
//        this.historyList = historyList;
//
//
//
//    }
//
//
//    // where to get the single card as ViewHolder object
//    @NonNull
//    @Override
//    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent,
//                false);
//
//        return new ViewHolder(view);
//    }
//
//
//
//    // what will happen after we create the viewHolder object
//    @Override
//    public void onBindViewHolder(@NonNull final RecyclerViewAdapter2.ViewHolder holder, final int position) {
//
//        final Name name= historyList.get(position);
//
//        holder.Amount.setText(name.getName());
//        holder.Time.setText(name.getMoney());
//
//
//    }
//    // How many Items
//    @Override
//    public int getItemCount() {
//        return historyList.size();
//    }
//
//    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
//        public TextView Amount;
//        public TextView Time;
//
//
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//
//           Amount = itemView.findViewById(R.id.instantMoney);
//           Time = itemView.findViewById(R.id.time);
//
//        }
//
//        @Override
//        public void onClick(View v) {
//
//
//        }
//
//
//
//
//    }
//
//
//}
