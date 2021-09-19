package com.example.favourr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favourr.models.FavourrModel;
import com.example.favourr.ui.ActiveFavourrItemAdapter;
import com.example.favourr.ui.AvailableFavourrItemAdapter;
import com.example.favourr.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private AvailableFavourrItemAdapter mAdapter;
    private ActiveFavourrItemAdapter activeAdaptor;
    private Drawable icon;
    private ColorDrawable background;
    public SwipeToDeleteCallback(AvailableFavourrItemAdapter adapter, ActiveFavourrItemAdapter _activeAdapter, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        activeAdaptor = _activeAdapter;
        icon = ContextCompat.getDrawable(context,
                R.drawable.profilepic1);
        background = new ColorDrawable(Color.GREEN);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        System.out.println(position);
        activeAdaptor.getFavourrs().add(0, mAdapter.getFavourrs().get(position));
//        activeAdaptor.setFavourrs(activeAdaptor.getFavourrs());
        mAdapter.getFavourrs().remove(position);

//        List<FavourrModel> newListActive = activeAdaptor.getFavourrs();
//        List<FavourrModel> newListAvailable = mAdapter.getFavourrs();
//
//
//        activeAdaptor.setFavourrs(newListActive);
//        mAdapter.setFavourrs(newListAvailable);

//        mAdapter.setFavourrs(mAdapter.getFavourrs());
        mAdapter.notifyItemRemoved(position);
        activeAdaptor.notifyItemRemoved(position);

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX,
                dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
//            int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
//            int iconRight = itemView.getLeft() + iconMargin;
//            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(10, 0,
                    0,
                    0);
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(90);
        c.drawText("Accept", 700, 100, p);
        background.draw(c);
//        icon.draw(c);
    }

}