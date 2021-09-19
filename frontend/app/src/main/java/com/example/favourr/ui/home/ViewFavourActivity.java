package com.example.favourr.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favourr.FavourrModel;
import com.example.favourr.databinding.ActivityViewFavourBinding;

public class ViewFavourActivity extends AppCompatActivity {

    private ActivityViewFavourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFavourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        FavourrModel favourr = (FavourrModel) extras.getSerializable("FavourrData");
    }
}