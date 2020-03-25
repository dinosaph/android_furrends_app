package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        Intent intent = getIntent();

        toolbar = findViewById(R.id.toolbar);
        TextView hamName = findViewById(R.id.details1);
        TextView hamDetail = findViewById(R.id.details2);
        ImageView hamImage = findViewById(R.id.imageBig);

        hamName.setText(intent.getStringExtra("hamName"));
        hamDetail.setText(intent.getStringExtra("hamDetail"));
        hamImage.setImageResource(intent.getIntExtra("hamImage", R.drawable.lil_ham));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean hiddenToolbar = sharedPreferences.getBoolean("toolbar", false);

        if (hiddenToolbar) {
            toolbar.setVisibility(View.GONE);
        }

        Log.d("DetailsActivity", "Showing toolbar: " + hiddenToolbar);
    }
}
