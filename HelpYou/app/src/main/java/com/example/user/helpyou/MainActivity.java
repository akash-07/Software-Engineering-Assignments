package com.example.user.helpyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view)    {
        Intent intent = new Intent(this,problemDescriptionActivity.class);
        startActivity(intent);
    }

    public void goToProblemsPage(View view){
        Intent intent = new Intent(this,problemFeed.class);
        startActivity(intent);
    }
}
