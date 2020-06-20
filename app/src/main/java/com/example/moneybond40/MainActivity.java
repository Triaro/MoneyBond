package com.example.moneybond40;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Picture;
import android.media.Image;
import android.os.Bundle;

import com.example.moneybond40.adapter.RecyclerViewAdapter;
import com.example.moneybond40.data.MyDBHandler;
import com.example.moneybond40.model.LentName;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    MyDBHandler db = new MyDBHandler(MainActivity.this);

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<LentName> lentNameArrayList;
    private ArrayAdapter<String> arrayAdapter;
    private Context context;
   // private ArrayList<LentName> lentNameList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //RecyclerView initialisation
        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       FloatingActionButton fab = findViewById(R.id.fab);




        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        lentNameArrayList = new ArrayList<>();
        // get all names
        final List<LentName> lentNameList= db.getAllNames();
        for(LentName lentName: lentNameList) {

            Log.d("dbAbhi", "Id " + lentName.getId() + "\n" +
                    "Name " + lentName.getLentName()+ "\n" +
                    "Lent Money " + lentName.getLentMoney() + "\n");
            lentNameArrayList.add(lentName);
           //db.deleteName(lentName.getId());
        }
        //Using RecyclerView
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, lentNameArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(MainActivity.this, EnterDetails.class);

                  startActivityForResult(intent,1);
//


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
            case R.id.deleteAll:
                lentNameArrayList = new ArrayList<>();
                // get all names
                final List<LentName> lentNameList= db.getAllNames();
                for(LentName lentName: lentNameList) {
                    db.deleteName(lentName.getId());

                }
                notifyAll();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == MainActivity.RESULT_OK) {
                    String name = data.getStringExtra("RName");
                    String money = data.getStringExtra("RMoney");

                    //Creating a lentName for db
                    LentName abhi= new LentName();
                    abhi.setLentMoney(money);
                    abhi.setLentName(name);

                    //Adding a lentName to db
                    db.addName(abhi);
                    lentNameArrayList.add(lentNameArrayList.size(),abhi);
                    // Notify the adapter that an item inserted
                    recyclerViewAdapter.notifyItemInserted(lentNameArrayList.size());
                }
                break;
            }
        }
    }
}