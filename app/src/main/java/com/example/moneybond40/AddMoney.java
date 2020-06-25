package com.example.moneybond40;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.moneybond40.adapter.RecyclerViewAdapter;
import com.example.moneybond40.adapter.RecyclerViewAdapter2;
import com.example.moneybond40.data.MyDBHandler;
import com.example.moneybond40.data.MyDBHandlerHistory;
import com.example.moneybond40.model.Name;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

public class AddMoney extends AppCompatActivity {
    public static int identityH=0;
    MyDBHandlerHistory db = new MyDBHandlerHistory(AddMoney.this);
    private RecyclerView recyclerView2;
    private RecyclerViewAdapter2 recyclerViewAdapter2;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        Intent intent = getIntent();
        String statusR = intent.getStringExtra("status");
        TextView status = findViewById(R.id.status);
        status.setText(statusR);
        final EditText money= findViewById(R.id.enteredMoney);
        Name history= new Name();
//        history.setIdH(identityH);
//        identityH++;
        history.setAmount(money.toString());
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        history.setTime(currentDateTimeString);
        Log.d("row","row inserted");
        //Adding history to db
        db.addHistory(history);
        Log.d("check2", "AddMoney reached");
        CustomerDetails.historyArrayList.add(history);
        // Notify the adapter that an item inserted
        //recyclerViewAdapter2.notifyItemInserted(CustomerDetails.historyArrayList.size());

        ImageButton save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retData=new Intent();
                String money1= money.getText().toString();
                retData.putExtra("money",money1);
                setResult(RESULT_OK, retData);
                finish();
            }
        });

    }
}


