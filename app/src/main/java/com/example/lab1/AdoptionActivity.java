package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;

public class AdoptionActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView chosenHamster;
    TextInputEditText userName;
    TextInputEditText userAge;
    TextInputEditText userLocation;
    TextInputEditText userAbout;
    TextInputEditText userMotivation;
    int MY_STORAGE_PERMISSION;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adoption_page);

        Intent intent = getIntent();

        toolbar = findViewById(R.id.toolbar);
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

                    if (ContextCompat.checkSelfPermission(AdoptionActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("AdoptionActivity", "No permission granted. Asking again.");
                        ActivityCompat.requestPermissions(AdoptionActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_STORAGE_PERMISSION);
                    } else {
                        Log.d("AdoptionActivity", "Permission granted. We can proceed saving the mail externally.");
                        saveExternal("fur_mail", requestBody);
                    }

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean hiddenToolbar = sharedPreferences.getBoolean("toolbar", false);

        if (hiddenToolbar) {
            toolbar.setVisibility(View.GONE);
        }

        Log.d("AdoptionActivity", "Showing toolbar: " + hiddenToolbar);
    }

    public void saveExternal(String filename, String text) {

        FileOutputStream fos = null;

        try {

            String furrendsDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/furrends";

            File dir = new File(furrendsDir);

            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.e("AdoptionActivity", "saveExternal: App folder can't be created.");
                }
            }

            File textFile = new File(dir, filename + ".txt");

            if (!textFile.exists()) {
                textFile.createNewFile();
            }

            fos = new FileOutputStream(textFile);
            fos.write(text.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
