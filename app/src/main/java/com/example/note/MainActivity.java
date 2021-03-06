package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    RecyclerView recyclerView;
    Adapter adapter;
    ActionBarDrawerToggle actionBarDrawerToggle;//hanh dong ngan keo thanh chuyen doi
    NavigationView navigationView;
    TextView noItemText;
    DrawerLayout drawerLayout;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        noItemText = findViewById(R.id.noItemText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);//???? b???t nav
        actionBarDrawerToggle.syncState();


        NoteDatabase db = new NoteDatabase(this);


        List<Note> allNotes = db.getNotes();
        recyclerView = findViewById(R.id.listofNotes);

        if(allNotes.isEmpty()){
//            noItemText.setVisibility(View.VISIBLE);
        }else {
//            noItemText.setVisibility(View.GONE);
            displayList(allNotes);
        }
    }
//hien thi danh sach note
    private void displayList(List<Note> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,allNotes);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.add_menu, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.Tim)
                .getActionView();
        searchView.setSearchableInfo(searchManager
        .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // nghe thay ?????i v??n b???n truy v???n t??m ki???m
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // b??? l???c xem t??i ch??? khi g???i truy v???n
                adapter.getFilter().filter(query);
                return false;

            }
                @Override
                public boolean onQueryTextChange (String newText){
                    // b??? l???c xem t??i ch??? khi v??n b???n ???????c thay ?????i
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
            return true;
        }
            @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.them){
            Intent i = new Intent(this, AddNote.class);
            startActivity(i);
            Toast.makeText(this, "nh???n n??t Th??m",Toast.LENGTH_SHORT).show();
        }
                if (item.getItemId() == R.id.Tim) {
                    return true;
                }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        NoteDatabase db = new NoteDatabase(this);
        List<Note> getAllNotes = db.getNotes();
        if(getAllNotes.isEmpty()){
//            noItemText.setVisibility(View.VISIBLE);
        }else {
//            noItemText.setVisibility(View.GONE);
            displayList(getAllNotes);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.refesh_Notes){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        if(item.getItemId() == R.id.them){
            Intent i = new Intent(this, AddNote.class);
            startActivity(i);
        }
        if(item.getItemId() == R.id.thoat){
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Th??ng b??o");
            alert.setMessage("B???n th???c s??? mu???n tho??t?");
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    //tao su kien ket thuc app
                    Intent starmain = new Intent(Intent.ACTION_MAIN);
                    starmain.addCategory(Intent.CATEGORY_HOME);
                    startActivity(starmain);
                    finish();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }
        return true;

    }
}
