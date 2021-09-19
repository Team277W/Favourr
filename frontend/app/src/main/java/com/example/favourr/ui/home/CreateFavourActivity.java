package com.example.favourr.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favourr.Favourr;
import com.example.favourr.MainActivity;
import com.example.favourr.R;
import com.example.favourr.ui.profile.ProfileFragment;

import java.util.Calendar;
import java.util.Date;

public class CreateFavourActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText title, bountyPrice, description, location;
    Button payment;
    ImageView cal;
    Date deadline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_favour);
        payment = findViewById(R.id.payment);
        cal = findViewById(R.id.calendar);
        title = findViewById(R.id.favourTitle);
        bountyPrice = findViewById(R.id.bountyPrice);
        description = findViewById(R.id.description);
        location = findViewById(R.id.locationText);
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
                Favourr favourr = new Favourr(title.getText().toString(), Double.parseDouble(bountyPrice.getText().toString()),
                        description.getText().toString(), deadline, location.getText().toString());
                CreateFavourActivity.super.onBackPressed();
//                MainActivity main = new MainActivity();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("goProfile", true);
                startActivity(intent);
            }
        }
        );
    }
}