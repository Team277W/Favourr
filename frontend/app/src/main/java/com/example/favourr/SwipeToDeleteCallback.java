package com.example.favourr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favourr.models.FavourrModel;
import com.example.favourr.models.UpdatedFavourrModel;
import com.example.favourr.network.ApiInterface;
import com.example.favourr.ui.ActiveFavourrItemAdapter;
import com.example.favourr.ui.AvailableFavourrItemAdapter;
import com.example.favourr.ui.home.HomeFragment;
import com.example.favourr.ui.localFavourrs.LocalFavourrsAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private AvailableFavourrItemAdapter mAdapter;
    private ActiveFavourrItemAdapter activeAdaptor;
    private LocalFavourrsAdapter localAdaptor;
    private Drawable icon;
    private ColorDrawable background;
    Context context;
    public SwipeToDeleteCallback(LocalFavourrsAdapter adapter, Context _context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        localAdaptor = adapter;
        context = _context;
        icon = ContextCompat.getDrawable(_context,
                R.drawable.ic_baseline_check_24);
        background = new ColorDrawable(ContextCompat.getColor(context, R.color.greenSwipe));
    }

    public SwipeToDeleteCallback(AvailableFavourrItemAdapter adapter, ActiveFavourrItemAdapter _activeAdapter, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        activeAdaptor = _activeAdapter;
        icon = ContextCompat.getDrawable(context,
                R.drawable.ic_baseline_check_24);
        background = new ColorDrawable(ContextCompat.getColor(context, R.color.greenSwipe));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (activeAdaptor != null && mAdapter != null) {
            System.out.println(position);
            activeAdaptor.getFavourrs().add(0, mAdapter.getFavourrs().get(position));
//        activeAdaptor.setFavourrs(activeAdaptor.getFavourrs());
            mAdapter.getFavourrs().remove(position);
//        activeAdaptor.setFavourrs(newListActive);
//        mAdapter.setFavourrs(newListAvailable);

//        mAdapter.setFavourrs(mAdapter.getFavourrs());
            mAdapter.notifyItemRemoved(position);
            activeAdaptor.notifyItemInserted(0);
        } else {
            SharedPreferences sharedPrefs = context.getSharedPreferences("LaunchPrefs", Context.MODE_PRIVATE);
            Call<UpdatedFavourrModel> apiInterface = ApiInterface.Companion.create().updateFavourrProgress(
                    sharedPrefs.getString("userId", "No user id"),
                    Objects.requireNonNull(localAdaptor.getFlavourrs().get(position).get_id()),
                    1
            );
            apiInterface.enqueue(new Callback<UpdatedFavourrModel>() {
                @Override
                public void onResponse(Call<UpdatedFavourrModel> call, Response<UpdatedFavourrModel> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Couldn't accept favourr", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<UpdatedFavourrModel> call, Throwable t) {

                }
            });
            localAdaptor.getFlavourrs().remove(0);
            localAdaptor.notifyItemRemoved(position);
        }


    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX / 2,
                dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight + 500, iconBottom);

            background.setBounds(0, 0,
                    0,
                    0);
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight - 500, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(60);

        background.draw(c);
        c.drawText("ACCEPT", 630, itemView.getTop() + 170, p);
        icon.draw(c);
    }

}