package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] hamNames = {"Lil Ham", "Jack Sparrow", "Ninja", "Woof", "Speedy", "Sushi"};
    String[] hamDetails = {
            "- 1 month old\n- Campbell\n- gray\n- always wants to play\n- very friendly with humans\n- food is its best friend\n- take care, hamsters can become obese",
            "- 2 months old\n- wild Russian hamster (tamed)\n- classic red, black and white\n- take care, it still needs some training\n- don't adopt if you have kids, it can bite pretty badly\n- not very emotionally available",
            "- 1.5 months old\n- Syrian\n- white and orange\n- eats and plays a lot\n- it squeaks from time to time, it likes to communicate with humans\n- this kind of hamster lives alone\n- hides often, disappears like a ninja",
            "- 3 weeks old\n- Campbell\n- beige\n- likes to bathe in sand and drink lots of water\n- craves lots of attention from owner\n- very cuddly, it sleeps in hand\n- sometimes it makes some bark like sounds to get attention",
            "- 2.5 weeks old\n- Campbell\n- gray and white\n- he gotta go fast everywhere, like sonic\n- he is very fast and very friendly\n- likes to jump from hand to hand and do other parkour-like things",
            "- 1 month old\n- Roborowski\n- white\n- very small and cuddly\n- it likes to sleep a lot\n- it also likes to travel a lot and design its home very often\n- it's always busy"
    };
    int[] hamImages = {R.drawable.lil_ham, R.drawable.jack_sparrow, R.drawable.ninja, R.drawable.woof, R.drawable.speedy, R.drawable.sushi};
    String[] hamDonateChoices = {"Food", "Snacks", "Toys", "Hygiene", "Hideouts"};
    ArrayList<Integer> userChoices = new ArrayList<>();
    boolean[] checkedDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        registerForContextMenu(listView);

        MyAdapter myAdapter = new MyAdapter(this, hamNames, hamDetails, hamImages);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("hamName", hamNames[position]);
                intent.putExtra("hamDetail", hamDetails[position]);
                intent.putExtra("hamImage", hamImages[position]);
                startActivity(intent);
            }
        });

        checkedDonations = new boolean[hamDonateChoices.length];

        Log.d("MainActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.item_row, R.id.text1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.item_row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.text1);
            TextView myDescription = row.findViewById(R.id.text2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);

        Log.d("MainActivity", "onCreateContextMenu: CREATED MENU");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_adopt:
                // This opens up the adoption activity
                startAdoptionRequest(item);
                return true;
            case R.id.action_donate:
                // This opens up donation choices dialog
                showDonationChoices();
                return true;
            case R.id.action_share:
                // Sharing hamster text data to external apps
                shareHamster(item);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void shareHamster(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int currentHammy = menuInfo.position;
        Log.d("MainActivity", "onContextItemSelected: " + currentHammy);

        String shareText = "Check out " + hamNames[currentHammy] + ". It's a cute hamster that needs a home. Can you help? Download Furrends from Google Store and adopt.";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Sharing bits of fluffiness");
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void startAdoptionRequest(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int currentHammy = menuInfo.position;
        Log.d("MainActivity", "onContextItemSelected: " + currentHammy);

        Intent intent = new Intent(getApplicationContext(), AdoptionActivity.class);
        intent.putExtra("hamName", hamNames[currentHammy]);
        startActivity(intent);
    }

    public void showDonationChoices() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Accepted donations:");
        mBuilder.setMultiChoiceItems(hamDonateChoices, checkedDonations, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if(isChecked){
                    if(! userChoices.contains(position)) {
                        userChoices.add(position);
                    } else if (userChoices.contains(position)) {
                        userChoices.remove(position);
                    }
                }
            }
        });

        mBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String donations = "";
                for (int i = 0; i < userChoices.size(); i++) {
                    donations += hamDonateChoices[userChoices.get(i)];
                    if (i != userChoices.size() - 1) {
                        donations += ", ";
                    }
                }

                userChoices.clear();
                for (int i = 0; i < checkedDonations.length; i++) {
                    checkedDonations[i] = false;
                }

                String msg = "You opted for the following donations:\n" + donations;
                Toast toast = Toast.makeText(MainActivity.this, msg, (int) 1.5);
                toast.show();
                checkedDonations = new boolean[hamDonateChoices.length];
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                userChoices.clear();
                for (int i = 0; i < checkedDonations.length; i++) {
                    checkedDonations[i] = false;
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog donateDialog = mBuilder.create();
        donateDialog.show();
    }
}
