package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weather.ui.detail.DetailFragment;
import com.example.weather.ui.main.MainFragment;
import com.example.weather.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_alias_activity);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, MainFragment.newInstance())
//                .commitNow();
//        }
        View detail_container = findViewById(R.id.detail_container);
        if (detail_container == null) {
            NavController controller = Navigation.findNavController(this, R.id.fragment);
            NavigationUI.setupActionBarWithNavController(this, controller);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setIcon(R.drawable.ic_launcher_foreground);
        } else {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, MainFragment.newInstance())
                        .commitNow();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
//        return super.onSupportNavigateUp();
        return Navigation.findNavController(this, R.id.fragment).navigateUp();
    }
}
