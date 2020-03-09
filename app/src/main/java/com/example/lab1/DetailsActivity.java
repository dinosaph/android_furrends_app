package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        Intent intent = getIntent();

        TextView hamName = findViewById(R.id.details1);
        TextView hamDetail = findViewById(R.id.details2);
        ImageView hamImage = findViewById(R.id.imageBig);

        hamName.setText(intent.getStringExtra("hamName"));
        hamDetail.setText(intent.getStringExtra("hamDetail"));
        hamImage.setImageResource(intent.getIntExtra("hamImage", R.drawable.lil_ham));
    }
}
