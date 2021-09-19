package com.example.favourr.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favourr.Favourr;
import com.example.favourr.MainActivity;
import com.example.favourr.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CreateFavourActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText title, bountyPrice, description, location;
    Button payment, cal;
    Date deadline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_favour);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        payment = findViewById(R.id.payment);
        cal = findViewById(R.id.cal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(CreateFavourActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                deadline = new Date(year, monthOfYear, dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Favourr favourr = new Favourr(title.getText().toString(), Double.parseDouble(bountyPrice.getText().toString()),
                        description.getText().toString(), deadline, location.getText().toString());
                intent.putExtra("newFavourr", favourr);

            }
        }
        );
    }
}