package com.example.favourr.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favourr.models.FavourrModel;
import com.example.favourr.R;
import com.example.favourr.databinding.ActivityViewFavourBinding;

import java.util.Locale;

public class ViewFavourActivity extends AppCompatActivity {

    private ActivityViewFavourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFavourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        FavourrModel favourr = (FavourrModel) extras.getSerializable("FavourrData");
//        binding.icon.setImageResource(idx.to);
        binding.titleBody.setText(favourr.getTitle());
        binding.priceBody.setText(String.format(Locale.getDefault(), "$%d", favourr.getCash()));
        binding.descBody.setText(favourr.getBody());
        binding.contactBody.setText(favourr.getContact());
        binding.locationBody.setText(favourr.getCity());

        binding.cancelButton.setOnClickListener(view -> onBackPressed());
    }
}