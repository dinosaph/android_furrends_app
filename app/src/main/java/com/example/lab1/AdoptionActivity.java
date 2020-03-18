package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class AdoptionActivity extends AppCompatActivity {

    TextView chosenHamster;
    TextInputEditText userName;
    TextInputEditText userAge;
    TextInputEditText userLocation;
    TextInputEditText userAbout;
    TextInputEditText userMotivation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adoption_page);

        Intent intent = getIntent();

        chosenHamster = findViewById(R.id.textView_chosenHamster);
        userName = findViewById(R.id.textInputEditText_name);
        userAge = findViewById(R.id.textInputEditText_age);
        userLocation = findViewById(R.id.textInputEditText_location);
        userAbout = findViewById(R.id.textInputEditText_aboutYou);
        userMotivation = findViewById(R.id.textInputEditText_adoptionMotivation);

        final String hamster = intent.getStringExtra("hamName");
        chosenHamster.setText("You want to adopt: " + hamster);

        final Button sendButton = findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int errors = 0;
                if (TextUtils.isEmpty(userName.getText())) {
                    userName.setError("Your name is required.");
                    errors++;
                }

                if (TextUtils.isEmpty(userAge.getText())) {
                    userAge.setError("Your age is required.");
                    errors++;
                }

                if (TextUtils.isEmpty(userLocation.getText())) {
                    userLocation.setError("Your location is required.");
                    errors++;
                }

                if (TextUtils.isEmpty(userAbout.getText())) {
                    userAbout.setError("Let us know you better.");
                    errors++;
                }

                if (TextUtils.isEmpty(userMotivation.getText())) {
                    userMotivation.setError("Your motivation is required.");
                    errors++;
                }

                if (errors == 0) {

                    String requestSubject = "Furrends Adoption Request - " + hamster;
                    String requestBody = "I am " + userName.getText() + " (aged " + userAge.getText() + ").\n";
                    requestBody += "I am from " + userLocation.getText() + ".\n";
                    requestBody += "Here are some things about me:\n" + userAbout.getText() + "\n";
                    requestBody += "I want to adopt " + hamster + " because: \n" + userMotivation.getText();

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "n.saph13@yahoo.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, requestSubject);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, requestBody);
                    startActivity(Intent.createChooser(emailIntent, "Send adoption request..."));
                    finish();
                }
            }
        });

        final Button cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
