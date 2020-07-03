package com.example.moneybond40;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.moneybond40.adapter.RecyclerViewAdapter2;
import com.example.moneybond40.adapter.RecyclerViewAdapter;
import com.example.moneybond40.data.MyDBHandler;

import com.example.moneybond40.model.Name;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;




public class CustomerDetails extends AppCompatActivity {



    MyDBHandler db = new MyDBHandler(this);
    public static ArrayList<Name> historyArrayList;
    public static int status=1;
    private static final int PICK_IMAGE = 100;
    private static final int CAPTURE_IMAGE = 200;
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
        String time = intent1.getStringExtra("RTime");
        byte[] image = intent1.getByteArrayExtra("RImage");
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
        TextView CustomerTime = findViewById(R.id.time);
        if(time!=null)
        CustomerTime.setText("On "+time);
        ImageView dp=findViewById(R.id.dp);
        if(image!=null)
        dp.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        else
            dp.setImageResource(R.drawable.abstractuser);
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
                finish();
                overridePendingTransition( R.anim.nothing,R.anim.bottom_down);

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
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);


            }
        });
        borrowedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd= new Intent(CustomerDetails.this, AddMoney.class);
                intentAdd.putExtra("status","Enter the amount you borrowed");
                status=0;
                startActivityForResult(intentAdd, PICK_MONEY);
                overridePendingTransition(R.anim.bottom_up, R.anim.nothing);


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent =getIntent();
        final int position = intent.getIntExtra("RPosition",0);
       final int id = intent.getIntExtra("RId", 0);
        final List<Name> nameList = db.getAllNames();
        Name name = nameList.get(position);
        switch (item.getItemId()) {
            case R.id.call:

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" +name.getNumber()));
                startActivity(callIntent);
                return true;
            case R.id.changePicture:
                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose your profile picture");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Take Photo")) {

                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                            takePicture.putExtra("crop", "true");
//                            takePicture.putExtra("outputX", 300);
//                            takePicture.putExtra("outputY", 300);
//                            takePicture.putExtra("scale", true);
//                            takePicture.putExtra("return-data", true);
                            startActivityForResult(takePicture,CAPTURE_IMAGE);

                        } else if (options[item].equals("Choose from Gallery")) {
                            Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            i.putExtra("crop", "true");
                            i.putExtra("outputX", 300);
                            i.putExtra("outputY", 300);
                            i.putExtra("scale", true);
                            i.putExtra("return-data", true);
                            startActivityForResult(i , PICK_IMAGE);

                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return true;
            case R.id.del:
          // Remove the item on remove/button click
                final CharSequence[] options2 = { "Confirm", "Cancel" };

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Confirm remove "+ name.getName()+"?");

                builder2.setItems(options2, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options2[item].equals("Confirm")) {
                            nameList.remove(position);
                            db.deleteName(id);
                            notifyAll();
                            finish();

                       } else if (options2[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder2.show();

                return true;
            case R.id.share:

                View view1;
                view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.reminder_message, null);
                LinearLayoutCompat view = view1.findViewById(R.id.message);
                TextView name_message =view1.findViewById(R.id.name_message);
                TextView time_message =view1.findViewById(R.id.time_message);
                TextView money_message =view1.findViewById(R.id.money_message);
                name_message.setText("Hello! "+ name.getName()+",");
                time_message.setText("On "+name.getTime());
                money_message.setText("₹ "+name.getMoney());

                Bitmap returnedBitmap;
                try {

                    view.setDrawingCacheEnabled(true);

                    view.measure(View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.UNSPECIFIED));
                    view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                    view.offsetLeftAndRight(100);

                    view.buildDrawingCache(true);
                    returnedBitmap = Bitmap.createBitmap(view.getDrawingCache());
                    Canvas canvas = new Canvas(returnedBitmap);
                    //Get the view's background
                    Drawable bgDrawable = view.getBackground();
                    if (bgDrawable != null) {
                        //has background drawable, then draw it on the canvas
                        bgDrawable.draw(canvas);
                    } else {
                        //does not have background drawable, then draw white background on the canvas
                        canvas.drawColor(Color.WHITE);
                    }
                    // draw the view on the canvas
                    view.draw(canvas);
                    //Define a bitmap with the same size as the view
                    view.setDrawingCacheEnabled(false);
                }catch (Exception e){
                    return false;
                }
                Bitmap bitmap= returnedBitmap;


                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Message3", null);
                Uri imageUri = Uri.parse(path);
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, "This is reminder!!! Download the *MoneyBond* app from playstore now!!");
                whatsappIntent.setType("image/jpeg");
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this,"Whatsapp have not been installed",Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//
////            String[] filePathColumn = {MediaStore.Images.Media.DATA};
////            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
////            cursor.moveToFirst();
////            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////            String picturePath = cursor.getString(columnIndex);
////            cursor.close();
            Bitmap bitmap;

                bitmap  = (Bitmap) data.getExtras().get("data");
               // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                ImageView imageView = findViewById(R.id.dp);
                imageView.setImageBitmap(bitmap);
                Intent intent1 =getIntent();
                int position = intent1.getIntExtra("RPosition",0);
                Name name = nameList.get(position);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,stream);
                name.setImage(stream.toByteArray());
                db.updateName(name);
                db.notify();


        }
        if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK && data != null) {
            ImageView imageView = findViewById(R.id.dp);
            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
             imageView.setImageBitmap(selectedImage);
                    Intent intent1 =getIntent();
                    int position = intent1.getIntExtra("RPosition",0);
                    Name name = nameList.get(position);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100,stream);
                    name.setImage(stream.toByteArray());
                    db.updateName(name);
                    notify();
                }

        if (requestCode == PICK_MONEY && resultCode == RESULT_OK && data != null)
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
            String time=data.getExtras().getString("time");
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

           }
            TextView Time= findViewById(R.id.time);
           Time.setText("On "+time);
           name.setTime(time);
           db.updateName(name);

        }


    }
}

