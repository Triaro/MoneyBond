package com.example.moneybond40;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.moneybond40.adapter.RecyclerViewAdapter;
import com.example.moneybond40.data.MyDBHandler;
import com.example.moneybond40.model.Name;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyDBHandler db = new MyDBHandler(MainActivity.this);
    SwipeRefreshLayout mSwipeRefreshLayout;
    private FirebaseAuth mAuth;
    private RecyclerViewAdapter recyclerViewAdapter;
    public ArrayList<Name> nameArrayList;
    //public static int dataChangeStatus=0;

   // private ArrayList<LentName> lentNameList = new ArrayList<>();
   static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase Auth

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);
        //RecyclerView initialisation
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       FloatingActionButton fab = findViewById(R.id.fab);
        TextView rupee1=findViewById(R.id.rupee1);
        TextView Money=findViewById(R.id.Money);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
            }
        });

        blink();
        nameArrayList = new ArrayList<>();

        if(db.getAllNames()!=null) {
            // get all names
            List<Name> nameList = db.getAllNames();


            for (Name name : nameList) {

                Log.d("dbDisplay", "Id " + name.getId() + "\n" +
                        "Name " + name.getName() + "\n" +
                        "Money " + name.getMoney() + "Number " + name.getNumber() + "\n");
                nameArrayList.add(name);

                //db.deleteName(lentName.getId());
            }


        }

        //Using RecyclerView
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, nameArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);



        class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
            private Drawable mDivider;

            public SimpleDividerItemDecoration(Context context) {
                mDivider = context.getResources().getDrawable(R.drawable.divider);

            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int left = 250;
                int right = parent.getWidth() - parent.getPaddingRight();

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount-1; i++) {
                    View child = parent.getChildAt(i);

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mDivider.getIntrinsicHeight();

                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }


        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()
        ));





                  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  // Start an activity for the user to pick a phone number from contacts
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
                        overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
                    }







            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        blink();
        nameArrayList = new ArrayList<>();

        if(db.getAllNames()!=null) {
            // get all names
            List<Name> nameList = db.getAllNames();


            for (Name name : nameList) {

                Log.d("dbDisplay", "Id " + name.getId() + "\n" +
                        "Name " + name.getName() + "\n" +
                        "Money " + name.getMoney() + "Number " + name.getNumber() + "\n");
                nameArrayList.add(name);

                //db.deleteName(lentName.getId());
            }


        }
        //Using RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, nameArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void blink() {
        TextView txt = findViewById(R.id.swipe);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(5);
        txt.startAnimation(anim);
        txt.setVisibility(View.INVISIBLE);
    }

//        final Handler handler = new Handler();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int timeToBlink = 1000;    //in milissegunds
//                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        TextView txt = findViewById(R.id.swipe);
//                        if(txt.getVisibility() == View.VISIBLE){
//                            txt.setVisibility(View.INVISIBLE);
//                        }else{
//                            txt.setVisibility(View.VISIBLE);
//                        }
//                        blink();
//                    }
//                },5);
//            }
//        }).start();




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                return true;
            case R.id.info:
                startActivity(new Intent(this, AppInfo.class));
                return true;
            case R.id.refresh:
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
                return true;
            case R.id.deleteAll:
                nameArrayList = new ArrayList<>();
                // get all names
                final List<Name> nameList = db.getAllNames();
                for(Name name : nameList) {
                    db.deleteName(name.getId());
                    db.close();
                }
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.share1:
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Heyy!! Check out this cool app *MoneyBond* and download it to manage your money.Now, lent or borrow money from your friends with a proper record on your hand." +
                        " Downlod it from google Playstore now");
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this,"Whatsapp have not been installed",Toast.LENGTH_SHORT).show();
            }

                return true;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, RegisterNumber.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String id, name, phone, hasPhone;
            int idx;
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                idx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                id = cursor.getString(idx);

                idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                name = cursor.getString(idx);

                idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                hasPhone = cursor.getString(idx);
                Name abhi= new Name();
                abhi.setMoney("0");
                abhi.setNumber(hasPhone);
                abhi.setName(name);
                abhi.setStatus("Your Transaction will be shown here");

                //Adding a customerName to db
                db.addName(abhi);
                nameArrayList.add(nameArrayList.size(),abhi);
                // Notify the adapter that an item inserted
                recyclerViewAdapter.notifyItemInserted(nameArrayList.size());



            }
        }
    }





}

