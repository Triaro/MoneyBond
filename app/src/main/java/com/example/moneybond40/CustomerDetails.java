package com.example.moneybond40;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.moneybond40.adapter.RecyclerViewAdapter2;
import com.example.moneybond40.adapter.RecyclerViewAdapter;
import com.example.moneybond40.data.MyDBHandler;

import com.example.moneybond40.model.Name;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class CustomerDetails extends AppCompatActivity {



    MyDBHandler db = new MyDBHandler(this);
    public static ArrayList<Name> historyArrayList;
    public static int status=1;
    private static final int PICK_IMAGE = 100;
    private static final int PICK_MONEY = 7;
    public List<Name> nameList;
    private RecyclerViewAdapter recyclerViewAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        Toolbar myToolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(myToolbar);
        Log.d("check", "Customer details reached");
        Intent intent1 =getIntent();
        int position = intent1.getIntExtra("RPosition",0);
        String number = intent1.getStringExtra("RNumber");
        String name = intent1.getStringExtra("RName");
        TextView status1= findViewById(R.id.status);
        TextView netMoney1 = findViewById(R.id.netMoney);
        TextView rupee2 = findViewById(R.id.rupee2);
        nameList= db.getAllNames();
        Name name1 = nameList.get(position);
        if(name1.getMoney()==null) {
            status1.setText("Your transaction will be shown here");
            netMoney1.setText("0");

        }
        else{
            netMoney1.setText(name1.getMoney());
            status1.setText(name1.getStatus());
        }

        //RecyclerView initialisation
//        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
//        recyclerView2.setHasFixedSize(true);
//        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


        TextView CustomerName = findViewById(R.id.customerName);
        CustomerName.setText(name);
        TextView CustomerNumber = findViewById(R.id.customerNumber);
        CustomerNumber.setText(number);
        getSupportActionBar().setTitle(" ");
if(name1.getMoney().equals("0"))
{
    netMoney1.setTextColor(getResources().getColor(R.color.divider));
    rupee2.setTextColor(getResources().getColor(R.color.divider));
}
else if(name1.getColorStatus()==0) {
    netMoney1.setTextColor(getResources().getColor(R.color.green));
    rupee2.setTextColor(getResources().getColor(R.color.green));
}
else{
    netMoney1.setTextColor(getResources().getColor(R.color.red));
    rupee2.setTextColor(getResources().getColor(R.color.red));
}

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CustomerDetails.this,MainActivity.class);
                startActivity(i);
            }
        });

        Button lentButton = findViewById(R.id.lentButton);
        Button borrowedButton = findViewById(R.id.borrowedButton);
        lentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd= new Intent(CustomerDetails.this, AddMoney.class);
                intentAdd.putExtra("status","Enter the amount you lent");
                status=1;
                startActivityForResult(intentAdd,PICK_MONEY);


            }
        });
        borrowedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd= new Intent(CustomerDetails.this, AddMoney.class);
                intentAdd.putExtra("status","Enter the amount you borrowed");
                status=0;
                startActivityForResult(intentAdd, PICK_MONEY);


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
                Intent intent1 =getIntent();
                int position = intent1.getIntExtra("RPosition",0);
                Name name = nameList.get(position);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" +name.getNumber()));
                startActivity(callIntent);
                return true;
            case R.id.changePicture:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,PICK_IMAGE);
                return true;
            case R.id.del:
                Intent intentCall = getIntent();
                int id = intentCall.getIntExtra("RId", 0);
                int position1 = intentCall.getIntExtra("RPosition",0);
                List<Name> nameList = db.getAllNames();
                 // Remove the item on remove/button click
                nameList.remove(position1);
                db.deleteName(id);
                notifyAll();
                finish();
                return true;
            case R.id.share:
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                ImageView imageView = findViewById(R.id.dp);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //   imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//             String stringUri = selectedImage.getPath();
//             imageView.setImageURI(Uri.parse(stringUri));
        }
        if (requestCode == PICK_MONEY)
         { //LayoutInflater inflater = getLayoutInflater();
//             View myView = inflater.inflate(R.layout.row, null);
             TextView status1= findViewById(R.id.status);
             TextView netMoney1 = findViewById(R.id.netMoney);
//             TextView Money = myView.findViewById(R.id.Money);
//             TextView rupee1 = myView.findViewById(R.id.rupee1);
             TextView rupee2 = findViewById(R.id.rupee2);
             nameList= db.getAllNames();
             Intent intent1 =getIntent();
             int position = intent1.getIntExtra("RPosition",0);
             Name name = nameList.get(position);
             if(name.getMoney()==null) {
                 status1.setText("There is no transaction due right now with "+ name.getName());
                 netMoney1.setText("");

             }
             else{
                 netMoney1.setText(name.getMoney());
                 status1.setText(name.getStatus());
             }

            String money2=data.getExtras().getString("money");
            int money = Integer.parseInt(money2);
            String netMoney=netMoney1.getText().toString();
            int prevMoney = Integer.parseInt(netMoney);

           if(status==1) {
               if(status1.getText().equals("You have Borrowed from "+ name.getName()))
                   prevMoney=-prevMoney;
               int finalMoney = prevMoney + money;
               if(finalMoney==0) {
                   status1.setText("Your current transaction status with" + name.getName()+" is");
                   netMoney1.setTextColor(getResources().getColor(R.color.divider));
                   rupee2.setTextColor(getResources().getColor(R.color.divider));
               }
               else if(finalMoney<0)
               {
                   status1.setText("You have Borrowed from "+ name.getName());
                   finalMoney=-finalMoney;
                   name.setColorStatus(0);
                   name.setStatus("You have Borrowed from "+ name.getName());
                   netMoney1.setTextColor(getResources().getColor(R.color.green));
                   rupee2.setTextColor(getResources().getColor(R.color.green));

               }
               else {
                   status1.setText("You have Lent "+ name.getName());
                   name.setColorStatus(1);
                   name.setStatus("You have Lent "+ name.getName());
                   netMoney1.setTextColor(getResources().getColor(R.color.red));
                   rupee2.setTextColor(getResources().getColor(R.color.red));
               }

               netMoney1.setText(String.valueOf(finalMoney));
               name.setMoney(String.valueOf(finalMoney));
               db.updateName(name);

               Log.d("check7","Name updated of id "+name.getId()+ " with amount "+name.getMoney());
           }
           else{
               if(status1.getText().equals("You have Borrowed from "+ name.getName()))
                   prevMoney=-prevMoney;
               int finalMoney = prevMoney - money;
               if(finalMoney==0) {
                   status1.setText("Your current transaction status with "+ name.getName()+" is");
                   netMoney1.setTextColor(getResources().getColor(R.color.divider));
                   rupee2.setTextColor(getResources().getColor(R.color.divider));
               }
               else if(finalMoney<0)
               {
                status1.setText("You have Borrowed from "+ name.getName());
                finalMoney=-finalMoney;
                name.setColorStatus(0);
                netMoney1.setTextColor(getResources().getColor(R.color.green));
                rupee2.setTextColor(getResources().getColor(R.color.green));
                name.setStatus("You have Borrowed from "+ name.getName());

               }
               else{status1.setText("You have Lent "+ name.getName());
                   name.setColorStatus(1);
                   netMoney1.setTextColor(getResources().getColor(R.color.red));
                   rupee2.setTextColor(getResources().getColor(R.color.red));
                   name.setStatus("You have Lent "+ name.getName());
               }

               netMoney1.setText(String.valueOf(finalMoney));
               name.setMoney(String.valueOf(finalMoney));
               db.updateName(name);
           }
            MainActivity.dataChangeStatus=1;
        }


    }
}

