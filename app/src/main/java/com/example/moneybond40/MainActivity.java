package com.example.moneybond40;

import android.app.Activity;
import android.app.Application;
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

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;


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
                    }







            }
        });



    }



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
            case R.id.help:
                startActivity(new Intent(this, Help.class));
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

