package com.example.favourr.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favourr.MainActivity;
import com.example.favourr.databinding.ActivityCreateFavourBinding;
import com.example.favourr.models.FavourrModel;
import com.example.favourr.network.ApiInterface;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFavourActivity extends AppCompatActivity {
    DatePickerDialog picker;
    Date deadline;

    private ActivityCreateFavourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateFavourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.calendar.setOnClickListener(view -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(CreateFavourActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> deadline = new Date(year1, monthOfYear, dayOfMonth), year, month, day);
            picker.show();
        });
        binding.submitButton.setOnClickListener(view -> {
            FavourrModel favourr = new FavourrModel(
                    binding.favourTitle.getText().toString(),
                    binding.description.getText().toString(),
                    binding.contact.getText().toString(),
                    binding.locationText.getText().toString(),
                    Double.parseDouble(binding.bountyPrice.getText().toString())
            );
            Gson gson = new Gson();
            String json = gson.toJson(favourr);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
            SharedPreferences sharedPrefs = getSharedPreferences("LaunchPrefs", MODE_PRIVATE);
            String userId = sharedPrefs.getString("userId", "No user id");
            Call<FavourrModel> postFavourrService = ApiInterface.Companion.create().postFavourr(userId, requestBody);
            postFavourrService.enqueue(new Callback<FavourrModel>() {
                @Override
                public void onResponse(Call<FavourrModel> call, Response<FavourrModel> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getApplicationContext(), "Couldn't create favourr", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FavourrModel> call, Throwable t) {

                }
            });
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("goProfile", true);
            startActivity(intent);
        });
    }
}