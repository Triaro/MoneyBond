package com.example.moneybond40;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class EnterDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        Intent intent = getIntent();


        ImageButton save= findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent rintent = new Intent(EnterDetails.this, MainActivity.class);
                EditText enteredName =findViewById(R.id.enteredName);
                String name= enteredName.getText().toString();
                EditText enteredMoney= findViewById(R.id.enteredMoney);
                String money= enteredMoney.getText().toString();
                rintent.putExtra("RName", name);
                rintent.putExtra("RMoney", money);
                setResult(RESULT_OK, rintent);
                finish();


            }
        });


    }
}
