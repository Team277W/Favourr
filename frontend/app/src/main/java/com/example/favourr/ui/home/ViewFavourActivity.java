package com.example.favourr.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favourr.ListIdData;
import com.example.favourr.databinding.ActivityViewFavourBinding;
import com.example.favourr.models.FavourrModel;
import com.example.favourr.models.UpdatedFavourrModel;
import com.example.favourr.network.ApiInterface;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFavourActivity extends AppCompatActivity {

    private ActivityViewFavourBinding binding;
    static private int IN_PROGRESS = 1;
    static private int COMPLETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFavourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        FavourrModel favourr = (FavourrModel) extras.getSerializable("FavourrData");
        int id = extras.getInt("icIndex");
        boolean isCreator = extras.getBoolean("isCreator", false);

        binding.icon.setImageResource(new ListIdData().getIdList()[id]);
        binding.titleBody.setText(favourr.getTitle());
        binding.priceBody.setText(String.format(Locale.getDefault(), "$%.00f", favourr.getCash()));
        binding.descBody.setText(favourr.getBody());
        binding.contactBody.setText(favourr.getContact());
        binding.locationBody.setText(favourr.getCity());
        int status = IN_PROGRESS;
        if (isCreator) {
            binding.acceptButton.setText("Close favourr");
            status = COMPLETE;
        }
        final int finalStatus = status;
        binding.acceptButton.setOnClickListener(view -> {
            SharedPreferences sharedPrefs = getSharedPreferences("LaunchPrefs", Context.MODE_PRIVATE);
            Call<UpdatedFavourrModel> apiInterface = ApiInterface.Companion.create().updateFavourrProgress(
                    sharedPrefs.getString("userId", "No user id"),
                    favourr.get_id(),
                    finalStatus
            );
            apiInterface.enqueue(new Callback<UpdatedFavourrModel>() {
                @Override
                public void onResponse(Call<UpdatedFavourrModel> call, Response<UpdatedFavourrModel> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Action failed", Toast.LENGTH_LONG).show();
                    } else {
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UpdatedFavourrModel> call, Throwable t) {

                }
            });
        });
        binding.cancelButton.setOnClickListener(view -> onBackPressed());
    }
}