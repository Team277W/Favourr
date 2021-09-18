package com.example.favourr.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.favourr.Favourr;
import com.example.favourr.R;

import java.util.Objects;

public class CreateFavour extends AppCompatActivity {
    EditText title, bountyPrice, description, location;
    Button payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_favour);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        payment = findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favourr favourr = new Favourr();
            }
        }

        );
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
    }
}