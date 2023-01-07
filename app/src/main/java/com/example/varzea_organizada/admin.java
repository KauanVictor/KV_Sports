package com.example.varzea_organizada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    BottomNavigationView navigationAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        navigationAdmin = findViewById(R.id.navigationAdmin);

        NavController navController = Navigation.findNavController(this, R.id.fragmentAdmin);
        NavigationUI.setupWithNavController(navigationAdmin, navController);

        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLogin", "false").equals("false"))
        {openLogin();}


    }




    private void openLogin()
    {
        startActivity(new Intent(admin.this, MainActivity.class));
        finish();
    }
}