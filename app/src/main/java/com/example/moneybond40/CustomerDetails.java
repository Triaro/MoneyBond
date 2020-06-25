package com.example.moneybond40;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneybond40.adapter.RecyclerViewAdapter2;
import com.example.moneybond40.data.MyDBHandler;
import com.example.moneybond40.data.MyDBHandlerHistory;
import com.example.moneybond40.model.Name;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetails extends AppCompatActivity {
    MyDBHandler db1 = new MyDBHandler(this);
    MyDBHandlerHistory db = new MyDBHandlerHistory(this);
    private RecyclerView recyclerView2;
    private RecyclerViewAdapter2 recyclerViewAdapter2;
    public static ArrayList<Name> historyArrayList;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        Toolbar myToolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(myToolbar);
        Log.d("check", "Customer details reached");

        Intent intent1 = getIntent();
        String number = intent1.getStringExtra("RNumber");
        String name = intent1.getStringExtra("RName");
        String money = intent1.getStringExtra("RMoney");
        //RecyclerView initialisation
        recyclerView2= findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


        TextView CustomerName = findViewById(R.id.customerName);
        CustomerName.setText(name);
        TextView CustomerNumber = findViewById(R.id.customerNumber);
        CustomerNumber.setText(number);
        TextView CustomerMoney = findViewById(R.id.netMoney);
        CustomerMoney.setText(money);
        getSupportActionBar().setTitle(" ");

        historyArrayList = new ArrayList<>();
        // get all names
        List<Name> historyList = db.getHistory();

        for(Name history : historyList) {

            Log.d("dbDisplay", "Amount " + history.getAmount() + "Time " + history.getTime()+ "\n");
            historyArrayList.add(history);

            //db.deleteName(lentName.getId());
        }
        //Using RecyclerView
        recyclerViewAdapter2 = new RecyclerViewAdapter2(CustomerDetails.this, historyArrayList);
        recyclerView2.setAdapter(recyclerViewAdapter2);
        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button lentButton = findViewById(R.id.lentButton);
        Button borrowedButton = findViewById(R.id.borrowedButton);
        lentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd= new Intent(CustomerDetails.this, AddMoney.class);
                intentAdd.putExtra("status","Enter the amount you lent");
                startActivityForResult(intentAdd,77);

            }
        });
        borrowedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd= new Intent(CustomerDetails.this, AddMoney.class);
                intentAdd.putExtra("status","Enter the amount you borrowed");
                startActivity(intentAdd);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.call:

                return true;
            case R.id.changePicture:

                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,PICK_IMAGE);
                return true;
            case R.id.del:
                Intent intent1 = getIntent();
                int id = intent1.getIntExtra("RId", 0);
                int position = intent1.getIntExtra("RPosition",0);
                List<Name> nameList = db1.getAllNames();
                Name name = nameList.get(id);
                // Remove the item on remove/button click
                nameList.remove(position);
                db1.deleteName(id);
                notifyAll();

                finish();
                return true;
            case R.id.share:
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = findViewById(R.id.icon_button);

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            String stringUri = selectedImage.getPath();
            imageView.setImageURI(null);
            imageView.setImageURI(Uri.parse(stringUri));
        }

        if (requestCode == 77) {
            if (resultCode == Activity.RESULT_OK) {
                String rMoney = data.getStringExtra("money");
                TextView netMoney = findViewById(R.id.netMoney);
                String moneyF = "₹" + rMoney;
                netMoney.setText(moneyF);
            }
        }
    }
}

