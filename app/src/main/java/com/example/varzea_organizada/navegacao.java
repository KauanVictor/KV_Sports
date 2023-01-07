package com.example.varzea_organizada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class navegacao extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacao);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottom_navigation, navController);




    }
}