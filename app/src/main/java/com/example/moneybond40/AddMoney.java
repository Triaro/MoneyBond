package com.example.moneybond40;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneybond40.adapter.RecyclerViewAdapter;
//import com.example.moneybond40.adapter.RecyclerViewAdapter2;
import com.example.moneybond40.data.MyDBHandler;
import com.example.moneybond40.model.Name;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.NameList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMoney extends AppCompatActivity {
    public static int identityH=0;
    MyDBHandler db = new MyDBHandler(AddMoney.this);
    private RecyclerView recyclerView2;
    //private RecyclerViewAdapter2 recyclerViewAdapter2;
    private List<Name> historyList;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        Intent intent = getIntent();
        String statusR = intent.getStringExtra("status");
        TextView status = findViewById(R.id.status);
        status.setText(statusR);


        // Notify the adapter that an item inserted
        //recyclerViewAdapter2.notifyItemInserted(CustomerDetails.historyArrayList.size());

        ImageButton save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText money= findViewById(R.id.enteredMoney);
//                Name history= historyList.get(CustomerDetails.position);
//                history.setAmount(money.toString());
//                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
//                history.setTime("₹" + currentDateTimeString);
//
//                //Adding history to db
//                db.addHistory(history);
//                Log.d("check2", "AddMoney reached");
//                CustomerDetails.historyArrayList.add(history);
                if( money.getText().toString().equals("")){
                    Toast.makeText(AddMoney.this, "Please enter something", Toast.LENGTH_SHORT).show();
//
                }
                else {Intent retData=new Intent();
                    String money1= money.getText().toString();
                    DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
                    String currentTime = dateFormat2.format(new Date());
                    retData.putExtra("money",money1);
                    retData.putExtra("time",currentTime);
                    setResult(RESULT_OK, retData);
                    finish();
                    overridePendingTransition( R.anim.nothing,R.anim.bottom_down);}
            }
        });
        ImageButton cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                         finish();
                overridePendingTransition( R.anim.nothing,R.anim.bottom_down);
            }
        });

    }
}


