package com.example.od3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NoteDatabaseHelper dbHelper;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static final int NAV_NOTES = R.id.nav_notes;
    private static final int NAV_SETTINGS = R.id.nav_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelectedListener());

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new NoteListFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_notes);
        }
        dbHelper = new NoteDatabaseHelper(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == NAV_NOTES) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoteListFragment())
                        .commit();
                getSupportActionBar().setTitle(R.string.menu_notes);
            }
            else if (item.getItemId() == NAV_SETTINGS) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoteDetailFragment())
                        .commit();
                getSupportActionBar().setTitle(R.string.menu_settings);
            }
            else if (item.getItemId() == R.id.nav_main) {
            returnToMainScreen();
        }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }
    private void returnToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

